package indi.deeservent.nightcrow.api.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import indi.deeservent.nightcrow.api.system.convert.SysUserConvert;
import indi.deeservent.nightcrow.api.system.dao.SysUserDao;
import indi.deeservent.nightcrow.api.system.enums.SuperAdminEnum;
import indi.deeservent.nightcrow.api.system.service.SysUserPostService;
import indi.deeservent.nightcrow.api.system.service.SysUserRoleService;
import indi.deeservent.nightcrow.api.system.service.SysUserService;
import indi.deeservent.nightcrow.api.system.vo.SysUserVO;
import lombok.AllArgsConstructor;
import indi.deeservent.nightcrow.api.common.constant.Constant;
import indi.deeservent.nightcrow.api.common.exception.FastException;
import indi.deeservent.nightcrow.api.common.page.PageResult;
import indi.deeservent.nightcrow.api.common.service.impl.BaseServiceImpl;
import indi.deeservent.nightcrow.api.system.entity.SysUserEntity;
import indi.deeservent.nightcrow.api.system.query.SysUserQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理
 *
 * @author Deeservent onion.dzw@icloud.com
 */
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends BaseServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
    private final SysUserRoleService sysUserRoleService;
    private final SysUserPostService sysUserPostService;

    @Override
    public PageResult<SysUserVO> page(SysUserQuery query) {
        // 查询参数
        Map<String, Object> params = getParams(query);

        // 分页查询
        IPage<SysUserEntity> page = getPage(query);
        params.put(Constant.PAGE, page);

        // 数据列表
        List<SysUserEntity> list = baseMapper.getList(params);

        return new PageResult<>(SysUserConvert.INSTANCE.convertList(list), page.getTotal());
    }

    private Map<String, Object> getParams(SysUserQuery query){
        Map<String, Object> params = new HashMap<>();
        params.put("username", query.getUsername());
        params.put("mobile", query.getMobile());
        params.put("gender", query.getGender());

        // 数据权限
        params.put(Constant.DATA_SCOPE, getDataScope("t1", null));

        return params;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysUserVO vo) {
        SysUserEntity entity = SysUserConvert.INSTANCE.convert(vo);
        entity.setSuperAdmin(SuperAdminEnum.NO.getValue());

        // 判断用户名是否存在
        SysUserEntity user = baseMapper.getByUsername(entity.getUsername());
        if(user != null) {
            throw new FastException("用户名已经存在");
        }

        // 判断手机号是否存在
        user = baseMapper.getByMobile(entity.getMobile());
        if(user != null) {
            throw new FastException("手机号已经存在");
        }

        // 保存用户
        baseMapper.insert(entity);

        // 保存用户角色关系
        sysUserRoleService.saveOrUpdate(entity.getId(), vo.getRoleIdList());

        // 更新用户岗位关系
        sysUserPostService.saveOrUpdate(entity.getId(), vo.getPostIdList());
    }

    @Override
    public void update(SysUserVO vo) {
        SysUserEntity entity = SysUserConvert.INSTANCE.convert(vo);

        // 判断用户名是否存在
        SysUserEntity user = baseMapper.getByUsername(entity.getUsername());
        if(user != null && !user.getId().equals(entity.getId())) {
            throw new FastException("用户名已经存在");
        }

        // 判断手机号是否存在
        user = baseMapper.getByMobile(entity.getMobile());
        if(user != null && !user.getId().equals(entity.getId())) {
            throw new FastException("手机号已经存在");
        }

        // 更新用户
        updateById(entity);

        // 更新用户角色关系
        sysUserRoleService.saveOrUpdate(entity.getId(), vo.getRoleIdList());

        // 更新用户岗位关系
        sysUserPostService.saveOrUpdate(entity.getId(), vo.getPostIdList());
    }

    @Override
    public void delete(List<Long> idList) {
        // 删除用户
        removeByIds(idList);

        // 删除用户角色关系
        sysUserRoleService.deleteByUserIdList(idList);

        // 删除用户岗位关系
        sysUserPostService.deleteByUserIdList(idList);
    }

    @Override
    public void updatePassword(Long id, String newPassword) {
        // 修改密码
        SysUserEntity user = getById(id);
        user.setPassword(newPassword);

        updateById(user);
    }

}

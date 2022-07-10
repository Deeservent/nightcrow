package indi.deeservent.nightcrow.api.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import indi.deeservent.nightcrow.api.system.convert.SysRoleConvert;
import indi.deeservent.nightcrow.api.system.dao.SysRoleDao;
import indi.deeservent.nightcrow.api.system.enums.DataScopeEnum;
import indi.deeservent.nightcrow.api.system.service.SysRoleDataScopeService;
import indi.deeservent.nightcrow.api.system.service.SysRoleMenuService;
import indi.deeservent.nightcrow.api.system.service.SysRoleService;
import indi.deeservent.nightcrow.api.system.service.SysUserRoleService;
import indi.deeservent.nightcrow.api.system.vo.SysRoleVO;
import lombok.AllArgsConstructor;
import indi.deeservent.nightcrow.api.common.page.PageResult;
import indi.deeservent.nightcrow.api.common.service.impl.BaseServiceImpl;
import indi.deeservent.nightcrow.api.system.entity.SysRoleEntity;
import indi.deeservent.nightcrow.api.system.query.SysRoleQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色
 * 
 * @author Deeservent onion.dzw@icloud.com
 */
@Service
@AllArgsConstructor
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {
	private final SysRoleMenuService sysRoleMenuService;
	private final SysRoleDataScopeService sysRoleDataScopeService;
	private final SysUserRoleService sysUserRoleService;

	@Override
	public PageResult<SysRoleVO> page(SysRoleQuery query) {
		IPage<SysRoleEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

		return new PageResult<>(SysRoleConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
	}

	@Override
	public List<SysRoleVO> getList(SysRoleQuery query) {
		List<SysRoleEntity> entityList = baseMapper.selectList(getWrapper(query));

		return SysRoleConvert.INSTANCE.convertList(entityList);
	}

	private QueryWrapper<SysRoleEntity> getWrapper(SysRoleQuery query){
		QueryWrapper<SysRoleEntity> wrapper = new QueryWrapper<>();
		wrapper.like(StrUtil.isNotBlank(query.getName()), "name", query.getName());

		// 数据权限
		dataScopeWrapper(wrapper);

		return wrapper;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(SysRoleVO vo) {
		SysRoleEntity entity = SysRoleConvert.INSTANCE.convert(vo);

		// 保存角色
		entity.setDataScope(DataScopeEnum.CUSTOM.getValue());
		baseMapper.insert(entity);

		// 保存角色菜单关系
		sysRoleMenuService.saveOrUpdate(entity.getId(), vo.getMenuIdList());

		// 保存角色数据权限关系
		sysRoleDataScopeService.saveOrUpdate(entity.getId(), vo.getOrgIdList());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysRoleVO vo) {
		SysRoleEntity entity = SysRoleConvert.INSTANCE.convert(vo);

		// 更新角色
		updateById(entity);

		// 更新角色菜单关系
		sysRoleMenuService.saveOrUpdate(entity.getId(), vo.getMenuIdList());

		// 更新角色数据权限关系
		sysRoleDataScopeService.saveOrUpdate(entity.getId(), vo.getOrgIdList());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(List<Long> idList) {
		// 删除角色
		removeByIds(idList);

		// 删除用户角色关系
		sysUserRoleService.deleteByRoleIdList(idList);

		// 删除角色菜单关系
		sysRoleMenuService.deleteByRoleIdList(idList);

		// 删除角色数据权限关系
		sysRoleDataScopeService.deleteByRoleIdList(idList);
	}

}
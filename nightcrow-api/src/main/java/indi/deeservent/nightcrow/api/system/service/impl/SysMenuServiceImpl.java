package indi.deeservent.nightcrow.api.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import indi.deeservent.nightcrow.api.system.convert.SysMenuConvert;
import indi.deeservent.nightcrow.api.system.dao.SysMenuDao;
import indi.deeservent.nightcrow.api.system.enums.SuperAdminEnum;
import indi.deeservent.nightcrow.api.system.service.SysMenuService;
import indi.deeservent.nightcrow.api.system.service.SysRoleMenuService;
import indi.deeservent.nightcrow.api.system.vo.SysMenuVO;
import lombok.AllArgsConstructor;
import indi.deeservent.nightcrow.api.common.constant.Constant;
import indi.deeservent.nightcrow.api.common.exception.FastException;
import indi.deeservent.nightcrow.api.common.service.impl.BaseServiceImpl;
import indi.deeservent.nightcrow.api.common.utils.TreeUtils;
import indi.deeservent.nightcrow.api.security.user.UserDetail;
import indi.deeservent.nightcrow.api.system.entity.SysMenuEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 菜单管理
 *
 * @author Deeservent onion.dzw@icloud.com
 */
@Service
@AllArgsConstructor
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {
	private final SysRoleMenuService sysRoleMenuService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(SysMenuVO vo) {
		SysMenuEntity entity = SysMenuConvert.INSTANCE.convert(vo);

		// 保存菜单
		baseMapper.insert(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysMenuVO vo) {
		SysMenuEntity entity = SysMenuConvert.INSTANCE.convert(vo);

		// 上级菜单不能为自己
		if(entity.getId().equals(entity.getPid())){
			throw new FastException("上级菜单不能为自己");
		}

		// 更新菜单
		updateById(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(Long id) {
		// 删除菜单
		removeById(id);

		// 删除角色菜单关系
		sysRoleMenuService.deleteByMenuId(id);
	}

	@Override
	public List<SysMenuVO> getMenuList(Integer type) {
		List<SysMenuEntity> menuList = baseMapper.getMenuList(type);

		return TreeUtils.build(SysMenuConvert.INSTANCE.convertList(menuList), Constant.ROOT);
	}

	@Override
	public List<SysMenuVO> getUserMenuList(UserDetail user, Integer type) {
		List<SysMenuEntity> menuList;

		// 系统管理员，拥有最高权限
		if(user.getSuperAdmin().equals(SuperAdminEnum.YES.getValue())){
			menuList = baseMapper.getMenuList(type);
		}else {
			menuList = baseMapper.getUserMenuList(user.getId(), type);
		}

		return TreeUtils.build(SysMenuConvert.INSTANCE.convertList(menuList));
	}

	@Override
	public Long getSubMenuCount(Long pid) {
		return count(new QueryWrapper<SysMenuEntity>().eq("pid", pid));
	}

	@Override
	public Set<String> getUserAuthority(UserDetail user) {
		// 系统管理员，拥有最高权限
		List<String> authorityList;
		if(user.getSuperAdmin().equals(SuperAdminEnum.YES.getValue())) {
			authorityList = baseMapper.getAuthorityList();
		}else{
			authorityList = baseMapper.getUserAuthorityList(user.getId());
		}

		// 用户权限列表
		Set<String> permsSet = new HashSet<>();
		for(String authority : authorityList){
			if(StrUtil.isBlank(authority)){
				continue;
			}
			permsSet.addAll(Arrays.asList(authority.trim().split(",")));
		}

		return permsSet;
	}

}
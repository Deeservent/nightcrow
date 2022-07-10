package indi.deeservent.nightcrow.api.system.service;

import indi.deeservent.nightcrow.api.common.page.PageResult;
import indi.deeservent.nightcrow.api.common.service.BaseService;
import indi.deeservent.nightcrow.api.system.entity.SysRoleEntity;
import indi.deeservent.nightcrow.api.system.query.SysRoleQuery;
import indi.deeservent.nightcrow.api.system.vo.SysRoleVO;

import java.util.List;

/**
 * 角色
 * 
 * @author Deeservent onion.dzw@icloud.com
 */
public interface SysRoleService extends BaseService<SysRoleEntity> {

	PageResult<SysRoleVO> page(SysRoleQuery query);

	List<SysRoleVO> getList(SysRoleQuery query);

	void save(SysRoleVO vo);

	void update(SysRoleVO vo);

	void delete(List<Long> idList);
}

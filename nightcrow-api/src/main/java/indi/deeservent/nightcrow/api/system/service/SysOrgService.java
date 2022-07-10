package indi.deeservent.nightcrow.api.system.service;

import indi.deeservent.nightcrow.api.common.service.BaseService;
import indi.deeservent.nightcrow.api.system.entity.SysOrgEntity;
import indi.deeservent.nightcrow.api.system.vo.SysOrgVO;

import java.util.List;

/**
 * 机构管理
 * 
 * @author Deeservent onion.dzw@icloud.com
 */
public interface SysOrgService extends BaseService<SysOrgEntity> {

	List<SysOrgVO> getList();

	void save(SysOrgVO vo);

	void update(SysOrgVO vo);

	void delete(Long id);

	/**
	 * 根据机构ID，获取子机构ID列表(包含本机构ID)
	 * @param id   机构ID
	 */
	List<Long> getSubOrgIdList(Long id);
}
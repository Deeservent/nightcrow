package indi.deeservent.nightcrow.api.system.dao;

import indi.deeservent.nightcrow.api.common.dao.BaseDao;
import indi.deeservent.nightcrow.api.system.entity.SysPostEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 岗位管理
*
* @author Deeservent onion.dzw@icloud.com
*/
@Mapper
public interface SysPostDao extends BaseDao<SysPostEntity> {
	
}
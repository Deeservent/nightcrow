package indi.deeservent.nightcrow.api.system.dao;

import indi.deeservent.nightcrow.api.common.dao.BaseDao;
import indi.deeservent.nightcrow.api.system.entity.SysOrgEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 机构管理
 * 
 * @author Deeservent onion.dzw@icloud.com
 */
@Mapper
public interface SysOrgDao extends BaseDao<SysOrgEntity> {

    List<SysOrgEntity> getList(Map<String, Object> params);

    /**
     * 获取所有机构的id、pid列表
     */
    List<SysOrgEntity> getIdAndPidList();

}
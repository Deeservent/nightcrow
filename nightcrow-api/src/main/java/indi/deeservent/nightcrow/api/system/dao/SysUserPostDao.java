package indi.deeservent.nightcrow.api.system.dao;

import indi.deeservent.nightcrow.api.common.dao.BaseDao;
import indi.deeservent.nightcrow.api.system.entity.SysUserPostEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 用户岗位关系
*
* @author Deeservent onion.dzw@icloud.com
*/
@Mapper
public interface SysUserPostDao extends BaseDao<SysUserPostEntity> {

    /**
     * 岗位ID列表
     * @param userId  用户ID
     */
    List<Long> getPostIdList(@Param("userId") Long userId);
}
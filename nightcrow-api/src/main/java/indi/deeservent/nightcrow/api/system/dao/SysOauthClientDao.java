package indi.deeservent.nightcrow.api.system.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import indi.deeservent.nightcrow.api.common.dao.BaseDao;
import indi.deeservent.nightcrow.api.system.entity.SysOauthClientEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 客户端管理
 *
 * @author Deeservent onion.dzw@icloud.com
 */
@Mapper
public interface SysOauthClientDao extends BaseDao<SysOauthClientEntity> {

    default SysOauthClientEntity getByClientId(String clientId){
        return this.selectOne(new QueryWrapper<SysOauthClientEntity>().eq("client_id", clientId));
    }
}

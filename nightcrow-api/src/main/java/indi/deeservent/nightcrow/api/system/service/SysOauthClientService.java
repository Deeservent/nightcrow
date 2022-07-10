package indi.deeservent.nightcrow.api.system.service;

import indi.deeservent.nightcrow.api.common.page.PageResult;
import indi.deeservent.nightcrow.api.common.query.Query;
import indi.deeservent.nightcrow.api.common.service.BaseService;
import indi.deeservent.nightcrow.api.system.entity.SysOauthClientEntity;
import indi.deeservent.nightcrow.api.system.vo.SysOauthClientVO;

import java.util.List;

/**
 * 客户端管理
 *
 * @author Deeservent onion.dzw@icloud.com
 */
public interface SysOauthClientService extends BaseService<SysOauthClientEntity> {

    PageResult<SysOauthClientVO> page(Query query);

    void save(SysOauthClientVO vo);

    void update(SysOauthClientVO vo);

    void delete(List<Long> idList);
}

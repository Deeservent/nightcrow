package indi.deeservent.nightcrow.api.system.service;

import indi.deeservent.nightcrow.api.common.page.PageResult;
import indi.deeservent.nightcrow.api.common.service.BaseService;
import indi.deeservent.nightcrow.api.system.entity.SysPostEntity;
import indi.deeservent.nightcrow.api.system.query.SysPostQuery;
import indi.deeservent.nightcrow.api.system.vo.SysPostVO;

import java.util.List;

/**
 * 岗位管理
 *
 * @author Deeservent onion.dzw@icloud.com
 */
public interface SysPostService extends BaseService<SysPostEntity> {

    PageResult<SysPostVO> page(SysPostQuery query);

    List<SysPostVO> getList();

    void save(SysPostVO vo);

    void update(SysPostVO vo);

    void delete(List<Long> idList);
}
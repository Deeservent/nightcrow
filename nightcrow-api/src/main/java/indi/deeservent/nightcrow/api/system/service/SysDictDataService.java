package indi.deeservent.nightcrow.api.system.service;

import indi.deeservent.nightcrow.api.common.page.PageResult;
import indi.deeservent.nightcrow.api.common.service.BaseService;
import indi.deeservent.nightcrow.api.system.entity.SysDictDataEntity;
import indi.deeservent.nightcrow.api.system.query.SysDictDataQuery;
import indi.deeservent.nightcrow.api.system.vo.SysDictDataVO;

import java.util.List;

/**
 * 数据字典
 *
 * @author Deeservent onion.dzw@icloud.com
 */
public interface SysDictDataService extends BaseService<SysDictDataEntity> {

    PageResult<SysDictDataVO> page(SysDictDataQuery query);

    void save(SysDictDataVO vo);

    void update(SysDictDataVO vo);

    void delete(List<Long> idList);

}
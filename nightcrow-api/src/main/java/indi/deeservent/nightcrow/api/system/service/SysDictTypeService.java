package indi.deeservent.nightcrow.api.system.service;

import indi.deeservent.nightcrow.api.common.page.PageResult;
import indi.deeservent.nightcrow.api.common.service.BaseService;
import indi.deeservent.nightcrow.api.system.entity.SysDictTypeEntity;
import indi.deeservent.nightcrow.api.system.vo.SysDictVO;
import indi.deeservent.nightcrow.api.system.query.SysDictTypeQuery;
import indi.deeservent.nightcrow.api.system.vo.SysDictTypeVO;

import java.util.List;

/**
 * 数据字典
 *
 * @author Deeservent onion.dzw@icloud.com
 */
public interface SysDictTypeService extends BaseService<SysDictTypeEntity> {

    PageResult<SysDictTypeVO> page(SysDictTypeQuery query);

    void save(SysDictTypeVO vo);

    void update(SysDictTypeVO vo);

    void delete(List<Long> idList);

    /**
     * 获取全部字典列表
     */
    List<SysDictVO> getDictList();

}
package indi.deeservent.nightcrow.api.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import indi.deeservent.nightcrow.api.system.convert.SysDictDataConvert;
import indi.deeservent.nightcrow.api.system.dao.SysDictDataDao;
import indi.deeservent.nightcrow.api.system.service.SysDictDataService;
import indi.deeservent.nightcrow.api.system.vo.SysDictDataVO;
import lombok.AllArgsConstructor;
import indi.deeservent.nightcrow.api.common.page.PageResult;
import indi.deeservent.nightcrow.api.common.service.impl.BaseServiceImpl;
import indi.deeservent.nightcrow.api.system.entity.SysDictDataEntity;
import indi.deeservent.nightcrow.api.system.query.SysDictDataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据字典
 *
 * @author Deeservent onion.dzw@icloud.com
 */
@Service
@AllArgsConstructor
public class SysDictDataServiceImpl extends BaseServiceImpl<SysDictDataDao, SysDictDataEntity> implements SysDictDataService {

    @Override
    public PageResult<SysDictDataVO> page(SysDictDataQuery query) {
        IPage<SysDictDataEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(SysDictDataConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    private QueryWrapper<SysDictDataEntity> getWrapper(SysDictDataQuery query){
        QueryWrapper<SysDictDataEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_type_id", query.getDictTypeId());
        wrapper.orderByAsc("sort");

        return wrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysDictDataVO vo) {
        SysDictDataEntity entity = SysDictDataConvert.INSTANCE.convert(vo);

        baseMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysDictDataVO vo) {
        SysDictDataEntity entity = SysDictDataConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

}
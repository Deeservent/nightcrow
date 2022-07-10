package indi.deeservent.nightcrow.api.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import indi.deeservent.nightcrow.api.system.dao.SysOauthClientDao;
import indi.deeservent.nightcrow.api.system.service.SysOauthClientService;
import indi.deeservent.nightcrow.api.system.vo.SysOauthClientVO;
import indi.deeservent.nightcrow.api.common.page.PageResult;
import indi.deeservent.nightcrow.api.common.query.Query;
import indi.deeservent.nightcrow.api.common.service.impl.BaseServiceImpl;
import indi.deeservent.nightcrow.api.system.convert.SysOauthClientConvert;
import indi.deeservent.nightcrow.api.system.entity.SysOauthClientEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户端管理
 *
 * @author Deeservent onion.dzw@icloud.com
 */
@Service
public class SysOauthClientServiceImpl extends BaseServiceImpl<SysOauthClientDao, SysOauthClientEntity>
        implements SysOauthClientService {

    @Override
    public PageResult<SysOauthClientVO> page(Query query) {
        IPage<SysOauthClientEntity> page = baseMapper.selectPage(getPage(query), Wrappers.emptyWrapper());

        return new PageResult<>(SysOauthClientConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    @Override
    public void save(SysOauthClientVO vo) {
        SysOauthClientEntity entity = SysOauthClientConvert.INSTANCE.convert(vo);
        //entity.setAuthorizedGrantTypes(JsonUtils.toJsonString(vo.getAuthorizedGrantTypes()));

        baseMapper.insert(entity);
    }

    @Override
    public void update(SysOauthClientVO vo) {
        SysOauthClientEntity entity = SysOauthClientConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }
}

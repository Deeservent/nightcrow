package indi.deeservent.nightcrow.api.system.convert;

import indi.deeservent.nightcrow.api.system.vo.SysOauthClientVO;
import indi.deeservent.nightcrow.api.system.entity.SysOauthClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface SysOauthClientConvert {
    SysOauthClientConvert INSTANCE = Mappers.getMapper(SysOauthClientConvert.class);

    SysOauthClientVO convert(SysOauthClientEntity entity);

    SysOauthClientEntity convert(SysOauthClientVO vo);

    List<SysOauthClientVO> convertList(List<SysOauthClientEntity> list);

}

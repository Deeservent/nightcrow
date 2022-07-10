package indi.deeservent.nightcrow.api.system.convert;

import indi.deeservent.nightcrow.api.system.vo.SysMenuVO;
import indi.deeservent.nightcrow.api.system.entity.SysMenuEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface SysMenuConvert {
    SysMenuConvert INSTANCE = Mappers.getMapper(SysMenuConvert.class);

    SysMenuEntity convert(SysMenuVO vo);

    SysMenuVO convert(SysMenuEntity entity);

    List<SysMenuVO> convertList(List<SysMenuEntity> list);

}

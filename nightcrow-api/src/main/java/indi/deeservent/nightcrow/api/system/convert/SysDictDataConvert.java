package indi.deeservent.nightcrow.api.system.convert;

import indi.deeservent.nightcrow.api.system.vo.SysDictDataVO;
import indi.deeservent.nightcrow.api.system.entity.SysDictDataEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SysDictDataConvert {
    SysDictDataConvert INSTANCE = Mappers.getMapper(SysDictDataConvert.class);

    SysDictDataVO convert(SysDictDataEntity entity);

    SysDictDataEntity convert(SysDictDataVO vo);
    
    List<SysDictDataVO> convertList(List<SysDictDataEntity> list);

}

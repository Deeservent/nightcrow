package indi.deeservent.nightcrow.api.system.convert;

import indi.deeservent.nightcrow.api.system.vo.SysRoleVO;
import indi.deeservent.nightcrow.api.system.entity.SysRoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SysRoleConvert {
    SysRoleConvert INSTANCE = Mappers.getMapper(SysRoleConvert.class);

    SysRoleVO convert(SysRoleEntity entity);

    SysRoleEntity convert(SysRoleVO vo);
    
    List<SysRoleVO> convertList(List<SysRoleEntity> list);

}

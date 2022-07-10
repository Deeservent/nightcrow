package indi.deeservent.nightcrow.api.system.convert;

import indi.deeservent.nightcrow.api.system.vo.SysUserVO;
import indi.deeservent.nightcrow.api.security.user.UserDetail;
import indi.deeservent.nightcrow.api.system.entity.SysUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface SysUserConvert {
    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

    SysUserVO convert(SysUserEntity entity);

    SysUserEntity convert(SysUserVO vo);

    SysUserVO convert(UserDetail userDetail);

    UserDetail convertDetail(SysUserEntity entity);

    List<SysUserVO> convertList(List<SysUserEntity> list);

}

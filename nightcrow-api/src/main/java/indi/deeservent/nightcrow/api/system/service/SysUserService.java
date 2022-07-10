package indi.deeservent.nightcrow.api.system.service;

import indi.deeservent.nightcrow.api.common.page.PageResult;
import indi.deeservent.nightcrow.api.common.service.BaseService;
import indi.deeservent.nightcrow.api.system.entity.SysUserEntity;
import indi.deeservent.nightcrow.api.system.query.SysUserQuery;
import indi.deeservent.nightcrow.api.system.vo.SysUserVO;

import java.util.List;

/**
 * 用户管理
 *
 * @author Deeservent onion.dzw@icloud.com
 */
public interface SysUserService extends BaseService<SysUserEntity> {

    PageResult<SysUserVO> page(SysUserQuery query);

    void save(SysUserVO vo);

    void update(SysUserVO vo);

    void delete(List<Long> idList);

    /**
     * 修改密码
     * @param id           用户ID
     * @param newPassword  新密码
     */
    void updatePassword(Long id, String newPassword);

}

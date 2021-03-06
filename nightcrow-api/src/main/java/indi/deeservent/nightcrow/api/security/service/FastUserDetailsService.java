package indi.deeservent.nightcrow.api.security.service;

import indi.deeservent.nightcrow.api.system.enums.DataScopeEnum;
import indi.deeservent.nightcrow.api.system.enums.UserStatusEnum;
import lombok.AllArgsConstructor;
import indi.deeservent.nightcrow.api.common.exception.ErrorCode;
import indi.deeservent.nightcrow.api.common.exception.FastException;
import indi.deeservent.nightcrow.api.security.user.UserDetail;
import indi.deeservent.nightcrow.api.system.convert.SysUserConvert;
import indi.deeservent.nightcrow.api.system.dao.SysRoleDao;
import indi.deeservent.nightcrow.api.system.dao.SysRoleDataScopeDao;
import indi.deeservent.nightcrow.api.system.dao.SysUserDao;
import indi.deeservent.nightcrow.api.system.entity.SysUserEntity;
import indi.deeservent.nightcrow.api.system.service.SysMenuService;
import indi.deeservent.nightcrow.api.system.service.SysOrgService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserDetailsService
 *
 * @author Deeservent onion.dzw@icloud.com
 */
@Service
@AllArgsConstructor
public class FastUserDetailsService implements UserDetailsService {
    private final SysMenuService sysMenuService;
    private final SysOrgService sysOrgService;
    private final SysUserDao sysUserDao;
    private final SysRoleDao sysRoleDao;
    private final SysRoleDataScopeDao sysRoleDataScopeDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserEntity userEntity = sysUserDao.getByUsername(username);
        if(userEntity == null) {
            throw new FastException(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }

        // ?????????UserDetail??????
        UserDetail userDetail = SysUserConvert.INSTANCE.convertDetail(userEntity);

        // ???????????????
        if(userEntity.getStatus() == UserStatusEnum.DISABLE.getValue()){
            userDetail.setEnabled(false);
        }

        // ??????????????????
        List<Long> dataScopeList = getDataScope(userDetail);
        userDetail.setDataScopeList(dataScopeList);

        // ??????????????????
        Set<GrantedAuthority> authorities = getUserAuthority(userDetail);
        userDetail.setAuthorities(authorities);

        return userDetail;
    }

    private List<Long> getDataScope(UserDetail userDetail){
        Integer dataScope = sysRoleDao.getDataScopeByUserId(userDetail.getId());
        if (dataScope == null){
            return new ArrayList<>();
        }

        if (dataScope.equals(DataScopeEnum.ALL.getValue())) {
            // ??????????????????????????????null
            return null;
        } else if (dataScope.equals(DataScopeEnum.DEPT_AND_CHILD.getValue())) {
            // ???????????????????????????
            List<Long> dataScopeList = sysOrgService.getSubOrgIdList(userDetail.getOrgId());
            // ???????????????????????????
            dataScopeList.addAll(sysRoleDataScopeDao.getDataScopeList(userDetail.getId()));

            return dataScopeList;
        } else if (dataScope.equals(DataScopeEnum.DEPT_ONLY.getValue())) {
            // ???????????????
            List<Long> dataScopeList = new ArrayList<>();
            dataScopeList.add(userDetail.getOrgId());
            // ???????????????????????????
            dataScopeList.addAll(sysRoleDataScopeDao.getDataScopeList(userDetail.getId()));

            return dataScopeList;
        } else if (dataScope.equals(DataScopeEnum.CUSTOM.getValue())) {
            // ???????????????????????????
            return sysRoleDataScopeDao.getDataScopeList(userDetail.getId());
        }

        return new ArrayList<>();
    }

    private Set<GrantedAuthority> getUserAuthority(UserDetail user) {
        // ????????????????????????
        Set<String> permsSet = sysMenuService.getUserAuthority(user);

        // ??????????????????
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.addAll(permsSet.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));

        return authorities;
    }
}

package indi.deeservent.nightcrow.api.security.user;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 用户
 * @author Deeservent onion.dzw@icloud.com
 */
public class SecurityUser {

    /**
     * 获取用户信息
     */
    public static UserDetail getUser() {
        UserDetail user;
        try {
            user = (UserDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (Exception e){
            return new UserDetail();
        }

        return user;
    }

    /**
     * 获取用户ID
     */
    public static Long getUserId() {
        return getUser().getId();
    }

}
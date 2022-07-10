package indi.deeservent.nightcrow.api.security.handler;

import indi.deeservent.nightcrow.api.common.utils.HttpContextUtils;
import indi.deeservent.nightcrow.api.common.utils.JsonUtils;
import indi.deeservent.nightcrow.api.common.utils.Result;
import lombok.SneakyThrows;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证失败处理器
 *
 * @author Deeservent onion.dzw@icloud.com
 */
@Component
public class UserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @SneakyThrows
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)  {
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());

        response.getWriter().write(JsonUtils.toJsonString(Result.error(e.getMessage())));
    }
}

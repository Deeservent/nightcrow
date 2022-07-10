package indi.deeservent.nightcrow.api.security.exception;

import indi.deeservent.nightcrow.api.common.exception.ErrorCode;
import indi.deeservent.nightcrow.api.common.utils.HttpContextUtils;
import indi.deeservent.nightcrow.api.common.utils.JsonUtils;
import indi.deeservent.nightcrow.api.common.utils.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 匿名用户(token不存在、错误)，异常处理器
 *
 * @author Deeservent onion.dzw@icloud.com
 */
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
       response.setContentType("application/json; charset=utf-8");
       response.setHeader("Access-Control-Allow-Credentials", "true");
       response.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());

       response.getWriter().print(JsonUtils.toJsonString(Result.error(ErrorCode.UNAUTHORIZED)));
    }
}
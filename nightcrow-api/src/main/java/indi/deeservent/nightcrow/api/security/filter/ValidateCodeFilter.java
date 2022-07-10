package indi.deeservent.nightcrow.api.security.filter;

import indi.deeservent.nightcrow.api.security.service.CaptchaService;
import lombok.AllArgsConstructor;
import indi.deeservent.nightcrow.api.security.exception.FastAuthenticationException;
import indi.deeservent.nightcrow.api.security.handler.UserAuthenticationFailureHandler;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码过滤器
 *
 * @author Deeservent onion.dzw@icloud.com
 */
@Component
@AllArgsConstructor
public class ValidateCodeFilter extends OncePerRequestFilter {
    private final static String OAUTH_TOKEN_URL = "/sys/oauth/token";
    private final CaptchaService captchaService;
    private final UserAuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals(OAUTH_TOKEN_URL)
            && request.getMethod().equalsIgnoreCase("POST")
            && "password".equalsIgnoreCase(request.getParameter("grant_type"))) {
            try {
                // 校验验证码
                validate(request);
            }catch (AuthenticationException e) {
                // 失败处理器
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void validate(HttpServletRequest request) {
        String key = request.getParameter("key");
        String captcha = request.getParameter("captcha");

        boolean flag = captchaService.validate(key, captcha);

        if(!flag) {
            throw new FastAuthenticationException("验证码错误");
        }
    }
}

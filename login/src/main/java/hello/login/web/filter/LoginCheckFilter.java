package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {


    /**
     * 인증 필터를 적용해도 홈, 회원가입, 로그인 화면, css 같은 리소스에는 접근할 수 있어야 한다.
     * 이렇게 화이트 리스트 경로는 인증과 무관하게 항상 허용한다.
     * 화이트 리스트를 제외한 나머지 모든 경로에는 인증 체크 로직을 적용한다.
     **/
    private static final String[] whiteList = {
            "/"
            , "/members/add"
            , "/login"
            , "/logout"
            , "/css/*"
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            log.info("인증 체크 필터 시작 {}", requestURI);

            // whiteList 에 등록되어 있지 않은 uri 일 경우 실행되고,
            if (isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 실행 {}", requestURI);
                HttpSession session = httpRequest.getSession(false);
                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                    log.info("미인증 사용자 요청 {} : ", requestURI);
                    // 로그인으로 redirect
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
                    return; // 미인증 사용자는 다음으로 진행하지 않고 끝.
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e; // 예외 로깅 가능 하지만, 톰캣까지 예외를 보내주어야 함.
        } finally {
            log.info("인증 체크 필터 종료 {}", requestURI);
        }

        /**
         * httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
         *      미인증 사용자는 로그인 화면으로 리다이렉트 한다.
         *      그런데 로그인 이후에 다시 홈으로 이동해버리면, 원하는 경로를 다시 찾아가야 하는 불편함이 있다.
         *      예를 들어서 상품 관리 화면을 보려고 들어갔다가 로그인 화면으로 이동하면,
         *      로그인 이후에 다시 상품 관리 화면으로 들어가는 것이 좋다.
         **/

    }

    /**
     * 화이트 리스트의 경우 인증 체크 하지않는다.
     **/
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }



}

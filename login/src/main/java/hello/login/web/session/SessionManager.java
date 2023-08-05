package hello.login.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 세션 관리
 *
 * 요청(로그인) -> 세션 id 생성, 세션 저장소 [ key : 세션 id / value : 유저 ]에 저장
 * -> 쿠키에 세션 id 정보 저장 [ key : SESSION_COOKIE_NAME, value : 세션 id ] -> 클라이언트
 *
 * 세션 생성 : response
 * 세션 조회, 만료 : request
 *
 **/
@Component
public class SessionManager {

    // 쿠키에 저장할 sessionId 이름 설정.
    public static final String SESSION_COOKIE_NAME = "mySessionId";

    private Map<String, Object> sessionStore = new ConcurrentHashMap<>(); // ConcurrentHashMap : 동시 요청에 안전.

    /**  
     * 세션 생성 및 쿠키에 세션 id 저장.
     **/
    public void createSession(Object value, HttpServletResponse response) {

        // 세션 id 생성, 값을 세션에 저장.
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);

        // 쿠키 생성
        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(mySessionCookie);
    }

    /**
     * 세션 조회 : 해당 쿠키를 찾은 다음, 세션 저장소에서 회원정보를 가져옴.
     **/
    public Object getSession(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie == null) {
            return null;
        }
        String sessionId = sessionCookie.getValue();
        Object object = sessionStore.get(sessionId);
        return object;
    }

    /**
     * 세션 만료
     **/
    public void expire(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie != null) {
            sessionStore.remove(sessionCookie.getValue());
        }
    }


    public Cookie findCookie(HttpServletRequest request, String cookieName) {
        if(request.getCookies() == null) {
            return null;
        }
        Cookie[] cookies = request.getCookies();
        Cookie cookie = Arrays.stream(cookies).filter(c -> c.getName().equals(cookieName)).findAny().orElse(null);
        return cookie;
    }



}

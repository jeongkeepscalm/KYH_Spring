package hello.login.web.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.TimerTask;


/**
 * 세션 정보와 타임아웃 설정
 **/
@Slf4j
@RestController
public class SessionInfoController {

    @GetMapping("session-info")
    public String sesionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "세션이 없습니다.";
        }
        session.getAttributeNames()
                .asIterator()
                .forEachRemaining(name -> log.info("session.names : {}, session.values : {}", name, session.getAttribute(name)));

        log.info("sessionId : {}", session.getId()); 
        log.info("maxInactiveInterval : {}", session.getMaxInactiveInterval());
        log.info("creationTime : {}", session.getCreationTime());
        log.info("lastAccessedTime : {}", session.getLastAccessedTime());
        log.info("isNew : {}", session.isNew());

        return "print session";

        /**  
         * sessionId : JSESSIONID 의 값. 
         * maxInactiveInterval : 세션 유효시간.
         * creationTime : 세션 생성시간.
         * lastAccessedTime : 세션과 연결된 사용자가 최근에 서버에 접근한 시간 ( 클라이언트 -> 서버 ). sessionId 를 요청한 경우에 갱신.
         * isNew : 새로 생성된 세션인지, 아니면 이미 과거에 만들어졌고, 클라이언트에서 서버로 sessionId를 요청해서 조회된 세션인지 여부.
         **/

    }

}

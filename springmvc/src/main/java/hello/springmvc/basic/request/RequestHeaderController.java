package hello.springmvc.basic.request;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Slf4j // log사용 가능.
@RestController // jsp를 찾지 않고 응답갑 그대로 출력. 테스트할 때는 jsp를 굳이 만들 필요 x.
public class RequestHeaderController {

    @RequestMapping("/headers")
    public String headers(HttpServletRequest request
            , HttpServletResponse response
            , HttpMethod httpMethod                                                 // HttpMethod : methodType (get, post, put, patch, delete..)
            , Locale locale                                                         // Locale : priority of language information
            , @RequestHeader MultiValueMap<String, String> headerMap                // get all things about Header
            , @RequestHeader("host") String host                                    // get host information from header
            , @CookieValue(value = "myCookie", required = false) String cookie) {   // get information of cookie with myCookie

        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("header host={}", host);
        log.info("myCookie={}", cookie);
        return "ok";

        /**
             MultiValueMap
             MAP과 유사한데, 하나의 키에 여러 값을 받을 수 있다.
             HTTP header, HTTP 쿼리 파라미터와 같이 하나의 키에 여러 값을 받을 때 사용한다.

             keyA=value1&keyA=value2
             MultiValueMap<String, String> map = new LinkedMultiValueMap();
             map.add("keyA", "value1");
             map.add("keyA", "value2");

             List<String> values = map.get("keyA"); // [value1,value2]
         **/

    }
}

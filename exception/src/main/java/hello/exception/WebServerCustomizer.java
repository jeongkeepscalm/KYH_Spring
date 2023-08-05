package hello.exception;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

//@Component
// 스프링 부트가 제공하는 기본 오류 메커니즘을 사용하기 위해 주석처리.
// 이제 오류 발생 시 오류페이지로 /error 를 기본 요청한다.
// 스프링 부트가 자동 등록한 BasicErrorController 는 이 경로를 기본으로 받는다.
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {

        /**
         * HttpStatusCode 에 맞는 ErrorPage 생성.
         **/
        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404");
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");
        ErrorPage errorPageEx = new ErrorPage(RuntimeException.class, "/error-page/500");

        factory.addErrorPages(errorPage404, errorPage500, errorPageEx);

    }

    /**
     * 요약
     * 1. WebServerCustomizer 생성.
     * 2. 예외 종류에 따라서 ErrorPage 를 추가하고
     * 3. 예외 처리용 컨트롤러 ErrorPageController 만듦.
     *
     * But,
     * 스프링 부트는 이런 과정을 모두 기본으로 제공한다.
     * ErrorPage 를 자동으로 등록한다. 이때 /error 라는 경로로 기본 오류 페이지를 설정한다.
     * new ErrorPage("/error") , 상태코드와 예외를 설정하지 않으면 기본 오류 페이지로 사용된다.
     * 서블릿 밖으로 예외가 발생하거나, response.sendError(...) 가 호출되면 모든 오류는 /error 를 호출하게 된다.
     * BasicErrorController 라는 스프링 컨트롤러를 자동으로 등록한다.
     * ErrorPage 에서 등록한 /error 를 매핑해서 처리하는 컨트롤러다.
     * ErrorMvcAutoConfiguration 이라는 클래스가 오류 페이지를 자동으로 등록하는 역할을 한다.
     **/
}

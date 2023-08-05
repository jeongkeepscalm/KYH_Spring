package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

    private final SessionManager sessionManager;


    // @GetMapping("/")
    public String home() {
        return "home";
    }


    //    @GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {

        /**
         * 로그인 쿠키가 없는 사용자는 home 으로 보낸다.
         * 로그인 쿠키가 있어도 회원이 아니면 home 으로 보낸다.
         **/

        if (memberId == null) {
            return "home";
        }

        // 로그인
        Member loginMember = memberRepository.findById(memberId);
        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }


    //    @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {

        Member member = (Member) sessionManager.getSession(request);
        if (member == null) {
            return "home";
        }

        model.addAttribute("member", member);
        return "loginHome";

    }


    //    @GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }


    /**
     * 이미 로그인 된 사용자를 찾을 경우 사용.
     *
     * @SessionAttribute(name = "", required = true/false ) 객체
     * 이 기능은 세션을 생성하지 않는다.
     * <p>
     * 이것은 웹 브라우저가 쿠키를 지원하지 않을 때 쿠키 대신 URL을 통해서 세션을 유지하는 방법이다.
     * 이 방법을 사용하려면 URL에 이 값을 계속 포함해서 전달해야 한다. 타임리프 같은 템플릿은 엔진을 통해서
     * 링크를 걸면 jsessionid 를 URL에 자동으로 포함해준다.
     * 서버 입장에서 웹 브라우저가 쿠키를 지원하는지 하지 않는지 최초에는 판단하지 못하므로, 쿠키 값도 전달하고, URL에 jsessionid 도 함께 전달한다.
     * URL 전달 방식을 끄고 항상 쿠키를 통해서만 세션을 유지하고 싶으면 다음 옵션을 넣어주면 된다.
     * 이렇게 하면 URL에 jsessionid 가 노출되지 않는다.
     * <p>
     * application.properties
     * server.servlet.session.tracking-modes=cookie
     **/
//    @GetMapping("/")
    public String homeLoginV3Spring(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {

        // 세션에 회원 데이터가 없을 경우.
        if (loginMember == null) {
            return "home";
        }

        // 세션이 유지되면 로그인페이지로 이동.
        model.addAttribute("member", loginMember);
        return "loginHome";
    }


    /**
     * @Login 애노테이션이 있으면 직접 만든 ArgumentResolver 가 동작해서 자동으로 세션에 있는 로그인 회원을 찾아주고,
     * 만약 세션에 없다면 null 을 반환하도록 개발해보자.
     **/
    @GetMapping("/")
    public String homeLoginV3ArgumentResolver(@Login Member loginMember, Model model) {

        // 세션에 회원 데이터가 없을 경우.
        if (loginMember == null) {
            return "home";
        }

        // 세션이 유지되면 로그인페이지로 이동.
        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}
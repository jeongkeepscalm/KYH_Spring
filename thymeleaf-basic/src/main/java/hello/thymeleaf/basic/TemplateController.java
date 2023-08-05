package hello.thymeleaf.basic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/template")
public class TemplateController {

    /** 템플릿 조각 **/
    @GetMapping("/fragment")
    public String template() {
        return "template/fragment/fragmentMain";
    }

    /** 레이아웃 1 **/
    @GetMapping("/layout")
    public String layout() {
        return "template/layout/layoutMain";
    }
    /** 레이아웃 2 **/
    @GetMapping("/layoutExtend")
    public String layoutExtends() {
        return "template/layoutExtend/layoutExtendMain";
    }

    /**
     * 레이아웃 1 - 헤더부분을 추가가능,
     * 레이아웃 2 - html 을 통째로 대체도 가능.
     **/

}

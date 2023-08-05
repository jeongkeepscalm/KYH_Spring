package hello.springmvc.basic;

import lombok.Data;


/**
 * @Data
 * @Getter , @Setter , @ToString , @EqualsAndHashCode , @RequiredArgsConstructor 자동완성
 * @ToString : 객체를 출력했을 경우, 객체 안 정보를 문자로 출력.
 * **/
@Data
public class HelloData {

    private String username;
    private int age;

}

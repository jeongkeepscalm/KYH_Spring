package hello.itemservice.domain.item;

import hello.itemservice.item.SaveCheck;
import hello.itemservice.item.UpdateCheck;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/*
    javax.validation 으로 시작하면 특정 구현에 관계없이 제공되는 표준 인터페이스이고,
    org.hibernate.validator 로 시작하면 하이버네이트 validator 구현체를 사용할 때만 제공되는 검증기능이다.
    실무에서 대부분 하이버네이트 validator를 사용하므로 자유롭게 사용해도 된다.
*/

@Data
// @ScriptAssert(lang="javascript", script="_this.price * _this.quantity >= 10000")  // 특정 필드 예외가 아닌 전체 예외처리
public class ItemForGroups {

    @NotNull(groups = UpdateCheck.class) // 수정 시에만 적용.
    private Long id;

    /** Apply Bean Validation Annotation  **/
    /*
    added implementation 'org.springframework.boot:spring-boot-starter-validation' in build.gradle
        @NotBlank : 빈값 + 공백만 있는 경우를 허용하지 않는다.
        @NotNull : null 을 허용하지 않는다.
        @Range(min = 1000, max = 1000000) : 범위 안의 값이어야 한다.
        @Max(9999) : 최대 9999까지만 허용한다.
     */

    @NotBlank(groups = {SaveCheck.class, UpdateCheck.class})
    private String itemName;

    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    @Range(min = 1000, max = 1000000, groups = {SaveCheck.class, UpdateCheck.class})
    private Integer price;

    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    @Max(value = 9999, groups = SaveCheck.class)
    private Integer quantity;

    public ItemForGroups() {
    }

    public ItemForGroups(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}

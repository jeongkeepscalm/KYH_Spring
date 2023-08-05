package hello.itemservice.domain.item;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TestEnum {

    FIRST("첫번째", 200)
    ,SECOND("두번째",300)
    ,THIRD("세번째",400);

    private final String str;
    private final Integer num;





}

package hello.itemservice.domain.item;


import static hello.itemservice.domain.item.TestEnum.FIRST;

/** 상품 종류 **/
public enum ItemType {

    BOOK("도서"), FOOD("식품"), ETC("기타");

    private final String description;


    // @RequiredArgsConstructor
    ItemType(String description) {
        this.description = description;
    }


    // @Getter
    public String getDescription() {
        return description;
    }



}

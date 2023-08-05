package hello.upload.domain;

import lombok.Data;

import java.util.List;

@Data
public class Item {

    private Long id;
    private String itemName; // 상품 이름
    private UploadFile attachFile; // 첨부파일 하나
    private List<UploadFile> imageFiles; // 이미지 파일 여러개

}

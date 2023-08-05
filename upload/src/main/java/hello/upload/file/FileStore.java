package hello.upload.file;


import hello.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    /**
     * 0. Dir 지정 변수 설정.
     * 1. 파일 경로 가져오는 메소드
     * 2. 확장자 가져오는 메소드
     * 3. 기존 파일명 -> 저장할 파일명 변경 메소드
     * 4. 파일 저장 메소드
     * 5. 파일들 저장 메소드
     **/

    @Value("${file.dir}")
    public String fileDir;

    public String getFullPath(String newFileName) {
        return fileDir + newFileName;
    }

    public String getExtension(String originalFileName) {
        int index = originalFileName.lastIndexOf(".");
        return originalFileName.substring(index + 1);
    }

    public String transferFileName(String originalFileName) {
        String extension = getExtension(originalFileName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + extension;
    }

    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String newFileName = transferFileName(originalFilename);

        multipartFile.transferTo(new File(getFullPath(newFileName)));
        return new UploadFile(originalFilename, newFileName);
    }

    public List<UploadFile> storeImageFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileList.add(storeFile(multipartFile));
            }
        }
        return storeFileList;
    }

}

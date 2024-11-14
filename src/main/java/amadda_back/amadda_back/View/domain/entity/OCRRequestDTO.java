package amadda_back.amadda_back.View.domain.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
@DynamicUpdate
public class OCRRequestDTO {

    private MultipartFile file;
    private String storeName;
    private String storeAddress;
}

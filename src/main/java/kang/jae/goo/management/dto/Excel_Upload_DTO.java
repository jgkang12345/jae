package kang.jae.goo.management.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class Excel_Upload_DTO {
    private MultipartFile file;
    private String excel_type;

}

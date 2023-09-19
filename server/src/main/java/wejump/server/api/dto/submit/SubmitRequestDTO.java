package wejump.server.api.dto.submit;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
public class SubmitRequestDTO implements Serializable {

    @NotBlank(message = "파일 첨부는 필수입니다.")
    private MultipartFile file; // 업로드된 파일
    private String comment;
}

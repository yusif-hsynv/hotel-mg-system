package az.orient.hotelmgsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RespStatus {
    private Integer code;
    private String message;

    public static RespStatus getSuccessMessage() {
        return new RespStatus(1, "success");
    }
}

package az.orient.hotelmgsystem.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Response<T> {
    @JsonProperty( "response")
    private T t;
    private RespStatus status;
}

package az.orient.hotelmgsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespCustomer {
    private Long id;
    private String name;
    private String surname;
    private String contactPhone;
    private String seria;
    private Date dob;
}

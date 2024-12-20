package az.orient.hotelmgsystem.dto.request;

import lombok.Data;

import java.util.Date;
@Data
public class ReqCustomer {
    private Long id;
    private String name;
    private String surname;
    private String contactPhone;
    private String seria;
    private Date dob;
}

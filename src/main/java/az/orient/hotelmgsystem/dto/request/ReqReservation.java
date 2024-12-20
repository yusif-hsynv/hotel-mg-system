package az.orient.hotelmgsystem.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class ReqReservation {
    private Long id;
    private Date checkInDate;
    private Date checkOutDate;
    private Long customerId;
    private Long roomId;
}

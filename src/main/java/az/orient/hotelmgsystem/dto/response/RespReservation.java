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
public class RespReservation {
    private Long id;
    private Date checkInDate;
    private Date checkOutDate;
    private Double totalPrice;
    private RespCustomer customer;
    private RespRoom room;
}

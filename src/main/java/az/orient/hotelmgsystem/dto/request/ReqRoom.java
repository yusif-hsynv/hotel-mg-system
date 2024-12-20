package az.orient.hotelmgsystem.dto.request;

import lombok.Data;

@Data
public class ReqRoom {
    private Long id;
    private String roomNumber;
    private Integer capacity;
    private String description;
    private Double price;
    private Boolean available;
    private Long roomTypeId;
}

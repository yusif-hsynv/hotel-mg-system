package az.orient.hotelmgsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespRoom {
    private Long id;
    private String roomNumber;
    private Integer capacity;
    private String description;
    private Double price;
    private Boolean available;
    private RespRoomType roomType;
}

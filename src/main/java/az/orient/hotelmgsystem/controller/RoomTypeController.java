package az.orient.hotelmgsystem.controller;

import az.orient.hotelmgsystem.dto.request.ReqCustomer;
import az.orient.hotelmgsystem.dto.request.ReqRoomType;
import az.orient.hotelmgsystem.dto.response.RespCustomer;
import az.orient.hotelmgsystem.dto.response.RespRoomType;
import az.orient.hotelmgsystem.dto.response.Response;
import az.orient.hotelmgsystem.entity.RoomType;
import az.orient.hotelmgsystem.service.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room_type")
@RequiredArgsConstructor
public class RoomTypeController {
    private final RoomTypeService roomTypeService;

    @GetMapping("/list")
    public Response<List<RespRoomType>> roomTypeList() {
        return roomTypeService.roomTypeList();
    }

    @GetMapping("byId/{id}")
    public Response<RespRoomType> roomTypeById(@PathVariable Long id) {
        return roomTypeService.roomTypeById(id);

    }

    @PostMapping("/create")
    public Response createRoomType(@RequestBody ReqRoomType reqRoomType) {
        return roomTypeService.createRoomType(reqRoomType);
    }

    @PutMapping("/update")
    public Response updateRoomType(@RequestBody ReqRoomType reqRoomType) {
        return roomTypeService.updateRoomType(reqRoomType);
    }

    @PutMapping("/delete")
    public Response deleteRoomType(@RequestParam Long id) {
        return roomTypeService.deleteRoomType(id);
    }
}

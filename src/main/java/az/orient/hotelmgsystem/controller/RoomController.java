package az.orient.hotelmgsystem.controller;

import az.orient.hotelmgsystem.dto.request.ReqRoom;
import az.orient.hotelmgsystem.dto.response.RespRoom;
import az.orient.hotelmgsystem.dto.response.Response;
import az.orient.hotelmgsystem.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/list")
    public Response<List<RespRoom>> roomList() {
        return roomService.roomList();
    }

    @GetMapping("byId/{id}")
    public Response<RespRoom> roomById(@PathVariable Long id) {
        return roomService.roomById(id);

    }

    @PostMapping("/create")
    public Response createRoom(@RequestBody ReqRoom reqRoom) {
        return roomService.createRoom(reqRoom);
    }

    @PutMapping("/update")
    public Response updateRoom(@RequestBody ReqRoom reqRoom) {
        return roomService.updateRoom(reqRoom);
    }

    @PutMapping("/delete")
    public Response deleteRoom(@RequestParam Long id) {
        return roomService.deleteRoom(id);

    }
}

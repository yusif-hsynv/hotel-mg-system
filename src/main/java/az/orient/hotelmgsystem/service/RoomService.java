package az.orient.hotelmgsystem.service;

import az.orient.hotelmgsystem.dto.request.ReqRoom;
import az.orient.hotelmgsystem.dto.response.RespRoom;
import az.orient.hotelmgsystem.dto.response.Response;

import java.util.List;

public interface RoomService {
    Response<List<RespRoom>> roomList();

    Response<RespRoom> roomById(Long id);

    Response createRoom(ReqRoom reqRoom);

    Response updateRoom(ReqRoom reqRoom);

    Response deleteRoom(Long id);
}

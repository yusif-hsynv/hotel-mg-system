package az.orient.hotelmgsystem.service;

import az.orient.hotelmgsystem.dto.request.ReqRoomType;
import az.orient.hotelmgsystem.dto.response.RespRoomType;
import az.orient.hotelmgsystem.dto.response.Response;

import java.util.List;

public interface RoomTypeService {
    Response<List<RespRoomType>> roomTypeList();

    Response<RespRoomType> roomTypeById(Long id);

    Response createRoomType(ReqRoomType reqRoomType);

    Response updateRoomType(ReqRoomType reqRoomType);

    Response deleteRoomType(Long id);
}

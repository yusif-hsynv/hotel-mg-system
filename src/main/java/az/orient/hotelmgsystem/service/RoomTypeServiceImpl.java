package az.orient.hotelmgsystem.service;

import az.orient.hotelmgsystem.dto.request.ReqRoomType;
import az.orient.hotelmgsystem.dto.response.RespRoomType;
import az.orient.hotelmgsystem.dto.response.RespStatus;
import az.orient.hotelmgsystem.dto.response.Response;
import az.orient.hotelmgsystem.entity.RoomType;
import az.orient.hotelmgsystem.enums.EnumAvailableStatus;
import az.orient.hotelmgsystem.exception.ExceptionConstants;
import az.orient.hotelmgsystem.exception.HotelException;
import az.orient.hotelmgsystem.repository.RoomTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomTypeServiceImpl implements RoomTypeService {
    private final RoomTypeRepository roomTypeRepository;


    @Override
    public Response<List<RespRoomType>> roomTypeList() {
        Response<List<RespRoomType>> response = new Response<>();
        try {
            List<RoomType> roomTypeList = roomTypeRepository.findAllByActive(EnumAvailableStatus.ACTIVE.value);
            if (roomTypeList.isEmpty()) {
                throw new HotelException(ExceptionConstants.ROOM_TYPE_NOT_FOUND, "Room type not found");
            }
            List<RespRoomType> respRoomTypeList = roomTypeList.stream().map(this::mapping).toList();
            response.setT(respRoomTypeList);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (HotelException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }

    private RespRoomType mapping(RoomType roomType) {
        return RespRoomType.builder()
                .id(roomType.getId())
                .roomTypeName(roomType.getRoomTypeName())
                .build();
    }


    @Override
    public Response<RespRoomType> roomTypeById(Long id) {
        Response<RespRoomType> response = new Response<>();
        try {
            if (id == null) {
                throw new HotelException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            RoomType roomType = roomTypeRepository.findRoomTypeByIdAndActive(id, EnumAvailableStatus.ACTIVE.value);
            if (roomType == null) {
                throw new HotelException(ExceptionConstants.ROOM_TYPE_NOT_FOUND, "Room type not found");
            }
            RespRoomType respRoomType = mapping(roomType);
            response.setT(respRoomType);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (HotelException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public Response createRoomType(ReqRoomType reqRoomType) {
        Response response = new Response();
        try {
            String roomTypeName = reqRoomType.getRoomTypeName();
            if (roomTypeName == null) {
                throw new HotelException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            RoomType roomType = RoomType.builder()
                    .roomTypeName(roomTypeName)
                    .build();
            roomTypeRepository.save(roomType);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (HotelException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public Response updateRoomType(ReqRoomType reqRoomType) {
        Response response = new Response();
        try {
            Long roomTypeId = reqRoomType.getId();
            String roomTypeName = reqRoomType.getRoomTypeName();
            if (roomTypeId == null || roomTypeName == null) {
                throw new HotelException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            RoomType roomType = roomTypeRepository.findRoomTypeByIdAndActive(roomTypeId, EnumAvailableStatus.ACTIVE.value);
            if (roomType == null) {
                throw new HotelException(ExceptionConstants.ROOM_TYPE_NOT_FOUND, "Room type not found");
            }
            roomType.setRoomTypeName(roomTypeName);
            roomTypeRepository.save(roomType);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (HotelException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }

        return response;
    }

    @Override
    public Response deleteRoomType(Long id) {
        Response response = new Response();
        try {
            if (id == null) {
                throw new HotelException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            RoomType roomType = roomTypeRepository.findRoomTypeByIdAndActive(id, EnumAvailableStatus.ACTIVE.value);
            if (roomType == null) {
                throw new HotelException(ExceptionConstants.ROOM_TYPE_NOT_FOUND, "Room type not found");
            }
            roomType.setActive(EnumAvailableStatus.DEACTIVE.value);
            roomTypeRepository.save(roomType);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (HotelException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }
}

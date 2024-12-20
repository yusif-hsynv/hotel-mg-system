package az.orient.hotelmgsystem.service;

import az.orient.hotelmgsystem.dto.request.ReqRoom;
import az.orient.hotelmgsystem.dto.response.RespRoom;
import az.orient.hotelmgsystem.dto.response.RespRoomType;
import az.orient.hotelmgsystem.dto.response.RespStatus;
import az.orient.hotelmgsystem.dto.response.Response;
import az.orient.hotelmgsystem.entity.Room;
import az.orient.hotelmgsystem.entity.RoomType;
import az.orient.hotelmgsystem.enums.EnumAvailableStatus;
import az.orient.hotelmgsystem.exception.ExceptionConstants;
import az.orient.hotelmgsystem.exception.HotelException;
import az.orient.hotelmgsystem.repository.RoomRepository;
import az.orient.hotelmgsystem.repository.RoomTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;


    @Override
    public Response<List<RespRoom>> roomList() {
        Response<List<RespRoom>> response = new Response<>();
        try {
            List<Room> roomList = roomRepository.findAllByActive(EnumAvailableStatus.ACTIVE.value);
            if (roomList.isEmpty()) {
                throw new HotelException(ExceptionConstants.ROOM_NOT_FOUND, "Room Not Found");
            }
            List<RespRoom> respRoomList = roomList.stream().map(this::convertToRespRoom).toList();
            response.setT(respRoomList);
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

    private RespRoom convertToRespRoom(Room room) {
        RespRoomType respRoomType = RespRoomType.builder()
                .id(room.getRoomType().getId())
                .roomTypeName(room.getRoomType().getRoomTypeName())
                .build();

        return RespRoom.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .capacity(room.getCapacity())
                .description(room.getDescription())
                .price(room.getPrice())
                .available(room.getAvailable())
                .roomType(respRoomType)
                .build();
    }

    @Override
    public Response<RespRoom> roomById(Long id) {
        Response<RespRoom> response = new Response<>();
        try {
            if (id == null) {
                throw new HotelException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid Request Data");
            }
            Room room = roomRepository.findRoomByIdAndActive(id, EnumAvailableStatus.ACTIVE.value);
            if (room == null) {
                throw new HotelException(ExceptionConstants.ROOM_NOT_FOUND, "Room Not Found");
            }
            RespRoom respRoom = convertToRespRoom(room);
            response.setT(respRoom);
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
    public Response createRoom(ReqRoom reqRoom) {
        Response response = new Response();
        try {
            String roomNumber = reqRoom.getRoomNumber();
            Double price = reqRoom.getPrice();
            Integer capacity = reqRoom.getCapacity();
            Long roomTypeId = reqRoom.getRoomTypeId();
            if (roomNumber == null || price == null || capacity == null || roomTypeId == null) {
                throw new HotelException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid Request Data");
            }
            RoomType roomType = roomTypeRepository.findRoomTypeByIdAndActive(roomTypeId, EnumAvailableStatus.ACTIVE.value);
            if (roomType == null) {
                throw new HotelException(ExceptionConstants.ROOM_TYPE_NOT_FOUND, "Room Type Not Found");
            }
            Room room = Room.builder()
                    .roomNumber(roomNumber)
                    .price(price)
                    .capacity(capacity)
                    .description(reqRoom.getDescription())
                    .available(reqRoom.getAvailable())
                    .roomType(roomType)
                    .build();
            roomRepository.save(room);
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
    public Response updateRoom(ReqRoom reqRoom) {
        Response response = new Response();
        try {
            Long roomId = reqRoom.getId();
            String roomNumber = reqRoom.getRoomNumber();
            Long roomTypeId = reqRoom.getRoomTypeId();
            if (roomId == null || roomNumber == null || roomTypeId == null) {
                throw new HotelException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid Request Data");
            }
            Room room = roomRepository.findRoomByIdAndActive(roomId, EnumAvailableStatus.ACTIVE.value);
            if (room == null) {
                throw new HotelException(ExceptionConstants.ROOM_NOT_FOUND, "Room Not Found");
            }
            RoomType roomType = roomTypeRepository.findRoomTypeByIdAndActive(roomTypeId, EnumAvailableStatus.ACTIVE.value);
            if (roomType == null) {
                throw new HotelException(ExceptionConstants.ROOM_TYPE_NOT_FOUND, "Room Type Not Found");
            }
            room.setRoomNumber(roomNumber);
            room.setCapacity(reqRoom.getCapacity());
            room.setDescription(reqRoom.getDescription());
            room.setPrice(reqRoom.getPrice());
            room.setAvailable(reqRoom.getAvailable());
            room.setRoomType(roomType);
            roomRepository.save(room);
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
    public Response deleteRoom(Long id) {
        Response response = new Response();
        try {
            if (id == null) {
                throw new HotelException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid Request Data");
            }
            Room room = roomRepository.findRoomByIdAndActive(id, EnumAvailableStatus.ACTIVE.value);
            if (room == null) {
                throw new HotelException(ExceptionConstants.ROOM_NOT_FOUND, "Room Not Found");
            }
            room.setActive(EnumAvailableStatus.DEACTIVE.value);
            roomRepository.save(room);
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

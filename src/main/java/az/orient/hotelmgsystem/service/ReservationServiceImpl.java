package az.orient.hotelmgsystem.service;

import az.orient.hotelmgsystem.dto.request.ReqReservation;
import az.orient.hotelmgsystem.dto.response.*;
import az.orient.hotelmgsystem.entity.Customer;
import az.orient.hotelmgsystem.entity.Reservation;
import az.orient.hotelmgsystem.entity.Room;
import az.orient.hotelmgsystem.enums.EnumAvailableStatus;
import az.orient.hotelmgsystem.exception.ExceptionConstants;
import az.orient.hotelmgsystem.exception.HotelException;
import az.orient.hotelmgsystem.repository.CustomerRepository;
import az.orient.hotelmgsystem.repository.ReservationRepository;
import az.orient.hotelmgsystem.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final RoomRepository roomRepository;


    @Override
    public Response<List<RespReservation>> reservationList() {
        Response<List<RespReservation>> response = new Response<>();
        try {
            List<Reservation> reservationList = reservationRepository.findAllByActive(EnumAvailableStatus.ACTIVE.value);
            if (reservationList.isEmpty()) {
                throw new HotelException(ExceptionConstants.RESERVATION_NOT_FOUND, "Reservation not found");
            }
            List<RespReservation> respReservationList = reservationList.stream().map(this::convertToRespReservation).toList();
            response.setT(respReservationList);
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

    private RespReservation convertToRespReservation(Reservation reservation) {
        RespCustomer respCustomer = RespCustomer.builder()
                .id(reservation.getCustomer().getId())
                .name(reservation.getCustomer().getName())
                .surname(reservation.getCustomer().getSurname())
                .contactPhone(reservation.getCustomer().getContactPhone())
                .seria(reservation.getCustomer().getSeria())
                .dob(reservation.getCustomer().getDob())
                .build();

        RespRoom respRoom = RespRoom.builder()
                .id(reservation.getRoom().getId())
                .roomNumber(reservation.getRoom().getRoomNumber())
                .capacity(reservation.getRoom().getCapacity())
                .description(reservation.getRoom().getDescription())
                .price(reservation.getRoom().getPrice())
                .available(reservation.getRoom().getAvailable())
                .roomType(RespRoomType.builder()
                        .id(reservation.getRoom().getRoomType().getId())
                        .roomTypeName(reservation.getRoom().getRoomType().getRoomTypeName())
                        .build())
                .build();

        return RespReservation.builder()
                .id(reservation.getId())
                .checkInDate(reservation.getCheckInDate())
                .checkOutDate(reservation.getCheckOutDate())
                .totalPrice(reservation.getTotalPrice())
                .customer(respCustomer)
                .room(respRoom)
                .build();

    }

    @Override
    public Response<RespReservation> reservationById(Long id) {
        Response<RespReservation> response = new Response<>();
        try {
            if (id == null) {
                throw new HotelException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Reservation reservation = reservationRepository.findReservationByIdAndActive(id, EnumAvailableStatus.ACTIVE.value);
            if (reservation == null) {
                throw new HotelException(ExceptionConstants.RESERVATION_NOT_FOUND, "Reservation not found");
            }
            RespReservation respReservation = convertToRespReservation(reservation);
            response.setT(respReservation);
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
    public Response createReservation(ReqReservation reqReservation) {
        Response response = new Response();
        try {
            Date checkInDate = reqReservation.getCheckInDate();
            Date checkOutDate = reqReservation.getCheckOutDate();
            Long customerId = reqReservation.getCustomerId();
            Long roomId = reqReservation.getRoomId();
            if (checkInDate == null || checkOutDate == null || customerId == null || roomId == null) {
                throw new HotelException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }

            Customer customer = customerRepository.findCustomerByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.value);
            if (customer == null) {
                throw new HotelException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            Room room = roomRepository.findRoomByIdAndActive(roomId, EnumAvailableStatus.ACTIVE.value);
            if (room == null) {
                throw new HotelException(ExceptionConstants.ROOM_NOT_FOUND, "Room not found");
            }
            if (!checkOutDate.after(checkInDate)) {
                throw new HotelException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid check-in or check-out date");
            }
            List<Reservation> overlappingReservations = reservationRepository.findByRoomIdAndActiveAndCheckInDateBeforeAndCheckOutDateAfter(roomId, EnumAvailableStatus.ACTIVE.value, checkOutDate, checkInDate);
            if (!overlappingReservations.isEmpty()) {
                throw new HotelException(ExceptionConstants.ROOM_NOT_AVAILABLE, "Room is not available for the selected dates");
            }


            // Calculate total price based on the duration of stay
            long duration = checkOutDate.getTime() - checkInDate.getTime();
            long days = (duration / (1000 * 60 * 60 * 24));
            double totalPrice = days * room.getPrice();

            Reservation reservation = Reservation.builder()
                    .checkInDate(checkInDate)
                    .checkOutDate(checkOutDate)
                    .totalPrice(totalPrice)
                    .customer(customer)
                    .room(room)
                    .build();
            reservationRepository.save(reservation);
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
    public Response updateReservation(ReqReservation reqReservation) {
        Response response = new Response();
        try {
            Long reservationId = reqReservation.getId();
            Date checkInDate = reqReservation.getCheckInDate();
            Date checkOutDate = reqReservation.getCheckOutDate();
            Long customerId = reqReservation.getCustomerId();
            Long roomId = reqReservation.getRoomId();
            if (reservationId == null || checkInDate == null || checkOutDate == null || customerId == null || roomId == null) {
                throw new HotelException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Reservation reservation = reservationRepository.findReservationByIdAndActive(reservationId, EnumAvailableStatus.ACTIVE.value);
            if (reservation == null) {
                throw new HotelException(ExceptionConstants.RESERVATION_NOT_FOUND, "Reservation not found");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.value);
            if (customer == null) {
                throw new HotelException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            Room room = roomRepository.findRoomByIdAndActive(roomId, EnumAvailableStatus.ACTIVE.value);
            if (room == null) {
                throw new HotelException(ExceptionConstants.ROOM_NOT_FOUND, "Room not found");
            }
            if (!checkOutDate.after(checkInDate)) {
                throw new HotelException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid check-in or check-out date");
            }
            List<Reservation> overlappingReservations = reservationRepository.findByRoomIdAndActiveAndCheckInDateBeforeAndCheckOutDateAfter(roomId, EnumAvailableStatus.ACTIVE.value, checkOutDate, checkInDate);
            if (!overlappingReservations.isEmpty()) {
                throw new HotelException(ExceptionConstants.ROOM_NOT_AVAILABLE, "Room is not available for the selected dates");
            }

            long duration = checkOutDate.getTime() - checkInDate.getTime();
            long days = (duration / (1000 * 60 * 60 * 24));
            double totalPrice = days * room.getPrice();

            reservation.setCheckInDate(checkInDate);
            reservation.setCheckOutDate(checkOutDate);
            reservation.setTotalPrice(totalPrice);
            reservation.setCustomer(customer);
            reservation.setRoom(room);
            reservationRepository.save(reservation);
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
    public Response deleteReservation(Long id) {
        Response response = new Response();
        try {
            if (id == null) {
                throw new HotelException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Reservation reservation = reservationRepository.findReservationByIdAndActive(id, EnumAvailableStatus.ACTIVE.value);
            if (reservation == null) {
                throw new HotelException(ExceptionConstants.RESERVATION_NOT_FOUND, "Reservation not found");
            }
            reservation.setActive(EnumAvailableStatus.DEACTIVE.value);
            reservationRepository.save(reservation);
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

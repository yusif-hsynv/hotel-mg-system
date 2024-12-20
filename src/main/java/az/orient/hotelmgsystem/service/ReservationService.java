package az.orient.hotelmgsystem.service;

import az.orient.hotelmgsystem.dto.request.ReqReservation;
import az.orient.hotelmgsystem.dto.response.RespReservation;
import az.orient.hotelmgsystem.dto.response.Response;

import java.util.List;

public interface ReservationService {
    Response<List<RespReservation>> reservationList();

    Response<RespReservation> reservationById(Long id);

    Response createReservation(ReqReservation reqReservation);

    Response updateReservation(ReqReservation reqReservation);

    Response deleteReservation(Long id);
}

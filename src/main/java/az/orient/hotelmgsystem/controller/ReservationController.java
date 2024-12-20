package az.orient.hotelmgsystem.controller;

import az.orient.hotelmgsystem.dto.request.ReqReservation;
import az.orient.hotelmgsystem.dto.response.RespReservation;
import az.orient.hotelmgsystem.dto.response.Response;
import az.orient.hotelmgsystem.entity.Reservation;
import az.orient.hotelmgsystem.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("/list")
    public Response<List<RespReservation>> reservationList() {
        return reservationService.reservationList();
    }

    @GetMapping("byId/{id}")
    public Response<RespReservation> reservationById(@PathVariable Long id) {
        return reservationService.reservationById(id);
    }

    @PostMapping("/create")
    public Response createReservation(@RequestBody ReqReservation reqReservation) {
        return reservationService.createReservation(reqReservation);
    }

    @PutMapping("/update")
    public Response updateReservation(@RequestBody ReqReservation reqReservation) {
        return reservationService.updateReservation(reqReservation);
    }

    @PutMapping("/delete")
    public Response deleteReservation(@RequestParam Long id) {
        return reservationService.deleteReservation(id);
    }
}

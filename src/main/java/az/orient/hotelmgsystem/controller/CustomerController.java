package az.orient.hotelmgsystem.controller;

import az.orient.hotelmgsystem.dto.request.ReqCustomer;
import az.orient.hotelmgsystem.dto.response.RespCustomer;
import az.orient.hotelmgsystem.dto.response.Response;
import az.orient.hotelmgsystem.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/list")
    public Response<List<RespCustomer>> customerList() {
        return customerService.customerList();
    }

    @GetMapping("byId/{id}")
    public Response<RespCustomer> customerById(@PathVariable Long id) {
        return customerService.customerById(id);

    }

    @PostMapping("/create")
    public Response createCustomer(@RequestBody ReqCustomer reqCustomer) {
        return customerService.createCustomer(reqCustomer);
    }

    @PutMapping("/update")
    public Response updateCustomer(@RequestBody ReqCustomer reqCustomer) {
        return customerService.updateCustomer(reqCustomer);
    }

    @PutMapping("/delete")
    public Response deleteCustomer(@RequestParam Long id) {
        return customerService.deleteCustomer(id);
    }
}

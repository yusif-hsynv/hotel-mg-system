package az.orient.hotelmgsystem.service;

import az.orient.hotelmgsystem.dto.request.ReqCustomer;
import az.orient.hotelmgsystem.dto.response.RespCustomer;
import az.orient.hotelmgsystem.dto.response.Response;

import java.util.List;

public interface CustomerService {
    Response<List<RespCustomer>> customerList();

    Response<RespCustomer> customerById(Long id);

    Response createCustomer(ReqCustomer reqCustomer);

    Response updateCustomer(ReqCustomer reqCustomer);

    Response deleteCustomer(Long id);
}

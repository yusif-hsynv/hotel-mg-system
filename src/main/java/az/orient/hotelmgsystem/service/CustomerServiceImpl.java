package az.orient.hotelmgsystem.service;

import az.orient.hotelmgsystem.dto.request.ReqCustomer;
import az.orient.hotelmgsystem.dto.response.RespCustomer;
import az.orient.hotelmgsystem.dto.response.RespStatus;
import az.orient.hotelmgsystem.dto.response.Response;
import az.orient.hotelmgsystem.entity.Customer;
import az.orient.hotelmgsystem.enums.EnumAvailableStatus;
import az.orient.hotelmgsystem.exception.ExceptionConstants;
import az.orient.hotelmgsystem.exception.HotelException;
import az.orient.hotelmgsystem.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Response<List<RespCustomer>> customerList() {
        Response<List<RespCustomer>> response = new Response<>();
        try {
            List<Customer> customerList = customerRepository.findAllByActive(EnumAvailableStatus.ACTIVE.value);
            if (customerList.isEmpty()) {
                throw new HotelException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            List<RespCustomer> respCustomerList = customerList.stream().map(this::mapping).toList();
            response.setT(respCustomerList);
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

    private RespCustomer mapping(Customer customer) {
        return RespCustomer.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surname(customer.getSurname())
                .contactPhone(customer.getContactPhone())
                .dob(customer.getDob())
                .seria(customer.getSeria())
                .build();
    }

    @Override
    public Response<RespCustomer> customerById(Long id) {
        Response<RespCustomer> response = new Response<>();
        try {
            if (id == null) {
                throw new HotelException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(id, EnumAvailableStatus.ACTIVE.value);
            if (customer == null) {
                throw new HotelException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            RespCustomer respCustomer = mapping(customer);
            response.setT(respCustomer);
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
    public Response createCustomer(ReqCustomer reqCustomer) {
        Response response = new Response();
        try {
            String name = reqCustomer.getName();
            String surname = reqCustomer.getSurname();
            if (name == null || surname == null) {
                throw new HotelException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Customer customer = Customer.builder()
                    .name(name)
                    .surname(surname)
                    .contactPhone(reqCustomer.getContactPhone())
                    .seria(reqCustomer.getSeria())
                    .dob(reqCustomer.getDob())
                    .build();
            customerRepository.save(customer);
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
    public Response updateCustomer(ReqCustomer reqCustomer) {
        Response response = new Response();
        try {
            Long customerId = reqCustomer.getId();
            String name = reqCustomer.getName();
            String surname = reqCustomer.getSurname();
            if (customerId == null || name == null || surname == null) {
                throw new HotelException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Customer customer= customerRepository.findCustomerByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.value);
            if (customer == null) {
                throw new HotelException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            customer.setName(name);
            customer.setSurname(surname);
            customer.setContactPhone(reqCustomer.getContactPhone());
            customer.setSeria(reqCustomer.getSeria());
            customer.setDob(reqCustomer.getDob());
            customerRepository.save(customer);
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
    public Response deleteCustomer(Long id) {
        Response response = new Response();
        try {
            if (id == null) {
                throw new HotelException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(id, EnumAvailableStatus.ACTIVE.value);
            if (customer == null) {
                throw new HotelException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            customer.setActive(EnumAvailableStatus.DEACTIVE.value);
            customerRepository.save(customer);
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

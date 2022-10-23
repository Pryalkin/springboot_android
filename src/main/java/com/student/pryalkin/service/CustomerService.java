package com.student.pryalkin.service;

import com.student.pryalkin.exception.CustomerNotFoundException;
import com.student.pryalkin.model.Customer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CustomerService {

    Customer addCustomer(Customer customer, MultipartFile file) throws IOException;
    List<Customer> getCustomers();
    List<Customer> enrollCustomer(Long id, Boolean isStatus) throws CustomerNotFoundException;
    List<Customer> deleteCustomer(Long id) throws CustomerNotFoundException;
}

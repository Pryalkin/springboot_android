package com.student.pryalkin.controller;

import com.student.pryalkin.exception.CustomerNotFoundException;
import com.student.pryalkin.model.Customer;
import com.student.pryalkin.model.ExpandCustomer;
import com.student.pryalkin.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RestControllerAdvice
@AllArgsConstructor
@RequestMapping("/")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/customer/add")
    public void addUser(@RequestBody ExpandCustomer expandCustomer) throws IOException {
        customerService.addCustomer(expandCustomer);
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getUsers(){
        return new ResponseEntity<>(customerService.getCustomers(), HttpStatus.OK);
    }

    @GetMapping("/customer/enroll/{id}")
    public ResponseEntity<List<Customer>> enrollEntrant(@PathVariable Long id) throws CustomerNotFoundException {
        return new ResponseEntity<>(customerService.enrollCustomer(id, true), HttpStatus.OK);
    }

    @GetMapping("/customer/{id}/delete")
    public ResponseEntity<List<Customer>> deleteEntrant(@PathVariable Long id) throws CustomerNotFoundException {
        return new ResponseEntity<>(customerService.deleteCustomer(id), HttpStatus.OK);
    }

}

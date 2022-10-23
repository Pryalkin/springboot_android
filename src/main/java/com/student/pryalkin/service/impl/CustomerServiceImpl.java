package com.student.pryalkin.service.impl;

import com.student.pryalkin.exception.CustomerNotFoundException;
import com.student.pryalkin.model.Customer;
import com.student.pryalkin.model.ExpandCustomer;
import com.student.pryalkin.repository.CustomerRepo;
import com.student.pryalkin.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.student.pryalkin.contstants.Constants.CUSTOMER_NOT_FOUND_EXCEPTION;
import static com.student.pryalkin.contstants.FileConstant.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;

    @Override
    public Customer addCustomer(ExpandCustomer expandCustomer) throws IOException {
        Customer customer = expandCustomer.getCustomer();
        saveProfileImage(customer, expandCustomer.getProfileImage());
        return customerRepo.save(customer);
    }

    private void saveProfileImage(Customer customer, MultipartFile profileImage) throws IOException {
        if (profileImage != null){
            Path userFolder = Paths.get(USER_FOLDER + customer.getName() + customer.getSurname() + customer.getPatronymic()).toAbsolutePath().normalize();
            if (!Files.exists(userFolder)){
                Files.createDirectories(userFolder);
            }
            Files.deleteIfExists(Paths.get(userFolder + customer.getName() + customer.getSurname() + customer.getPatronymic() + DOT + JPG_EXTENSION));
            Files.copy(profileImage.getInputStream(), userFolder.resolve(customer.getName() + customer.getSurname() + customer.getPatronymic() + DOT + JPG_EXTENSION), REPLACE_EXISTING);
            customer.setImageUrl(setProfileImageUrl(customer.getName() + customer.getSurname() + customer.getPatronymic()));
            customerRepo.save(customer);
        }
    }

    private String setProfileImageUrl(String customer) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().
                path(USER_IMAGE_PATH + customer + FORWARD_SLASH + customer + DOT + JPG_EXTENSION).toUriString();
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepo.findAll();
    }

    @Override
    public List<Customer> enrollCustomer(Long id, Boolean isStatus) throws CustomerNotFoundException {
        Customer customer = customerRepo.findById(id).orElseThrow(() -> new CustomerNotFoundException(CUSTOMER_NOT_FOUND_EXCEPTION));
        customer.setStatus(isStatus);
        customerRepo.save(customer);
        return customerRepo.findAll();
    }

    @Override
    public List<Customer> deleteCustomer(Long id) throws CustomerNotFoundException {
        Customer entrant = customerRepo.findById(id).orElseThrow(() -> new CustomerNotFoundException(CUSTOMER_NOT_FOUND_EXCEPTION));
        customerRepo.deleteById(entrant.getId());
        return customerRepo.findAll();
    }
}

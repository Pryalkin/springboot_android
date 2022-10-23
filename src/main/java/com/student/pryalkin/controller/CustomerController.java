package com.student.pryalkin.controller;

import com.dropbox.core.v2.files.FileMetadata;
import com.student.pryalkin.exception.CustomerNotFoundException;
import com.student.pryalkin.model.Customer;
import com.student.pryalkin.service.CustomerService;
import com.student.pryalkin.service.DropboxService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.student.pryalkin.contstants.FileConstant.FORWARD_SLASH;
import static com.student.pryalkin.contstants.FileConstant.USER_FOLDER;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@RestController
@RestControllerAdvice
@AllArgsConstructor
@RequestMapping("/")
public class CustomerController {

    private final CustomerService customerService;
    private final DropboxService dropboxService;

    @PostMapping("/customer/add")
    public void addUser(@RequestPart Customer customer,
                        @RequestPart(required = false) MultipartFile file) throws IOException {
        customerService.addCustomer(customer, file);
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

    @GetMapping(path = "/image", produces = IMAGE_JPEG_VALUE)
    public byte[] getProfileImage(@RequestParam String username) throws IOException {
        FileMetadata fileMetadata = dropboxService.getFileDetails(username);
        return fileMetadata.getPathLower().getBytes();
    }

}

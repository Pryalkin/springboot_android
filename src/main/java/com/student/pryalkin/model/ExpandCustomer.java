package com.student.pryalkin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class ExpandCustomer {

    private Customer customer;
    private MultipartFile profileImage;

}

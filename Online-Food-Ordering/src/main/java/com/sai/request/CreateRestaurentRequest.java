package com.sai.request;

import com.sai.model.Address;
import com.sai.model.ContactInformation;
import lombok.Data;

import java.util.List;

@Data
public class CreateRestaurentRequest {

    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private String openingHours;
    private List<String> images;

}

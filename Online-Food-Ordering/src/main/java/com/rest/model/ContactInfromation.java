package com.rest.model;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ContactInfromation {

    private String email;
    private String mobile;
    private String twitter;
    private String instagram;


}

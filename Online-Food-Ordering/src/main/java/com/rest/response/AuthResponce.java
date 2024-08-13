package com.rest.response;

import com.rest.model.USER_ROLE;
import lombok.Data;

@Data
public class AuthResponce {

    private String jwt;
    private String message;

    private USER_ROLE role;
}

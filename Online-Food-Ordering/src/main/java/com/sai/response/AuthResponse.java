package com.sai.response;

import com.sai.model.USER_ROLE;
import com.sai.model.User;
import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;
    private String message;
    private USER_ROLE role;


}

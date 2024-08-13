package com.rest.service;

import com.rest.model.User;

public interface UserService {
    public User findUserByJwtToken(String jwt) throws Exception;

    public User findUserByEmail(String string) throws Exception;
}

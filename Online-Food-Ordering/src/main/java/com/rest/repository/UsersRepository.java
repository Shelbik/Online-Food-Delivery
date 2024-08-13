package com.rest.repository;

import com.rest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User,Long> {

    public User findByEmail(String username);
}

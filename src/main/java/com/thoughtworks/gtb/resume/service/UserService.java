package com.thoughtworks.gtb.resume.service;

import com.thoughtworks.gtb.resume.domain.User;
import com.thoughtworks.gtb.resume.repository.UserRespository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRespository userRespository;

    public UserService(UserRespository userRespository) {
        this.userRespository = userRespository;
    }

    public int addUser(User user) {
        userRespository.save(user);
        return user.getId();
    }
}
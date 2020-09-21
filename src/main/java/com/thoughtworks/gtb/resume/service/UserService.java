package com.thoughtworks.gtb.resume.service;

import com.thoughtworks.gtb.resume.domain.User;
import com.thoughtworks.gtb.resume.exception.UserNotExistException;
import com.thoughtworks.gtb.resume.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRespository;

    public UserService(UserRepository userRepository) {
        this.userRespository = userRepository;
    }

    public long addUser(User user) {
        userRespository.save(user);
        return user.getId();
    }


    public User getUser(long id) throws UserNotExistException {
        Optional<User> user = userRespository.findById(id);
        if (!user.isPresent())
            throw new UserNotExistException();
        return user.get();
    }
}

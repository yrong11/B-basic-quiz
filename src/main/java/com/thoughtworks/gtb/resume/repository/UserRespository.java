package com.thoughtworks.gtb.resume.repository;

import com.thoughtworks.gtb.resume.domain.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class UserRespository {
    private static final Map<Integer, User> userMap = new HashMap<>();
    private static final AtomicInteger atoInteger = new AtomicInteger(1);

    public void save(User user){
        user.setId(atoInteger.get());
        userMap.put(user.getId(), user);
        atoInteger.set(atoInteger.get() + 1);
    }

    public User findById(int id){
        return userMap.get(id);
    }
}

package com.thoughtworks.gtb.resume.repository;

import com.thoughtworks.gtb.resume.domain.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class UserRespository {
    private static final Map<Long, User> userMap = new HashMap<>();
    private static final AtomicLong atomicLong = new AtomicLong(1);

    public void save(User user){
        user.setId(atomicLong.get());
        userMap.put(user.getId(), user);
        atomicLong.set(atomicLong.get() + 1);
    }

    public User findById(long id){
        return userMap.get(id);
    }
}

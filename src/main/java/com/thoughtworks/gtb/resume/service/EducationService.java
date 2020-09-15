package com.thoughtworks.gtb.resume.service;

import com.thoughtworks.gtb.resume.domain.Education;
import com.thoughtworks.gtb.resume.domain.User;
import com.thoughtworks.gtb.resume.exception.EducationException;
import com.thoughtworks.gtb.resume.exception.UserNotExistException;
import com.thoughtworks.gtb.resume.repository.EducationRespository;
import com.thoughtworks.gtb.resume.repository.UserRespository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class EducationService {

    private final UserRespository userRespository;
    private final EducationRespository educationRespository;

    public EducationService(UserRespository userRespository, EducationRespository educationRespository) {
        this.userRespository = userRespository;
        this.educationRespository = educationRespository;
    }

    public void addEducations(long userId, Education education) throws UserNotExistException, EducationException {
        checkUserExist(userId);
        education.setUserId(userId);
        if(educationRespository.isExistEducations(userId) && educationRespository.findEducationsByUserId(userId).contains(education)){
            throw new EducationException("add education repeat");
        }
        educationRespository.save(education);
    }

    public List<Education> getEducations(long userId) throws UserNotExistException {
        checkUserExist(userId);
        return educationRespository.findEducationsByUserId(userId);
    }


    public User checkUserExist(long userId) throws UserNotExistException {
        User user = userRespository.findById(userId);
        if (user == null)
            throw new UserNotExistException();
        return user;
    }
}

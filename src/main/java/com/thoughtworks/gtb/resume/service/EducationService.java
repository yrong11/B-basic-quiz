package com.thoughtworks.gtb.resume.service;

import com.thoughtworks.gtb.resume.domain.Education;
import com.thoughtworks.gtb.resume.domain.User;
import com.thoughtworks.gtb.resume.exception.EducationException;
import com.thoughtworks.gtb.resume.exception.UserNotExistException;
import com.thoughtworks.gtb.resume.repository.EducationRespository;
import com.thoughtworks.gtb.resume.repository.UserRespository;
import org.springframework.stereotype.Service;

@Service
public class EducationService {

    private final UserRespository userRespository;
    private final EducationRespository educationRespository;

    public EducationService(UserRespository userRespository, EducationRespository educationRespository) {
        this.userRespository = userRespository;
        this.educationRespository = educationRespository;
    }

    public void addEducations(int userId, Education education) throws UserNotExistException, EducationException {
        User user = userRespository.findById(userId);
        if (user == null)
            throw new UserNotExistException();
        education.setUserId(userId);
        if(educationRespository.isExistEducations(userId) && educationRespository.findEducationsByUserId(userId).contains(education)){
            throw new EducationException("add education repeat");
        }
        educationRespository.save(education);
    }
}

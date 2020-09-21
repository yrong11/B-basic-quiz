package com.thoughtworks.gtb.resume.service;

import com.thoughtworks.gtb.resume.domain.Education;
import com.thoughtworks.gtb.resume.domain.User;
import com.thoughtworks.gtb.resume.exception.EducationException;
import com.thoughtworks.gtb.resume.exception.UserNotExistException;
import com.thoughtworks.gtb.resume.repository.EducationRepository;
import com.thoughtworks.gtb.resume.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EducationService {

    private final UserRepository userRepository;
    private final EducationRepository educationRepository;

    public EducationService(UserRepository userRepository, EducationRepository educationRepository) {
        this.userRepository = userRepository;
        this.educationRepository = educationRepository;
    }

    public void addEducations(long userId, Education education) throws UserNotExistException, EducationException {
        User user = checkUserExist(userId);
        education.setUser(user);
        if(educationRepository.findById(education.getId()).isPresent()){
            throw new EducationException("add education repeat");
        }
        educationRepository.save(education);
    }

    public List<Education> getEducations(long userId) throws UserNotExistException {
        checkUserExist(userId);
        return educationRepository.findAllByUserId(userId);
    }


    public User checkUserExist(long userId) throws UserNotExistException {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent())
            throw new UserNotExistException();
        return user.get();
    }
}

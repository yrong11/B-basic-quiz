package com.thoughtworks.gtb.resume.repository;

import com.thoughtworks.gtb.resume.domain.Education;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EducationRespository {
    private static final Map<Long, List<Education>> educationMap = new HashMap<>();

    public void save(Education education) {
        List<Education> educationList;
        if (isExistEducations(education.getUserId()))
            educationList = educationMap.get(education.getUserId());
        else
            educationList = new ArrayList<>();
        educationList.add(education);
        educationMap.put(education.getUserId(), educationList);
    }

    public boolean isExistEducations(long userId) {
        return educationMap.containsKey(userId);
    }

    public List<Education> findEducationsByUserId(long userId) {
        Collections.sort(educationMap.get(userId));
        return educationMap.get(userId);
    }
}

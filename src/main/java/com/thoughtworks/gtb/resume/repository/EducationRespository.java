package com.thoughtworks.gtb.resume.repository;

import com.thoughtworks.gtb.resume.domain.Education;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EducationRespository {
    private static final Map<Integer, List<Education>> educationMap = new HashMap<>();

    public void save(Education education) {
        List<Education> educationList;
        if (isExistEducations(education.getUserId()))
            educationList = educationMap.get(education.getUserId());
        else
            educationList = new ArrayList<>();
        educationList.add(education);
        educationMap.put(education.getUserId(), educationList);
    }

    public boolean isExistEducations(int userId) {
        return educationMap.containsKey(userId);
    }

    public List<Education> findEducationsByUserId(int userId) {
        return educationMap.get(userId);
    }
}

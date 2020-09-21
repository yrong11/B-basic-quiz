package com.thoughtworks.gtb.resume.repository;

import com.thoughtworks.gtb.resume.domain.Education;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationRepository extends CrudRepository<Education, Integer> {
    List<Education> findAllByUserId(long userId);
}

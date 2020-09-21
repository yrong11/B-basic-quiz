package com.thoughtworks.gtb.resume.repository;

import com.thoughtworks.gtb.resume.domain.Education;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationRepository extends CrudRepository<Education, Integer> {
}

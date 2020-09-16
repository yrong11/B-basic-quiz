package com.thoughtworks.gtb.resume.controller;

import com.thoughtworks.gtb.resume.domain.Education;
import com.thoughtworks.gtb.resume.exception.EducationException;
import com.thoughtworks.gtb.resume.exception.UserNotExistException;
import com.thoughtworks.gtb.resume.service.EducationService;
import com.thoughtworks.gtb.resume.utils.json.EmptyJsonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/users/{userId}/educations")
public class EducationController {

    private final EducationService educationService;

    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    @PostMapping("")
    public ResponseEntity addEducations(@Valid @RequestBody Education education, @PathVariable int userId) throws UserNotExistException, EducationException {
        educationService.addEducations(userId, education);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity getEducations(@PathVariable long userId) throws UserNotExistException {
        List<Education> educationList = educationService.getEducations(userId);
        // GTB: - education 为空的时，直接返回空的列表就行，不需要使用 EmptyJsonResponse
        return educationList == null ? ResponseEntity.ok(new EmptyJsonResponse()) : ResponseEntity.ok(educationList);
    }
}

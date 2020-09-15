package com.thoughtworks.gtb.resume.controller;

import com.thoughtworks.gtb.resume.domain.Education;
import com.thoughtworks.gtb.resume.domain.User;
import com.thoughtworks.gtb.resume.exception.EducationException;
import com.thoughtworks.gtb.resume.exception.UserNotExistException;
import com.thoughtworks.gtb.resume.service.EducationService;
import com.thoughtworks.gtb.resume.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity addUser(@Valid @RequestBody User user){
        long id = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping("{id}")
    public ResponseEntity getUser(@PathVariable long id) throws UserNotExistException {
        User user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }

}

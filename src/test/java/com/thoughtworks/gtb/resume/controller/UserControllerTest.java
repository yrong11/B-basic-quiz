package com.thoughtworks.gtb.resume.controller;

import com.thoughtworks.gtb.resume.domain.User;
import com.thoughtworks.gtb.resume.exception.ErrorMsg;
import com.thoughtworks.gtb.resume.exception.ErrorResult;
import com.thoughtworks.gtb.resume.exception.UserNotExistException;
import com.thoughtworks.gtb.resume.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @MockBean
    private UserService userService;
    @Autowired
    private TestRestTemplate restTemplate;

    private User firstUser;

    @BeforeEach
    public void setup() {
        firstUser = User.builder()
                .id(1L)
                .name("yurong")
                .age(18)
                .avatar("https://inews.gtimg.com/newsapp_match/0/3581582328/0")
                .description("happy every day!")
                .build();
    }

    @AfterEach
    public void afterEach() {
        Mockito.reset(userService);
    }

    @Nested
    class GetUserById {

        @Nested
        class WhenUserIdExists {

            @Test
            public void should_return_user_by_id() throws UserNotExistException {
                when(userService.getUser(1L)).thenReturn(firstUser);
                ResponseEntity<User> responseEntity = restTemplate.getForEntity("/users/{id}", User.class, 1L);

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(responseEntity.getBody()).isEqualTo(firstUser);
                verify(userService).getUser(1L);
            }
        }

        @Nested
        class WhenUserIdNotExists {

            @Test
            public void should_throw_user_not_exist_exception() throws UserNotExistException {
                when(userService.getUser(1L)).thenThrow(new UserNotExistException());

                ResponseEntity<ErrorResult> responseEntity = restTemplate.getForEntity("/users/{id}", ErrorResult.class, 1L);
                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getMessage()).isEqualTo("user not exist");
                verify(userService).getUser(1L);
            }
        }
    }

    @Nested
    class AddUser {

        @Nested
        class WhenUserRequestValid {

            @Test
            public void should_add_user_success() {
                when(userService.addUser(firstUser)).thenReturn(firstUser.getId());
                ResponseEntity<Long> responseEntity = restTemplate.postForEntity("/users", firstUser, Long.class);

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
                assertThat(responseEntity.getBody()).isEqualTo(firstUser.getId());
            }
        }

        @Nested
        class WhenUserRequestNoValid {

            @Test
            public void should_return_name_not_empty_when_user_name_empty() {
                firstUser.setName("");
                ResponseEntity<ErrorResult> responseEntity = restTemplate.postForEntity("/users", firstUser, ErrorResult.class);

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
                assertThat(responseEntity.getBody().getMessage()).isEqualTo(ErrorMsg.USER_NAME_NOT_EMPTY);
            }

            @Test
            public void should_return_age_not_valid() {
                firstUser.setAge(1);
                ResponseEntity<ErrorResult> responseEntity = restTemplate.postForEntity("/users", firstUser, ErrorResult.class);

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getMessage()).isEqualTo("最小不能小于16");
            }

            @Test
            public void should_return_avatar_url_not_valid() {
                firstUser.setAvatar("1212121212121");
                ResponseEntity<ErrorResult> responseEntity = restTemplate.postForEntity("/users", firstUser, ErrorResult.class);

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getMessage()).isEqualTo(ErrorMsg.USER_AVATAR_FORMAT_INCORRECT);
            }

            @Test
            public void should_return_avatar_not_in_range() {
                firstUser.setAvatar("http:");
                ResponseEntity<ErrorResult> responseEntity = restTemplate.postForEntity("/users", firstUser, ErrorResult.class);

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getMessage()).isEqualTo(ErrorMsg.USER_AVATAR_LENGTH_INVALID);
            }
        }
    }



}
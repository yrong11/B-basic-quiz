package com.thoughtworks.gtb.resume.controller;

import com.thoughtworks.gtb.resume.domain.Education;
import com.thoughtworks.gtb.resume.domain.User;
import com.thoughtworks.gtb.resume.exception.EducationException;
import com.thoughtworks.gtb.resume.exception.ErrorMsg;
import com.thoughtworks.gtb.resume.exception.ErrorResult;
import com.thoughtworks.gtb.resume.exception.UserNotExistException;
import com.thoughtworks.gtb.resume.service.EducationService;
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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EducationControllerTest {

    @MockBean
    private EducationService educationService;
    @Autowired
    private TestRestTemplate restTemplate;

    private User firstUser;
    private Education firstEducation;
    private Education secondEducation;

    @BeforeEach
    public void setup() {
        Mockito.reset(educationService);
        firstUser = User.builder()
                .id(1L)
                .name("yurong")
                .age(18)
                .avatar("https://inews.gtimg.com/newsapp_match/0/3581582328/0")
                .description("happy every day!")
                .build();
        firstEducation = Education.builder()
                .id(1)
                .description("hahahahhah, nam, tenetur et ab eius deserunt aspernatur ipsum ducimus quibusdam quis voluptatibus.")
                .year("2005")
                .title("Secondar school sefsdf lizing in artistic")
                .user(firstUser)
                .build();

        secondEducation = Education.builder()
                .id(2)
                .description("hahahahhah, nam, tenetur et ab eius deserunt aspernatur ipsum ducimus quibusdam quis voluptatibus.")
                .year("2006")
                .title("Secondar school sefsdf lizing in artistic")
                .user(firstUser)
                .build();
    }

    @Nested
    class GetEducationsByUserId {

        @Nested
        class WhenUserExist {

            @Test
            public void should_return_educations() throws UserNotExistException {
                List<Education> educations = new ArrayList<>();
                educations.add(firstEducation);
                educations.add(secondEducation);
                when(educationService.getEducations(firstEducation.getUser().getId())).thenReturn(educations);

                ResponseEntity<List> responseEntity = restTemplate.getForEntity("/users/{userId}/educations", List.class, 1L);

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(responseEntity.getBody().size()).isEqualTo(2);
                verify(educationService).getEducations(firstEducation.getUser().getId());
            }

            @Test
            public void should_return_empty_list() throws UserNotExistException {
                List<Education> educations = new ArrayList<>();
                when(educationService.getEducations(firstEducation.getUser().getId())).thenReturn(educations);

                ResponseEntity<List> responseEntity = restTemplate.getForEntity("/users/{userId}/educations", List.class, 1L);

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(responseEntity.getBody().size()).isEqualTo(0);
                verify(educationService).getEducations(firstEducation.getUser().getId());
            }
        }

        @Nested
        class WhenUserNotExists {

            @Test
            public void should_throw_user_not_exist_exception() throws UserNotExistException {
                when(educationService.getEducations(111)).thenThrow(new UserNotExistException());

                ResponseEntity<ErrorResult> responseEntity = restTemplate.getForEntity("/users/{userId}/educations", ErrorResult.class, 111L);
                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getMessage()).isEqualTo("user not exist");
                verify(educationService).getEducations(111L);

            }
        }
    }

    @Nested
    class AddEducations {
        @Nested
        class WhenUserExist {

            @Nested
            class WhenEducationRequestValid {
                @Test
                public void should_add_education_success() throws UserNotExistException, EducationException {
                    ResponseEntity responseEntity = restTemplate.postForEntity("/users/{userId}/educations", firstEducation, null, firstUser.getId());
                    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//                    verify(educationService, times(1)).addEducations(firstUser.getId(), firstEducation);
                }
            }

        }

        @Nested
        class WhenEducationRequestNotValid {

            @Test
            public void should_throw_education_year_not_empty_exception() {
                firstEducation.setYear("");
                ResponseEntity<ErrorResult> responseEntity = restTemplate.postForEntity("/users/{userId}/educations", firstEducation, ErrorResult.class, firstUser.getId());

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getMessage()).isEqualTo(ErrorMsg.EDUCATION_YEAR_NOT_EMPTY);
            }

            @Test
            public void should_throw_education_title_not_empty_exception() {
                firstEducation.setTitle("");
                ResponseEntity<ErrorResult> responseEntity = restTemplate.postForEntity("/users/{userId}/educations", firstEducation, ErrorResult.class, firstUser.getId());

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getMessage()).isEqualTo(ErrorMsg.EDUCATION_TITLE_NOT_EMPTY);
            }

            @Test
            public void should_throw_education_title_not_in_range() {
                firstEducation.setTitle("好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的好的");
                ResponseEntity<ErrorResult> responseEntity = restTemplate.postForEntity("/users/{userId}/educations", firstEducation, ErrorResult.class, firstUser.getId());

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getMessage()).isEqualTo(ErrorMsg.EDUCATION_TITLE_LENGTH_INVALID);
            }

            @Test
            public void should_throw_education_description_not_empty_exception() {
                firstEducation.setDescription("");
                ResponseEntity<ErrorResult> responseEntity = restTemplate.postForEntity("/users/{userId}/educations", firstEducation, ErrorResult.class, firstUser.getId());

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getMessage()).isEqualTo(ErrorMsg.EDUCATION_DESC_NOT_EMPTY);
            }
        }

    }

}
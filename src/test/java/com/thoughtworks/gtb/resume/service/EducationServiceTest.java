package com.thoughtworks.gtb.resume.service;

import com.thoughtworks.gtb.resume.domain.Education;
import com.thoughtworks.gtb.resume.domain.User;
import com.thoughtworks.gtb.resume.exception.EducationException;
import com.thoughtworks.gtb.resume.exception.UserNotExistException;
import com.thoughtworks.gtb.resume.repository.EducationRepository;
import com.thoughtworks.gtb.resume.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EducationServiceTest {

    private EducationService educationService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EducationRepository educationRepository;
    private User user;
    private Education firstEducation;
    private Education secondEducation;
    private Optional<User> optionalUser;


    @BeforeEach
    public void setup() {
        Mockito.reset(userRepository);
        Mockito.reset(educationRepository);
        educationService = new EducationService(userRepository, educationRepository);
        user = User.builder()
                .id(1L)
                .name("yurong")
                .age(18)
                .avatar("https://inews.gtimg.com/newsapp_match/0/3581582328/0")
                .description("happy every day!")
                .build();
        optionalUser = Optional.ofNullable(user);
        firstEducation = Education.builder()
                .id(1)
                .description("hahahahhah, nam, tenetur et ab eius deserunt aspernatur ipsum ducimus quibusdam quis voluptatibus.")
                .year("2005")
                .title("Secondar school sefsdf lizing in artistic")
                .user(user)
                .build();

        secondEducation = Education.builder()
                .id(2)
                .description("hahahahhah, nam, tenetur et ab eius deserunt aspernatur ipsum ducimus quibusdam quis voluptatibus.")
                .year("2006")
                .title("Secondar school sefsdf lizing in artistic")
                .user(user)
                .build();
    }

    @Nested
    class CheckUserExists {
        @Nested
        class WhenUserExists {

            @Test
            public void should_return_user() throws UserNotExistException {
                when(userRepository.findById(user.getId())).thenReturn(optionalUser);
                assertEquals(user, educationService.checkUserExist(user.getId()));

            }
        }

        @Nested
        class WhenUserNotExists {

            @Test
            public void should_throw_user_not_exists_exception() {
                when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
                UserNotExistException exception = assertThrows(UserNotExistException.class, () -> {
                    educationService.checkUserExist(user.getId());
                });

                assertThat(exception.getMessage()).isEqualTo("user not exist");
            }
        }
    }


    @Nested
    class GetEducations {
        @Nested
        class WhenUserNotExists {

            @Test
            public void should_throw_user_not_exists_exception() {
                when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
                UserNotExistException exception = assertThrows(UserNotExistException.class, () -> {
                    educationService.checkUserExist(user.getId());
                });

                assertThat(exception.getMessage()).isEqualTo("user not exist");
            }
        }

        @Nested
        class WhenUserExist {
            @Test
            public void should_return_edcations() throws UserNotExistException {
                List<Education> educationList = new ArrayList<>();
                educationList.add(firstEducation);
                educationList.add(secondEducation);
                Optional<User> optionalUser = Optional.ofNullable(user);

                when(userRepository.findById(user.getId())).thenReturn(optionalUser);
                when(educationRepository.findAllByUserId(user.getId())).thenReturn(educationList);

                List<Education> results = educationService.getEducations(user.getId());
                assertEquals(educationList, results);
            }

            @Test
            public void should_return_empty_list() throws UserNotExistException {
                List<Education> educationList = new ArrayList<>();
                Optional<User> optionalUser = Optional.ofNullable(user);

                when(userRepository.findById(user.getId())).thenReturn(optionalUser);
                when(educationRepository.findAllByUserId(user.getId())).thenReturn(educationList);

                List<Education> results = educationService.getEducations(user.getId());
                assertEquals(0, results.size());
            }
        }
    }

    @Nested
    class AddEducations {

        @Nested
        class WhenEducationExist {
            @Test
            public void should_throw_education_repeat_exception() {
                when(userRepository.findById(user.getId())).thenReturn(optionalUser);
                when(educationRepository.findById(firstEducation.getId())).thenReturn(Optional.ofNullable(firstEducation));

                EducationException exception = assertThrows(EducationException.class, () -> {
                    educationService.addEducations(user.getId(), firstEducation);
                });

                assertThat(exception.getMessage()).isEqualTo("add education repeat");
            }
        }

        @Nested
        class WhenEducationNotExist {
            @Test
            public void should_add_education_success() throws UserNotExistException, EducationException {
                when(userRepository.findById(user.getId())).thenReturn(optionalUser);
                when(educationRepository.findById(firstEducation.getId())).thenReturn(Optional.empty());

                educationService.addEducations(user.getId(), firstEducation);
                verify(educationRepository).save(firstEducation);
            }
        }
    }


}
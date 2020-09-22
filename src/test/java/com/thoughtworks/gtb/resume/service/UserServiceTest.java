package com.thoughtworks.gtb.resume.service;

import com.thoughtworks.gtb.resume.domain.User;
import com.thoughtworks.gtb.resume.exception.UserNotExistException;
import com.thoughtworks.gtb.resume.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    private User user;
    private Optional<User> optionalUser;


    @BeforeEach
    public void setup() {
        Mockito.reset(userRepository);
        userService = new UserService(userRepository);
        user = User.builder()
                .id(1L)
                .name("yurong")
                .age(18)
                .avatar("https://inews.gtimg.com/newsapp_match/0/3581582328/0")
                .description("happy every day!")
                .build();
        optionalUser = Optional.ofNullable(user);
    }

    @Nested
    class GetUser {
        @Nested
        class WhenUserExist {
            @Test
            public void should_return_user() throws UserNotExistException {
                when(userRepository.findById(user.getId())).thenReturn(optionalUser);
                assertEquals(user, userService.getUser(user.getId()));
            }
        }

        @Nested
        class WhenUserNotExist {
            @Test
            public void should_throw_user_not_exists_excepiton() {
                when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
                UserNotExistException exception = assertThrows(UserNotExistException.class, () -> {
                    userService.getUser(user.getId());
                });

                assertThat(exception.getMessage()).isEqualTo("user not exist");
            }
        }
    }

    @Nested
    class AddUser {

        @Test
        public void when_add_user_should_return_user_id() {
            assertEquals(userService.addUser(user), user.getId());
            verify(userRepository).save(user);
        }
    }


}
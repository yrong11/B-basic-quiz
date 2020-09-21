package com.thoughtworks.gtb.resume.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thoughtworks.gtb.resume.exception.ErrorMsg;
import com.thoughtworks.gtb.resume.utils.annotation.ByteLength;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ByteLength(min = 1, max = 128, message = ErrorMsg.USER_NAME_LENGTH_INVALID)
    @NotEmpty(message = ErrorMsg.USER_NAME_NOT_EMPTY)
    private String name;

    @NotNull
    @Min(16)
    private long age;
    @ByteLength(min = 8, max = 512, message = ErrorMsg.USER_AVATAR_LENGTH_INVALID)
    @NotEmpty(message = ErrorMsg.USER_AVATAR_NOT_EMPTY)
    @URL(message = ErrorMsg.USER_AVATAR_FORMAT_INCORRECT)
    private String avatar;

    @ByteLength(max = 1024, message = ErrorMsg.USER_DESC_LENGTH_INVALID)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    private List<Education> educations;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id &&
                name.equals(user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

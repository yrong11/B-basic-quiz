package com.thoughtworks.gtb.resume.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thoughtworks.gtb.resume.exception.ErrorMsg;
import com.thoughtworks.gtb.resume.utils.annotation.ByteLength;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Education implements Comparable<Education>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = ErrorMsg.EDUCATION_YEAR_NOT_EMPTY)
    private String year;
    @NotEmpty(message = ErrorMsg.EDUCATION_TITLE_NOT_EMPTY)
    @ByteLength(min = 1, max = 256, message = ErrorMsg.EDUCATION_TITLE_LENGTH_INVALID)
    private String title;
    @NotEmpty(message = ErrorMsg.EDUCATION_DESC_NOT_EMPTY)
    @ByteLength(min = 1, max = 4096, message = ErrorMsg.EDUCATION_DESC_LENGTH_INVALID)
    private String description;

    @ManyToOne
    @JsonIgnore
    private User user;

    @Override
    public int compareTo(Education o) {
        return this.year.compareTo(o.year);
    }
}

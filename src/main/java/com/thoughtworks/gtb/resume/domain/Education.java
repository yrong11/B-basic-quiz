package com.thoughtworks.gtb.resume.domain;

import com.thoughtworks.gtb.resume.exception.ErrorMsg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Education {
    @NotEmpty(message = ErrorMsg.EDUCATION_YEAR_NOT_EMPTY)
    private int year;
    @NotEmpty(message = ErrorMsg.EDUCATION_TITLE_NOT_EMPTY)
    private String title;
    @NotEmpty(message = ErrorMsg.EDUCATION_DESC_NOT_EMPTY)
    private String description;
}

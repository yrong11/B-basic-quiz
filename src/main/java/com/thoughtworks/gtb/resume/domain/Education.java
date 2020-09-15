package com.thoughtworks.gtb.resume.domain;

import com.thoughtworks.gtb.resume.exception.ErrorMsg;
import com.thoughtworks.gtb.resume.utils.annotation.ByteLength;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Education {
    @NotEmpty(message = ErrorMsg.EDUCATION_YEAR_NOT_EMPTY)
    private String year;
    @NotEmpty(message = ErrorMsg.EDUCATION_TITLE_NOT_EMPTY)
    @ByteLength(min = 1, max = 256, message = ErrorMsg.EDUCATION_TITLE_LENGTH_INVALID)
    private String title;
    @NotEmpty(message = ErrorMsg.EDUCATION_DESC_NOT_EMPTY)
    @ByteLength(min = 1, max = 4096, message = ErrorMsg.EDUCATION_DESC_LENGTH_INVALID)
    private String description;
    private long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Education)) return false;
        Education education = (Education) o;
        return userId == education.userId &&
                year.equals(education.year) &&
                title.equals(education.title) &&
                description.equals(education.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, title, description, userId);
    }
}

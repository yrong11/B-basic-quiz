package com.thoughtworks.gtb.resume.utils.annotation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ByteLengthValidHandler implements ConstraintValidator<ByteLength,String> {
    private int minByteLength;
    private int maxByteLength;
    @Override
    public void initialize(ByteLength constraintAnnotation) {
        minByteLength = constraintAnnotation.min();
        maxByteLength = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value))
            return true;
        return isValidByteLength(value);

    }

    private boolean isValidByteLength(String value) {
        if (value.getBytes().length > maxByteLength || value.getBytes().length < minByteLength)
            return false;
        return true;
    }
}

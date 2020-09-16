package com.thoughtworks.gtb.resume.utils.annotation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

// GTB: + 自定义了 ByteLengthValidHandler 支持非英文的长度判断，想的全面。虽然题目本身并不要求这么严谨，下次我改进一下题目描述。
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
        // GTB: - IDEA 都提醒了，可以直接 return value.getBytes().length > maxByteLength || value.getBytes().length < minByteLength；
        if (value.getBytes().length > maxByteLength || value.getBytes().length < minByteLength)
            return false;
        return true;
    }
}

package com.keiko.securityapp.validation;

import com.keiko.securityapp.dto.model.user.UserDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public boolean isValid (Object o, ConstraintValidatorContext constraintValidatorContext) {
        final UserDto user = (UserDto) o;
        return user.getPassword ().equals (user.getPasswordConfirm ());
    }
}

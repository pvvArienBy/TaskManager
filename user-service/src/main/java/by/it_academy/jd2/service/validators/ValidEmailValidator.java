package by.it_academy.jd2.service.validators;

import by.it_academy.jd2.annotation.ValidEmail;
import by.it_academy.jd2.service.api.IUserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class ValidEmailValidator implements ConstraintValidator<ValidEmail, String> {
    private final IUserService userService;

    public ValidEmailValidator(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(ValidEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String mail, ConstraintValidatorContext context) {
        return !this.userService.existsByMail(mail);
    }
}

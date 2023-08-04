package by.it_academy.jd2.service.validators;

import by.it_academy.jd2.annotation.ValidEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EnumValidator implements ConstraintValidator<ValidEnum, String> {
    private Set<String> allowedValues;

    public EnumValidator(Set<String> allowedValues) {
        this.allowedValues = allowedValues;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void initialize(ValidEnum targetEnum) {
        Class<? extends Enum> enumSelected = targetEnum.targetClassType();
        this.allowedValues = (Set<String>) EnumSet.allOf(enumSelected).stream().map(e -> ((Enum<? extends Enum<?>>) e).name())
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || allowedValues.contains(value);
    }
}
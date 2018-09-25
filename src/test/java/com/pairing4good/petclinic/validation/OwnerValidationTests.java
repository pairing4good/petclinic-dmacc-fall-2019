package com.pairing4good.petclinic.validation;

import com.pairing4good.petclinic.owner.Owner;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class OwnerValidationTests {

    private LocalValidatorFactoryBean validator;

    @Before
    public void setUp() {
        validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
        LocaleContextHolder.setLocale(Locale.ENGLISH);
    }

    @Test
    public void shouldBeValid() {
        Owner owner = createValidOwner();

        Set<ConstraintViolation<Owner>> constraintViolations = validator.validate(owner);

        assertThat(constraintViolations.size()).isEqualTo(0);
    }

    @Test
    public void shouldReturnErrorWhenFirstNameIsBlank() {
        Owner owner = createValidOwner();
        owner.setFirstName("");

        Set<ConstraintViolation<Owner>> constraintViolations = validator.validate(owner);

        assertConstraintValidation(constraintViolations, "firstName", "must not be empty");
    }

    @Test
    public void shouldReturnErrorWhenFirstNameIsNull() {
        Owner owner = createValidOwner();
        owner.setFirstName(null);

        Set<ConstraintViolation<Owner>> constraintViolations = validator.validate(owner);

        assertConstraintValidation(constraintViolations, "firstName", "must not be empty");
    }

    @Test
    public void shouldReturnErrorWhenLastNameIsBlank() {
        Owner owner = createValidOwner();
        owner.setLastName("");

        Set<ConstraintViolation<Owner>> constraintViolations = validator.validate(owner);

        assertConstraintValidation(constraintViolations, "lastName", "must not be empty");
    }

    @Test
    public void shouldReturnErrorWhenLastNameIsNull() {
        Owner owner = createValidOwner();
        owner.setLastName(null);

        Set<ConstraintViolation<Owner>> constraintViolations = validator.validate(owner);

        assertConstraintValidation(constraintViolations, "lastName", "must not be empty");
    }

    @Test
    public void shouldReturnErrorWhenAddressIsBlank() {
        Owner owner = createValidOwner();
        owner.setAddress("");

        Set<ConstraintViolation<Owner>> constraintViolations = validator.validate(owner);

        assertConstraintValidation(constraintViolations, "address", "must not be empty");
    }

    @Test
    public void shouldReturnErrorWhenAddressIsNull() {
        Owner owner = createValidOwner();
        owner.setAddress(null);

        Set<ConstraintViolation<Owner>> constraintViolations = validator.validate(owner);

        assertConstraintValidation(constraintViolations, "address", "must not be empty");
    }

    @Test
    public void shouldReturnErrorWhenCityIsBlank() {
        Owner owner = createValidOwner();
        owner.setCity("");

        Set<ConstraintViolation<Owner>> constraintViolations = validator.validate(owner);

        assertConstraintValidation(constraintViolations, "city", "must not be empty");
    }

    @Test
    public void shouldReturnErrorWhenCityIsNull() {
        Owner owner = createValidOwner();
        owner.setCity(null);

        Set<ConstraintViolation<Owner>> constraintViolations = validator.validate(owner);

        assertConstraintValidation(constraintViolations, "city", "must not be empty");
    }

    @Test
    public void shouldReturnErrorWhenTelephoneIsBlank() {
        Owner owner = createValidOwner();
        owner.setTelephone("");

        Set<ConstraintViolation<Owner>> constraintViolations = validator.validate(owner);

        assertThat(constraintViolations.size()).isEqualTo(2);

        List<ConstraintViolation<Owner>> violations = new ArrayList<>();

        for (ConstraintViolation<Owner> violation : constraintViolations) {
            violations.add(violation);
        }

        Collections.sort(violations, Comparator.comparing(ConstraintViolation::getMessage));

        ConstraintViolation<Owner> firstViolation = violations.get(0);
        assertThat(firstViolation.getPropertyPath().toString()).isEqualTo("telephone");
        assertThat(firstViolation.getMessage()).isEqualTo("must not be empty");

        ConstraintViolation<Owner> secondViolation = violations.get(1);
        assertThat(secondViolation.getPropertyPath().toString()).isEqualTo("telephone");
        assertThat(secondViolation.getMessage()).isEqualTo("numeric value out of bounds (<10 digits>.<0 digits> expected)");
    }

    @Test
    public void shouldReturnErrorWhenTelephoneIsNull() {
        Owner owner = createValidOwner();
        owner.setTelephone(null);

        Set<ConstraintViolation<Owner>> constraintViolations = validator.validate(owner);

        assertConstraintValidation(constraintViolations, "telephone", "must not be empty");
    }

    @Test
    public void shouldReturnErrorWhenTelephoneIsAlphaCharacters() {
        Owner owner = createValidOwner();
        owner.setTelephone("ABC");

        Set<ConstraintViolation<Owner>> constraintViolations = validator.validate(owner);

        assertConstraintValidation(constraintViolations, "telephone", "numeric value out of bounds (<10 digits>.<0 digits> expected)");
    }

    @Test
    public void shouldReturnErrorWhenTelephoneIsNumberIsTooLong() {
        Owner owner = createValidOwner();
        owner.setTelephone("12345678901");

        Set<ConstraintViolation<Owner>> constraintViolations = validator.validate(owner);

        assertConstraintValidation(constraintViolations, "telephone", "numeric value out of bounds (<10 digits>.<0 digits> expected)");
    }

    private Owner createValidOwner() {
        Owner owner = new Owner();
        owner.setFirstName("testFirstName");
        owner.setLastName("testLastName");
        owner.setAddress("test address");
        owner.setCity("testCity");
        owner.setTelephone("1234567890");
        return owner;
    }

    private void assertConstraintValidation(Set<ConstraintViolation<Owner>> constraintViolations, String propertyPath,
                                            String message) {
        assertThat(constraintViolations.size()).isEqualTo(1);
        ConstraintViolation<Owner> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo(propertyPath);
        assertThat(violation.getMessage()).isEqualTo(message);
    }
}

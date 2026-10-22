package com.ratingstaff.asn2.models;

import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ratingstaff.asn2.models.StaffRating.RoleType;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;

public class StaffValidationTest {
    private static ValidatorFactory factory;

    private static Validator validator;

    @BeforeAll 
    static void setup() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test 
    void validRatingHasNoViolations(){

        StaffRating ratings = new StaffRating("Jorge", "jorge@gmail.com", RoleType.TA, 2, 2, 2, null);

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(ratings);

        Assertions.assertTrue(violations.isEmpty());
    }

    @Test 
    void invalidRatingHasViolations(){

        StaffRating ratings = new StaffRating("", "jorge.com", null, 2, 2, 15, null);

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(ratings);

        Assertions.assertFalse(violations.isEmpty());
    }

}

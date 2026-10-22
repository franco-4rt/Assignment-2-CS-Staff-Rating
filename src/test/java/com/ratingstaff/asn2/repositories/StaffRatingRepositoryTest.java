package com.ratingstaff.asn2.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import com.ratingstaff.asn2.models.StaffRating;
import com.ratingstaff.asn2.models.StaffRating.RoleType;

@DataJpaTest @AutoConfigureTestDatabase (replace = AutoConfigureTestDatabase.Replace.NONE)
public class StaffRatingRepositoryTest {

    @Autowired 
    StaffRatingRepository data;

    StaffRating rating = new StaffRating("jorge", "jorge@gmail.com", RoleType.TA, 3, 3, 3, "good fellow");
    StaffRating saved;

    @BeforeEach 
    void setup(){
        StaffRating rating = new StaffRating("jorge", "jorge@gmail.com", RoleType.STAFF, 2, 2, 2, null);
        saved = data.save(rating);
    }

    @Test 
    void saveAssignsId() throws Exception {
        Assertions.assertNotNull(saved.getId());
    }
    
    @Test 
    void canfindByID() throws Exception {
        Optional<StaffRating> found = data.findById(saved.getId());
        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals("jorge", found.get().getName());
    }

    @Test 
    void canfindAll() throws Exception {
        long current = data.count();
        StaffRating d1 = new StaffRating("jhon", "john@gmail.com", RoleType.TA, 2, 2, 2, null);
        StaffRating d2 = new StaffRating("john2", "john2@gmail.com", RoleType.TA, 2, 2, 2, null);

        data.save(d1); data.save(d2);

        Assertions.assertEquals(current + 2, data.count());
    }

    @Test 
    void canUpdate() throws Exception {
        long current = data.count();
        StaffRating d1 = new StaffRating("jhon", "john@gmail.com", RoleType.TA, 2, 2, 2, null);
        StaffRating saved = data.save(d1);
        saved.setName("Juan");
        data.save(saved);
        Assertions.assertEquals(data.findById(saved.getId()).get().getName(), "Juan");
        Assertions.assertEquals(current + 1, data.count());
    }

    @Test
    void canDelete() throws Exception {
        data.deleteById(saved.getId());
        Optional<StaffRating> found = data.findById(saved.getId());
        Assertions.assertTrue(found.isEmpty());
    }
}

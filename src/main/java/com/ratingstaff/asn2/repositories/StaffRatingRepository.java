package com.ratingstaff.asn2.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.ratingstaff.asn2.models.StaffRating;

@Repository 
public class StaffRatingRepository {
    private List<StaffRating> data = new ArrayList<>();

    public StaffRatingRepository() {
        this.data.add(new StaffRating("Juan", "Juan@gmail.com", StaffRating.RoleType.TA, 3, 10, 10, "Fun person"));
        this.data.add(new StaffRating("Jorge", "Jorge@gmail.com", StaffRating.RoleType.STAFF, 2, 2, 2, "Good person"));
    }

    public List<StaffRating> findAll(){
        return data;
    }

    public Optional<StaffRating> findById(int id){
        for (StaffRating rating : data){
            if( rating.getId() == id ){return Optional.of(rating);}
        }
        return Optional.empty();
    }
}

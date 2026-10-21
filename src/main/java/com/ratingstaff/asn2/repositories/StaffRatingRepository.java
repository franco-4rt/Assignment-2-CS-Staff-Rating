package com.ratingstaff.asn2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ratingstaff.asn2.models.StaffRating;

public interface StaffRatingRepository extends JpaRepository<StaffRating, Long>{
}

package com.ratingstaff.asn2.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ratingstaff.asn2.models.StaffRating;





@Controller 
public class StaffRatingController{
    @GetMapping("index/view")
    public String getallstaffRatings(Model model) {
        List<StaffRating> ratings = new ArrayList<>();
        ratings.add(new StaffRating("Juan", "juan@gmail.com", StaffRating.RoleType.TA, 10,10,7, "good luck"));
        model.addAttribute("rt", ratings);
        return "index";
    }
}

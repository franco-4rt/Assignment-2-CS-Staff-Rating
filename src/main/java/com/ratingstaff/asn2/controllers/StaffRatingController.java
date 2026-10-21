package com.ratingstaff.asn2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ratingstaff.asn2.repositories.StaffRatingRepository;






@Controller 
public class StaffRatingController{
    private final StaffRatingRepository data;

    @GetMapping("index/view")
    public String getallstaffRatings(Model model) {
        
        model.addAttribute("rt", this.data.findAll());
        return "index";
    }

    public StaffRatingController(StaffRatingRepository data) {
        this.data = data;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/index/view";
    }
    
    
}

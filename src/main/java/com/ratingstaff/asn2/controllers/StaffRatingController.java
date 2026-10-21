package com.ratingstaff.asn2.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import com.ratingstaff.asn2.models.StaffRating;
import com.ratingstaff.asn2.repositories.StaffRatingRepository;

import jakarta.validation.Valid;












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
    
    @GetMapping("/ratings/{id}")
    public String showDetails(Model model, @PathVariable Long id) {
        
        model.addAttribute("rating", this.data.findById(id)
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rating not found")));
        return "ratings/details";
    }

    @GetMapping("/ratings/new")
    public String createRating(Model model) {
        model.addAttribute("rating", new StaffRating());
        return "ratings/create";
    }
    
    @PostMapping("/ratings")
    public String postRating(@Valid @ModelAttribute("rating") StaffRating rating, BindingResult result) {
        if(result.hasErrors()){return "ratings/create";}
        this.data.save(rating);
        return "redirect:/index/view";
    }
    
    @PostMapping("/ratings/{id}/delete")
    public String deleteRating(@PathVariable Long id) {
        this.data.deleteById(id);
        return "redirect:/index/view";
    }
    
    @GetMapping("/ratings/{id}/edit")
    public String editRating(@PathVariable Long id, Model model) {
        model.addAttribute("rating", this.data.findById(id)
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rating not found")));

        return "ratings/edit";
    }
    
    @PostMapping("/ratings/{id}/edit")
    public String saveRatingEdit(@Valid @ModelAttribute("rating") StaffRating rating, BindingResult result, @PathVariable Long id) {
        rating.setId(id);
        if(result.hasErrors()){return"ratings/edit";}
        this.data.save(rating);
        return "redirect:/ratings/" + id;
    }
    
}

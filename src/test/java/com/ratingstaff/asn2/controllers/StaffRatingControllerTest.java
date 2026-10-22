package com.ratingstaff.asn2.controllers;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ratingstaff.asn2.models.StaffRating;
import com.ratingstaff.asn2.repositories.StaffRatingRepository;

@WebMvcTest(StaffRatingController.class)
public class StaffRatingControllerTest {

    @MockitoBean
    private StaffRatingRepository data;

    @Autowired
    private MockMvc mockMvC;


    private StaffRating sampleRating() {
        StaffRating rating = new StaffRating("Juan", "juan@gmail.com", StaffRating.RoleType.TA, 8, 9, 7, "Good TA");
        rating.setId(1L);
        return rating;
    }

    @Test
    void testHome() throws Exception {
        mockMvC.perform(MockMvcRequestBuilders.get("/"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/index/view"));
    }

    @Test
    void testGetallstaffRatings() throws Exception {
        Mockito.when(data.findAll()).thenReturn(List.of(sampleRating()));

        mockMvC.perform(MockMvcRequestBuilders.get("/index/view"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("index"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("rt"));
    }

    @Test
    void testShowDetails() throws Exception {
        Mockito.when(data.findById(1L)).thenReturn(Optional.of(sampleRating()));

        mockMvC.perform(MockMvcRequestBuilders.get("/ratings/1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("ratings/details"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("rating"));
    }

    @Test
    void testShowDetailsNotFound() throws Exception {
        Mockito.when(data.findById(999L)).thenReturn(Optional.empty());

        mockMvC.perform(MockMvcRequestBuilders.get("/ratings/999"))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testCreateRating() throws Exception {
        mockMvC.perform(MockMvcRequestBuilders.get("/ratings/new"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("ratings/create"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("rating"));
    }

    @Test
    void testPostRating() throws Exception {
        mockMvC.perform(MockMvcRequestBuilders.post("/ratings")
                .param("name", "Juan")
                .param("email", "juan@gmail.com")
                .param("roleType", "TA")
                .param("clarity", "8")
                .param("niceness", "9")
                .param("knowledgeable", "7")
                .param("comment", "Good TA"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/index/view"));

        Mockito.verify(data).save(ArgumentMatchers.any(StaffRating.class));
    }

    @Test
    void testPostRatingInvalid() throws Exception {
        mockMvC.perform(MockMvcRequestBuilders.post("/ratings")
                .param("name", "")
                .param("email", "juan@gmail.com")
                .param("roleType", "TA")
                .param("clarity", "8")
                .param("niceness", "9")
                .param("knowledgeable", "7")
                .param("comment", "Good TA"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("ratings/create"))
            .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("rating", "name"));

        Mockito.verify(data, Mockito.never()).save(ArgumentMatchers.any());
    }

    @Test
    void testEditRating() throws Exception {
        Mockito.when(data.findById(1L)).thenReturn(Optional.of(sampleRating()));

        mockMvC.perform(MockMvcRequestBuilders.get("/ratings/1/edit"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("ratings/edit"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("rating"));
    }

    @Test
    void testSaveRatingEdit() throws Exception {
        mockMvC.perform(MockMvcRequestBuilders.post("/ratings/1/edit")
                .param("name", "Juan Updated")
                .param("email", "juan@gmail.com")
                .param("roleType", "PROF")
                .param("clarity", "10")
                .param("niceness", "10")
                .param("knowledgeable", "10")
                .param("comment", "Even better"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/ratings/1"));

        Mockito.verify(data).save(ArgumentMatchers.any(StaffRating.class));
    }

    @Test
    void testSaveRatingEditInvalid() throws Exception {
        mockMvC.perform(MockMvcRequestBuilders.post("/ratings/1/edit")
                .param("name", "")
                .param("email", "juan@gmail.com")
                .param("roleType", "PROF")
                .param("clarity", "10")
                .param("niceness", "10")
                .param("knowledgeable", "10")
                .param("comment", "Even better"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("ratings/edit"))
            .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("rating", "name"));

        Mockito.verify(data, Mockito.never()).save(ArgumentMatchers.any());
    }

    @Test
    void testDeleteRating() throws Exception {
        mockMvC.perform(MockMvcRequestBuilders.post("/ratings/1/delete"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/index/view"));

        Mockito.verify(data).deleteById(1L);
    }
}
package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {
    
    private final RatingRepository ratingRepository;
    
    @Autowired
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }
    
    /**
     * Find all Rating entries
     * @return List of all Rating entries
     */
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }
    
    /**
     * Find a Rating entry by its ID
     * @param id The ID of the Rating entry
     * @return Optional containing the Rating entry if found
     */
    public Optional<Rating> findById(Integer id) {
        return ratingRepository.findById(id);
    }
    
    /**
     * Save a new Rating entry
     * @param rating The Rating entry to save
     * @return The saved Rating entry
     */
    public Rating save(Rating rating) {
        return ratingRepository.save(rating);
    }
    
    /**
     * Update an existing Rating entry
     * @param id The ID of the Rating entry to update
     * @param rating The updated Rating entry
     * @return The updated Rating entry
     */
    public Rating update(Integer id, Rating rating) {
        rating.setId(id);
        return ratingRepository.save(rating);
    }
    
    /**
     * Delete a Rating entry by its ID
     * @param id The ID of the Rating entry to delete
     */
    public void deleteById(Integer id) {
        ratingRepository.deleteById(id);
    }
}
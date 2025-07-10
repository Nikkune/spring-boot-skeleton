package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingService ratingService;

    private Rating rating;

    @BeforeEach
    public void setUp() {
        rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("Moody Test");
        rating.setSandPRating("S&P Test");
        rating.setFitchRating("Fitch Test");
        rating.setOrderNumber(10);
    }

    @Test
    public void testFindAll() {
        // Given
        when(ratingRepository.findAll()).thenReturn(Arrays.asList(rating));

        // When
        List<Rating> result = ratingService.findAll();

        // Then
        assertEquals(1, result.size());
        assertEquals(rating.getMoodysRating(), result.get(0).getMoodysRating());
        verify(ratingRepository).findAll();
    }

    @Test
    public void testFindById() {
        // Given
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

        // When
        Optional<Rating> result = ratingService.findById(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals(rating.getMoodysRating(), result.get().getMoodysRating());
        verify(ratingRepository).findById(1);
    }

    @Test
    public void testFindById_NotFound() {
        // Given
        when(ratingRepository.findById(999)).thenReturn(Optional.empty());

        // When
        Optional<Rating> result = ratingService.findById(999);

        // Then
        assertFalse(result.isPresent());
        verify(ratingRepository).findById(999);
    }

    @Test
    public void testSave() {
        // Given
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        // When
        Rating result = ratingService.save(rating);

        // Then
        assertEquals(rating.getMoodysRating(), result.getMoodysRating());
        verify(ratingRepository).save(rating);
    }

    @Test
    public void testUpdate() {
        // Given
        Rating updatedRating = new Rating();
        updatedRating.setMoodysRating("Updated Moody");
        updatedRating.setSandPRating("Updated S&P");
        updatedRating.setFitchRating("Updated Fitch");
        updatedRating.setOrderNumber(20);

        when(ratingRepository.save(any(Rating.class))).thenReturn(updatedRating);

        // When
        Rating result = ratingService.update(1, updatedRating);

        // Then
        assertEquals(1, result.getId());
        assertEquals(updatedRating.getMoodysRating(), result.getMoodysRating());
        verify(ratingRepository).save(updatedRating);
    }

    @Test
    public void testDeleteById() {
        // When
        ratingService.deleteById(1);

        // Then
        verify(ratingRepository).deleteById(1);
    }
}
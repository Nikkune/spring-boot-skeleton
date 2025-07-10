package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
public class CurvePointServiceTest {

    @Mock
    private CurvePointRepository curvePointRepository;

    @InjectMocks
    private CurvePointService curvePointService;

    private CurvePoint curvePoint;

    @BeforeEach
    public void setUp() {
        curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(10);
        curvePoint.setTerm(1.0);
        curvePoint.setValue(10.0);
    }

    @Test
    public void testFindAll() {
        // Given
        when(curvePointRepository.findAll()).thenReturn(Arrays.asList(curvePoint));

        // When
        List<CurvePoint> result = curvePointService.findAll();

        // Then
        assertEquals(1, result.size());
        assertEquals(curvePoint.getCurveId(), result.get(0).getCurveId());
        verify(curvePointRepository).findAll();
    }

    @Test
    public void testFindById() {
        // Given
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

        // When
        Optional<CurvePoint> result = curvePointService.findById(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals(curvePoint.getCurveId(), result.get().getCurveId());
        verify(curvePointRepository).findById(1);
    }

    @Test
    public void testFindById_NotFound() {
        // Given
        when(curvePointRepository.findById(999)).thenReturn(Optional.empty());

        // When
        Optional<CurvePoint> result = curvePointService.findById(999);

        // Then
        assertFalse(result.isPresent());
        verify(curvePointRepository).findById(999);
    }

    @Test
    public void testSave() {
        // Given
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

        // When
        CurvePoint result = curvePointService.save(curvePoint);

        // Then
        assertEquals(curvePoint.getCurveId(), result.getCurveId());
        verify(curvePointRepository).save(curvePoint);
    }

    @Test
    public void testUpdate() {
        // Given
        CurvePoint updatedCurvePoint = new CurvePoint();
        updatedCurvePoint.setCurveId(20);
        updatedCurvePoint.setTerm(2.0);
        updatedCurvePoint.setValue(20.0);

        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(updatedCurvePoint);

        // When
        CurvePoint result = curvePointService.update(1, updatedCurvePoint);

        // Then
        assertEquals(1, result.getId());
        assertEquals(updatedCurvePoint.getCurveId(), result.getCurveId());
        verify(curvePointRepository).save(updatedCurvePoint);
    }

    @Test
    public void testDeleteById() {
        // When
        curvePointService.deleteById(1);

        // Then
        verify(curvePointRepository).deleteById(1);
    }
}
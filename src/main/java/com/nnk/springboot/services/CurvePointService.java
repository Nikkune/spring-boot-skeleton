package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurvePointService {
    
    private final CurvePointRepository curvePointRepository;
    
    @Autowired
    public CurvePointService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }
    
    /**
     * Find all CurvePoint entries
     * @return List of all CurvePoint entries
     */
    public List<CurvePoint> findAll() {
        return curvePointRepository.findAll();
    }
    
    /**
     * Find a CurvePoint entry by its ID
     * @param id The ID of the CurvePoint entry
     * @return Optional containing the CurvePoint entry if found
     */
    public Optional<CurvePoint> findById(Integer id) {
        return curvePointRepository.findById(id);
    }
    
    /**
     * Save a new CurvePoint entry
     * @param curvePoint The CurvePoint entry to save
     * @return The saved CurvePoint entry
     */
    public CurvePoint save(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }
    
    /**
     * Update an existing CurvePoint entry
     * @param id The ID of the CurvePoint entry to update
     * @param curvePoint The updated CurvePoint entry
     * @return The updated CurvePoint entry
     */
    public CurvePoint update(Integer id, CurvePoint curvePoint) {
        curvePoint.setId(id);
        return curvePointRepository.save(curvePoint);
    }
    
    /**
     * Delete a CurvePoint entry by its ID
     * @param id The ID of the CurvePoint entry to delete
     */
    public void deleteById(Integer id) {
        curvePointRepository.deleteById(id);
    }
}
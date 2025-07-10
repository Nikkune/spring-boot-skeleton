package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameService {
    
    private final RuleNameRepository ruleNameRepository;
    
    @Autowired
    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }
    
    /**
     * Find all RuleName entries
     * @return List of all RuleName entries
     */
    public List<RuleName> findAll() {
        return ruleNameRepository.findAll();
    }
    
    /**
     * Find a RuleName entry by its ID
     * @param id The ID of the RuleName entry
     * @return Optional containing the RuleName entry if found
     */
    public Optional<RuleName> findById(Integer id) {
        return ruleNameRepository.findById(id);
    }
    
    /**
     * Save a new RuleName entry
     * @param ruleName The RuleName entry to save
     * @return The saved RuleName entry
     */
    public RuleName save(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }
    
    /**
     * Update an existing RuleName entry
     * @param id The ID of the RuleName entry to update
     * @param ruleName The updated RuleName entry
     * @return The updated RuleName entry
     */
    public RuleName update(Integer id, RuleName ruleName) {
        ruleName.setId(id);
        return ruleNameRepository.save(ruleName);
    }
    
    /**
     * Delete a RuleName entry by its ID
     * @param id The ID of the RuleName entry to delete
     */
    public void deleteById(Integer id) {
        ruleNameRepository.deleteById(id);
    }
}
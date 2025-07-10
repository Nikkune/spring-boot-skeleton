package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListService {
    
    private final BidListRepository bidListRepository;
    
    @Autowired
    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }
    
    /**
     * Find all BidList entries
     * @return List of all BidList entries
     */
    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }
    
    /**
     * Find a BidList entry by its ID
     * @param id The ID of the BidList entry
     * @return Optional containing the BidList entry if found
     */
    public Optional<BidList> findById(Integer id) {
        return bidListRepository.findById(id);
    }
    
    /**
     * Save a new BidList entry
     * @param bidList The BidList entry to save
     * @return The saved BidList entry
     */
    public BidList save(BidList bidList) {
        return bidListRepository.save(bidList);
    }
    
    /**
     * Update an existing BidList entry
     * @param id The ID of the BidList entry to update
     * @param bidList The updated BidList entry
     * @return The updated BidList entry
     */
    public BidList update(Integer id, BidList bidList) {
        bidList.setBidListId(id);
        return bidListRepository.save(bidList);
    }
    
    /**
     * Delete a BidList entry by its ID
     * @param id The ID of the BidList entry to delete
     */
    public void deleteById(Integer id) {
        bidListRepository.deleteById(id);
    }
}
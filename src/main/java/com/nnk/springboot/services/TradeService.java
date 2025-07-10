package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeService {
    
    private final TradeRepository tradeRepository;
    
    @Autowired
    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }
    
    /**
     * Find all Trade entries
     * @return List of all Trade entries
     */
    public List<Trade> findAll() {
        return tradeRepository.findAll();
    }
    
    /**
     * Find a Trade entry by its ID
     * @param id The ID of the Trade entry
     * @return Optional containing the Trade entry if found
     */
    public Optional<Trade> findById(Integer id) {
        return tradeRepository.findById(id);
    }
    
    /**
     * Save a new Trade entry
     * @param trade The Trade entry to save
     * @return The saved Trade entry
     */
    public Trade save(Trade trade) {
        return tradeRepository.save(trade);
    }
    
    /**
     * Update an existing Trade entry
     * @param id The ID of the Trade entry to update
     * @param trade The updated Trade entry
     * @return The updated Trade entry
     */
    public Trade update(Integer id, Trade trade) {
        trade.setTradeId(id);
        return tradeRepository.save(trade);
    }
    
    /**
     * Delete a Trade entry by its ID
     * @param id The ID of the Trade entry to delete
     */
    public void deleteById(Integer id) {
        tradeRepository.deleteById(id);
    }
}
package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeService tradeService;

    private Trade trade;

    @BeforeEach
    public void setUp() {
        trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Account Test");
        trade.setType("Type Test");
        trade.setBuyQuantity(10.0);
        trade.setSellQuantity(5.0);
        trade.setBuyPrice(100.0);
        trade.setSellPrice(95.0);
        trade.setTradeDate(Timestamp.valueOf(LocalDateTime.now()));
        trade.setSecurity("Security Test");
        trade.setStatus("Status Test");
        trade.setTrader("Trader Test");
    }

    @Test
    public void testFindAll() {
        // Given
        when(tradeRepository.findAll()).thenReturn(Arrays.asList(trade));

        // When
        List<Trade> result = tradeService.findAll();

        // Then
        assertEquals(1, result.size());
        assertEquals(trade.getAccount(), result.get(0).getAccount());
        verify(tradeRepository).findAll();
    }

    @Test
    public void testFindById() {
        // Given
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        // When
        Optional<Trade> result = tradeService.findById(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals(trade.getAccount(), result.get().getAccount());
        verify(tradeRepository).findById(1);
    }

    @Test
    public void testFindById_NotFound() {
        // Given
        when(tradeRepository.findById(999)).thenReturn(Optional.empty());

        // When
        Optional<Trade> result = tradeService.findById(999);

        // Then
        assertFalse(result.isPresent());
        verify(tradeRepository).findById(999);
    }

    @Test
    public void testSave() {
        // Given
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        // When
        Trade result = tradeService.save(trade);

        // Then
        assertEquals(trade.getAccount(), result.getAccount());
        verify(tradeRepository).save(trade);
    }

    @Test
    public void testUpdate() {
        // Given
        Trade updatedTrade = new Trade();
        updatedTrade.setAccount("Updated Account");
        updatedTrade.setType("Updated Type");
        updatedTrade.setBuyQuantity(20.0);
        updatedTrade.setSellQuantity(15.0);
        updatedTrade.setBuyPrice(200.0);
        updatedTrade.setSellPrice(190.0);
        updatedTrade.setSecurity("Updated Security");
        updatedTrade.setStatus("Updated Status");
        updatedTrade.setTrader("Updated Trader");

        when(tradeRepository.save(any(Trade.class))).thenReturn(updatedTrade);

        // When
        Trade result = tradeService.update(1, updatedTrade);

        // Then
        assertEquals(1, result.getTradeId());
        assertEquals(updatedTrade.getAccount(), result.getAccount());
        verify(tradeRepository).save(updatedTrade);
    }

    @Test
    public void testDeleteById() {
        // When
        tradeService.deleteById(1);

        // Then
        verify(tradeRepository).deleteById(1);
    }
}
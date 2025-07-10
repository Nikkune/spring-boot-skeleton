package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
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
public class BidListServiceTest {

    @Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidListService bidListService;

    private BidList bidList;

    @BeforeEach
    public void setUp() {
        bidList = new BidList();
        bidList.setBidListId(1);
        bidList.setAccount("Account Test");
        bidList.setType("Type Test");
        bidList.setBidQuantity(10.0);
    }

    @Test
    public void testFindAll() {
        // Given
        when(bidListRepository.findAll()).thenReturn(Arrays.asList(bidList));

        // When
        List<BidList> result = bidListService.findAll();

        // Then
        assertEquals(1, result.size());
        assertEquals(bidList.getAccount(), result.get(0).getAccount());
        verify(bidListRepository).findAll();
    }

    @Test
    public void testFindById() {
        // Given
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));

        // When
        Optional<BidList> result = bidListService.findById(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals(bidList.getAccount(), result.get().getAccount());
        verify(bidListRepository).findById(1);
    }

    @Test
    public void testFindById_NotFound() {
        // Given
        when(bidListRepository.findById(999)).thenReturn(Optional.empty());

        // When
        Optional<BidList> result = bidListService.findById(999);

        // Then
        assertFalse(result.isPresent());
        verify(bidListRepository).findById(999);
    }

    @Test
    public void testSave() {
        // Given
        when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);

        // When
        BidList result = bidListService.save(bidList);

        // Then
        assertEquals(bidList.getAccount(), result.getAccount());
        verify(bidListRepository).save(bidList);
    }

    @Test
    public void testUpdate() {
        // Given
        BidList updatedBidList = new BidList();
        updatedBidList.setAccount("Updated Account");
        updatedBidList.setType("Updated Type");
        updatedBidList.setBidQuantity(20.0);

        when(bidListRepository.save(any(BidList.class))).thenReturn(updatedBidList);

        // When
        BidList result = bidListService.update(1, updatedBidList);

        // Then
        assertEquals(1, result.getBidListId());
        assertEquals(updatedBidList.getAccount(), result.getAccount());
        verify(bidListRepository).save(updatedBidList);
    }

    @Test
    public void testDeleteById() {
        // When
        bidListService.deleteById(1);

        // Then
        verify(bidListRepository).deleteById(1);
    }
}
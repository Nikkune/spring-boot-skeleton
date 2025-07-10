package com.nnk.springboot.integration;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("prod") // Use application-prod.properties
public class BidListIntegrationTest {

    @Autowired
    private BidListRepository bidListRepository;

    private BidList testBidList;

    @BeforeEach
    public void setUp() {
        // Create a test BidList entity
        testBidList = new BidList();
        testBidList.setAccount("Integration Test Account");
        testBidList.setType("Integration Test Type");
        testBidList.setBidQuantity(100.0);
        
        // Save it to the database
        testBidList = bidListRepository.save(testBidList);
    }

    @AfterEach
    public void tearDown() {
        // Clean up after test
        bidListRepository.deleteAll();
    }

    @Test
    public void testCreateBidList() {
        // Verify the BidList was created with an ID
        assertNotNull(testBidList.getBidListId());
        assertEquals("Integration Test Account", testBidList.getAccount());
        assertEquals("Integration Test Type", testBidList.getType());
        assertEquals(100.0, testBidList.getBidQuantity());
    }

    @Test
    public void testFindBidListById() {
        // Find the BidList by ID
        Optional<BidList> foundBidList = bidListRepository.findById(testBidList.getBidListId());
        
        // Verify it was found
        assertTrue(foundBidList.isPresent());
        assertEquals(testBidList.getAccount(), foundBidList.get().getAccount());
    }

    @Test
    public void testUpdateBidList() {
        // Update the BidList
        testBidList.setAccount("Updated Account");
        testBidList.setBidQuantity(200.0);
        bidListRepository.save(testBidList);
        
        // Find the updated BidList
        Optional<BidList> updatedBidList = bidListRepository.findById(testBidList.getBidListId());
        
        // Verify it was updated
        assertTrue(updatedBidList.isPresent());
        assertEquals("Updated Account", updatedBidList.get().getAccount());
        assertEquals(200.0, updatedBidList.get().getBidQuantity());
    }

    @Test
    public void testDeleteBidList() {
        // Delete the BidList
        bidListRepository.deleteById(testBidList.getBidListId());
        
        // Verify it was deleted
        Optional<BidList> deletedBidList = bidListRepository.findById(testBidList.getBidListId());
        assertFalse(deletedBidList.isPresent());
    }

    @Test
    public void testFindAllBidLists() {
        // Create another BidList
        BidList anotherBidList = new BidList();
        anotherBidList.setAccount("Another Account");
        anotherBidList.setType("Another Type");
        anotherBidList.setBidQuantity(300.0);
        bidListRepository.save(anotherBidList);
        
        // Find all BidLists
        List<BidList> allBidLists = bidListRepository.findAll();
        
        // Verify there are at least 2 BidLists
        assertTrue(allBidLists.size() >= 2);
    }
}
package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class BidListControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BidListService bidListService;

    @InjectMocks
    private BidListController bidListController;

    private BidList bidList;

    @BeforeEach
    public void setUp() {
        bidList = new BidList();
        bidList.setBidListId(1);
        bidList.setAccount("Account Test");
        bidList.setType("Type Test");
        bidList.setBidQuantity(10.0);

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(bidListController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testHome() throws Exception {
        // Given
        when(bidListService.findAll()).thenReturn(Arrays.asList(bidList));

        // When & Then
        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidLists"));

        verify(bidListService).findAll();
    }

    @Test
    public void testAddBidForm() throws Exception {
        // When & Then
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeExists("bidList"));
    }

    @Test
    public void testValidate_Success() throws Exception {
        // Given
        when(bidListService.save(any(BidList.class))).thenReturn(bidList);

        // When & Then
        mockMvc.perform(post("/bidList/validate")
                .param("account", "Account Test")
                .param("type", "Type Test")
                .param("bidQuantity", "10.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService).save(any(BidList.class));
    }

    @Test
    public void testValidate_Error() throws Exception {
        // When & Then
        mockMvc.perform(post("/bidList/validate")
                .param("account", "") // Empty account to trigger validation error
                .param("type", "Type Test")
                .param("bidQuantity", "10.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));

        verify(bidListService, never()).save(any(BidList.class));
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        // Given
        when(bidListService.findById(1)).thenReturn(Optional.of(bidList));

        // When & Then
        mockMvc.perform(get("/bidList/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidList"));

        verify(bidListService).findById(1);
    }

    @Test
    public void testShowUpdateForm_NotFound() throws Exception {
        // Given
        when(bidListService.findById(999)).thenReturn(Optional.empty());

        // When & Then
        try {
            mockMvc.perform(get("/bidList/update/999"));
        } catch (Exception e) {
            // Expected exception
            assertTrue(e.getCause() instanceof IllegalArgumentException);
            assertTrue(e.getCause().getMessage().contains("Invalid bid Id:999"));
        }

        verify(bidListService).findById(999);
    }

    @Test
    public void testUpdateBid_Success() throws Exception {
        // Given
        when(bidListService.update(eq(1), any(BidList.class))).thenReturn(bidList);

        // When & Then
        mockMvc.perform(post("/bidList/update/1")
                .param("account", "Updated Account")
                .param("type", "Updated Type")
                .param("bidQuantity", "20.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService).update(eq(1), any(BidList.class));
    }

    @Test
    public void testUpdateBid_Error() throws Exception {
        // When & Then
        mockMvc.perform(post("/bidList/update/1")
                .param("account", "") // Empty account to trigger validation error
                .param("type", "Updated Type")
                .param("bidQuantity", "20.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"));

        verify(bidListService, never()).update(eq(1), any(BidList.class));
    }

    @Test
    public void testDeleteBid() throws Exception {
        // When & Then
        mockMvc.perform(get("/bidList/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService).deleteById(1);
    }
}

package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
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
public class CurveControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CurvePointService curvePointService;

    @InjectMocks
    private CurveController curveController;

    private CurvePoint curvePoint;

    @BeforeEach
    public void setUp() {
        curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(10);
        curvePoint.setTerm(2.0);
        curvePoint.setValue(30.0);

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(curveController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testHome() throws Exception {
        // Given
        when(curvePointService.findAll()).thenReturn(Arrays.asList(curvePoint));

        // When & Then
        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"));

        verify(curvePointService).findAll();
    }

    @Test
    public void testAddCurvePointForm() throws Exception {
        // When & Then
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeExists("curvePoint"));
    }

    @Test
    public void testValidate_Success() throws Exception {
        // Given
        when(curvePointService.save(any(CurvePoint.class))).thenReturn(curvePoint);

        // When & Then
        mockMvc.perform(post("/curvePoint/validate")
                .param("curveId", "10")
                .param("term", "2.0")
                .param("value", "30.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService).save(any(CurvePoint.class));
    }

    @Test
    public void testValidate_Error() throws Exception {
        // When & Then
        mockMvc.perform(post("/curvePoint/validate")
                .param("curveId", "") // Empty curveId to trigger validation error
                .param("term", "2.0")
                .param("value", "30.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));

        verify(curvePointService, never()).save(any(CurvePoint.class));
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        // Given
        when(curvePointService.findById(1)).thenReturn(Optional.of(curvePoint));

        // When & Then
        mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("curvePoint"));

        verify(curvePointService).findById(1);
    }

    @Test
    public void testShowUpdateForm_NotFound() throws Exception {
        // Given
        when(curvePointService.findById(999)).thenReturn(Optional.empty());

        // When & Then
        try {
            mockMvc.perform(get("/curvePoint/update/999"));
        } catch (Exception e) {
            // Expected exception
            assertTrue(e.getCause() instanceof IllegalArgumentException);
            assertTrue(e.getCause().getMessage().contains("Invalid curve point Id:999"));
        }

        verify(curvePointService).findById(999);
    }

    @Test
    public void testUpdateCurvePoint_Success() throws Exception {
        // Given
        when(curvePointService.update(eq(1), any(CurvePoint.class))).thenReturn(curvePoint);

        // When & Then
        mockMvc.perform(post("/curvePoint/update/1")
                .param("curveId", "20")
                .param("term", "3.0")
                .param("value", "40.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService).update(eq(1), any(CurvePoint.class));
    }

    @Test
    public void testUpdateCurvePoint_Error() throws Exception {
        // When & Then
        mockMvc.perform(post("/curvePoint/update/1")
                .param("curveId", "") // Empty curveId to trigger validation error
                .param("term", "3.0")
                .param("value", "40.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));

        verify(curvePointService, never()).update(eq(1), any(CurvePoint.class));
    }

    @Test
    public void testDeleteCurvePoint() throws Exception {
        // When & Then
        mockMvc.perform(get("/curvePoint/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService).deleteById(1);
    }
}

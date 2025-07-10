package com.nnk.springboot.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
public class CustomErrorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private CustomErrorController customErrorController;

    @BeforeEach
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(customErrorController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testHandleError_Forbidden() {
        // Given
        when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE))
                .thenReturn(HttpStatus.FORBIDDEN.value());

        // When & Then
        String viewName = customErrorController.handleError(request);

        // Assert
        assert viewName.equals("403");
    }

    @Test
    public void testHandleError_OtherError() {
        // Given
        when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE))
                .thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.value());

        // When & Then
        String viewName = customErrorController.handleError(request);

        // Assert
        assert viewName.equals("error");
    }

    @Test
    public void testHandleError_NoStatusCode() {
        // Given
        when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE))
                .thenReturn(null);

        // When & Then
        String viewName = customErrorController.handleError(request);

        // Assert
        assert viewName.equals("error");
    }

    @Test
    public void testErrorEndpoint() throws Exception {
        // When & Then
        mockMvc.perform(get("/error"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
    }
}

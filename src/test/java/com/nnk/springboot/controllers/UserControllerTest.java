package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserController userController;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1);
        user.setUsername("testuser");
        user.setPassword("Password1!");
        user.setFullname("Test User");
        user.setRole("USER");

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testHome() throws Exception {
        // Given
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        // When & Then
        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"));

        verify(userRepository).findAll();
    }

    @Test
    public void testAddUserForm() throws Exception {
        // When & Then
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    public void testValidate_Success() throws Exception {
        // Given
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        // When & Then
        mockMvc.perform(post("/user/validate")
                .param("username", "testuser")
                .param("password", "Password1!")
                .param("fullname", "Test User")
                .param("role", "USER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(passwordEncoder).encode("Password1!");
        verify(userRepository).save(any(User.class));
        verify(userRepository).findAll();
    }

    @Test
    public void testValidate_Error() throws Exception {
        // When & Then
        mockMvc.perform(post("/user/validate")
                .param("username", "") // Empty username to trigger validation error
                .param("password", "Password1!")
                .param("fullname", "Test User")
                .param("role", "USER"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));

        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        // Given
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // When & Then
        mockMvc.perform(get("/user/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeExists("user"));

        verify(userRepository).findById(1);
    }

    @Test
    public void testShowUpdateForm_NotFound() throws Exception {
        // Given
        when(userRepository.findById(999)).thenReturn(Optional.empty());

        // When & Then
        try {
            mockMvc.perform(get("/user/update/999"));
        } catch (Exception e) {
            // Expected exception
            assertTrue(e.getCause() instanceof IllegalArgumentException);
            assertTrue(e.getCause().getMessage().contains("Invalid user Id:999"));
        }

        verify(userRepository).findById(999);
    }

    @Test
    public void testUpdateUser_Success() throws Exception {
        // Given
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        // When & Then
        mockMvc.perform(post("/user/update/1")
                .param("username", "updateduser")
                .param("password", "UpdatedPassword1!")
                .param("fullname", "Updated User")
                .param("role", "ADMIN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(passwordEncoder).encode("UpdatedPassword1!");
        verify(userRepository).save(any(User.class));
        verify(userRepository).findAll();
    }

    @Test
    public void testUpdateUser_Error() throws Exception {
        // When & Then
        mockMvc.perform(post("/user/update/1")
                .param("username", "") // Empty username to trigger validation error
                .param("password", "UpdatedPassword1!")
                .param("fullname", "Updated User")
                .param("role", "ADMIN"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));

        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testDeleteUser() throws Exception {
        // Given
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.findAll()).thenReturn(Arrays.asList());

        // When & Then
        mockMvc.perform(get("/user/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(userRepository).findById(1);
        verify(userRepository).delete(user);
        verify(userRepository).findAll();
    }

    @Test
    public void testDeleteUser_NotFound() throws Exception {
        // Given
        when(userRepository.findById(999)).thenReturn(Optional.empty());

        // When & Then
        try {
            mockMvc.perform(get("/user/delete/999"));
        } catch (Exception e) {
            // Expected exception
            assertTrue(e.getCause() instanceof IllegalArgumentException);
            assertTrue(e.getCause().getMessage().contains("Invalid user Id:999"));
        }

        verify(userRepository).findById(999);
        verify(userRepository, never()).delete(any(User.class));
    }
}

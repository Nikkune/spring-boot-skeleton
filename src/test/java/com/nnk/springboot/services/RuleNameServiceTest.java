package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
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
public class RuleNameServiceTest {

    @Mock
    private RuleNameRepository ruleNameRepository;

    @InjectMocks
    private RuleNameService ruleNameService;

    private RuleName ruleName;

    @BeforeEach
    public void setUp() {
        ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("Rule Test");
        ruleName.setDescription("Description Test");
        ruleName.setJson("Json Test");
        ruleName.setTemplate("Template Test");
        ruleName.setSqlStr("SQL String Test");
        ruleName.setSqlPart("SQL Part Test");
    }

    @Test
    public void testFindAll() {
        // Given
        when(ruleNameRepository.findAll()).thenReturn(Arrays.asList(ruleName));

        // When
        List<RuleName> result = ruleNameService.findAll();

        // Then
        assertEquals(1, result.size());
        assertEquals(ruleName.getName(), result.get(0).getName());
        verify(ruleNameRepository).findAll();
    }

    @Test
    public void testFindById() {
        // Given
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));

        // When
        Optional<RuleName> result = ruleNameService.findById(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals(ruleName.getName(), result.get().getName());
        verify(ruleNameRepository).findById(1);
    }

    @Test
    public void testFindById_NotFound() {
        // Given
        when(ruleNameRepository.findById(999)).thenReturn(Optional.empty());

        // When
        Optional<RuleName> result = ruleNameService.findById(999);

        // Then
        assertFalse(result.isPresent());
        verify(ruleNameRepository).findById(999);
    }

    @Test
    public void testSave() {
        // Given
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);

        // When
        RuleName result = ruleNameService.save(ruleName);

        // Then
        assertEquals(ruleName.getName(), result.getName());
        verify(ruleNameRepository).save(ruleName);
    }

    @Test
    public void testUpdate() {
        // Given
        RuleName updatedRuleName = new RuleName();
        updatedRuleName.setName("Updated Rule");
        updatedRuleName.setDescription("Updated Description");
        updatedRuleName.setJson("Updated Json");
        updatedRuleName.setTemplate("Updated Template");
        updatedRuleName.setSqlStr("Updated SQL String");
        updatedRuleName.setSqlPart("Updated SQL Part");

        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(updatedRuleName);

        // When
        RuleName result = ruleNameService.update(1, updatedRuleName);

        // Then
        assertEquals(1, result.getId());
        assertEquals(updatedRuleName.getName(), result.getName());
        verify(ruleNameRepository).save(updatedRuleName);
    }

    @Test
    public void testDeleteById() {
        // When
        ruleNameService.deleteById(1);

        // Then
        verify(ruleNameRepository).deleteById(1);
    }
}
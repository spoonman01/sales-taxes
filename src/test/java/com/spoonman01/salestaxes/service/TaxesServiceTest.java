package com.spoonman01.salestaxes.service;

import com.spoonman01.salestaxes.MockedData;
import com.spoonman01.salestaxes.repository.ExcludedTaxesDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TaxesServiceTest {

    @Value("${import.search.word:imported}")
    private String importWord;
    @Value("${import.percentage:0.05}")
    private Double importPercentage;
    @Value("${basic.percentage:0.1}")
    private Double basicPercentage;

    @Mock
    private ExcludedTaxesDAO excludedTaxesDAO;

    private TaxesService taxesService;

    @Before
    public void setUp() {
        when(excludedTaxesDAO.existsByDescriptionContaining(anyString())).thenReturn(false);
        when(excludedTaxesDAO.existsByDescriptionContaining(contains("book"))).thenReturn(true);
        when(excludedTaxesDAO.existsByDescriptionContaining(contains("chocolate"))).thenReturn(true);
        when(excludedTaxesDAO.existsByDescriptionContaining(contains("pills"))).thenReturn(true);

        taxesService = new TaxesService(excludedTaxesDAO, importWord, importPercentage, basicPercentage);
    }

    @Test
    public void calculateTaxes_givenInputNoImport_thenReturnsCorrectOutput() {
        String output = taxesService.calculateTaxes(MockedData.mockedInputNoImport());
        assertThat(output).isEqualTo(MockedData.mockedOutputNoImport());
    }

    @Test
    public void calculateTaxes_givenInputAllImport_thenReturnsCorrectOutput() {
        String output = taxesService.calculateTaxes(MockedData.mockedInputAllImport());
        assertThat(output).isEqualTo(MockedData.mockedOutputAllImport());
    }

    @Test
    public void calculateTaxes_givenInputMixedImport_thenReturnsCorrectOutput() {
        String output = taxesService.calculateTaxes(MockedData.mockedInputMixedImport());
        assertThat(output).isEqualTo(MockedData.mockedOutputMixedImport());
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateTaxes_givenBadString_thenThrowException() {
        String outputString = taxesService.calculateTaxes(List.of("A","B","C"));
    }
}

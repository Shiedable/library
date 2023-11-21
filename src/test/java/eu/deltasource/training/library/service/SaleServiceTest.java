package eu.deltasource.training.library.service;

import eu.deltasource.training.library.exceptions.*;
import eu.deltasource.training.library.model.Sale;
import eu.deltasource.training.library.repository.SalesRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SaleServiceTest {

    private static SaleService saleService;
    private static SalesRepository mockedSaleRepository;

    @BeforeAll
    public static void initialize() {
        mockedSaleRepository = Mockito.mock(SalesRepository.class);
        saleService = new SaleService(mockedSaleRepository);
    }


    //CREATE RELATED TESTS
    @Test
    public void givenEmptyStringSaleDate_WhenAddingSale_ThenThrowInvalidDateException() {
        //Given
        String saleDateString = "";
        int quantity = 10;

        //When

        //Then
        assertThrows(InvalidDateException.class, () -> saleService.addSale(saleDateString, quantity));
    }

    @Test
    public void givenNullStringSaleDate_WhenAddingSale_ThenThrowInvalidDateException() {
        //Given
        String saleDateString = null;
        int quantity = 10;

        //When

        //Then
        assertThrows(InvalidDateException.class, () -> saleService.addSale(saleDateString, quantity));
    }

    @Test
    public void givenInvalidStringSaleDate_WhenAddingSale_ThenThrowInvalidDateException() {
        //Given
        String saleDateString = "ima li prodajbi";
        int quantity = 10;

        //When

        //Then
        assertThrows(InvalidDateException.class, () -> saleService.addSale(saleDateString, quantity));
    }

    @Test
    public void givenNegativeQuantity_WhenAddingSale_ThenThrowNegativeNumberException() {
        //Given
        String saleDateString = "2000-01-01";
        int quantity = -10;

        //When

        //Then
        assertThrows(NegativeNumberException.class, () -> saleService.addSale(saleDateString, quantity));
    }

    //DELETE RELATED TESTS
    @Test
    public void givenNegativeIndex_WhenDeletingSale_ThenThrowNegativeIdException() {
        //Given
        long saleId = -1;

        //When

        //Then
        assertThrows(NegativeIdException.class, () -> saleService.deleteSaleById(saleId));
    }

    @Test
    public void givenIndexThatDoesNotExist_WhenDeletingSale_ThenThrowIdNotFoundException() {
        //Given
        long saleId = 9999;

        //When

        //Then
        assertThrows(IdNotFoundException.class, () -> saleService.deleteSaleById(saleId));
    }

    //UPDATE RELATED TESTS
    @Test
    public void givenNegativeIndex_WhenUpdatingSale_ThenThrowNegativeIdException() {
        //Given
        long saleId = -1;

        //When

        //Then
        assertThrows(NegativeIdException.class, () -> saleService.deleteSaleById(saleId));
    }

    @Test
    public void givenIndexThatDoesNotExist_WhenUpdatingSale_ThenThrowNegativeIdException() {
        //Given
        long saleId = 9999;

        //When

        //Then
        assertThrows(IdNotFoundException.class, () -> saleService.deleteSaleById(saleId));
    }

    @Test
    public void givenInvalidStringSaleDate_WhenUpdatingSale_ThenInvalidDateException() {
        //Given
        String saleDateString = "go6o mi e";
        int quantity = 10;

        //When

        //Then
        assertThrows(InvalidDateException.class, () -> saleService.addSale(saleDateString, quantity));
    }

    @Test
    public void givenNegativeQuantity_WhenUpdatingSale_ThenNegativeNumberException() {
        //Given
        String saleDateString = "2000-01-01";
        int quantity = -10;

        //When

        //Then
        assertThrows(NegativeNumberException.class, () -> saleService.addSale(saleDateString, quantity));
    }

    //READ RELATED TESTS
    @Test
    public void givenNegativeIndex_WhenGettingASale_ThenThrowNegativeIdException() {
        //Given
        long saleId = -1;

        //When

        //Then
        assertThrows(NegativeIdException.class, () -> saleService.deleteSaleById(saleId));
    }

    @Test
    public void givenNonExistentIndex_WhenGettingASale_ThenThrowIdNotFoundException() {
        //Given
        long saleId = 9999;

        //When

        //Then
        assertThrows(IdNotFoundException.class, () -> saleService.deleteSaleById(saleId));
    }

    @Test
    public void givenEmptyRepository_WhenGettingAllSales_ThenGetEmptyList() {
        //Given
        List<Sale> emptyList = new ArrayList<>();

        //When
        Mockito.when(mockedSaleRepository.findAll()).thenReturn(emptyList);

        //Then
        assertThat(saleService.getAllSales().isEmpty(), is(true));
    }

    @Test
    public void givenRepositoryWithSales_WhenGettingAllSales_ThenGetListOfSales() {
        //Given
        List<Sale> saleList = new ArrayList<>();
        Sale sale1 = new Sale(LocalDate.parse("2000-01-01"), 10);
        Sale sale2 = new Sale(LocalDate.parse("2000-02-02"), 100);
        Sale sale3 = new Sale(LocalDate.parse("2000-03-03"), 1000);
        saleList.add(sale1);
        saleList.add(sale2);
        saleList.add(sale3);

        //When
        Mockito.when(mockedSaleRepository.findAll()).thenReturn(saleList);

        //Then
        assertThat(saleService.getAllSales().contains(sale1), is(true));
        assertThat(saleService.getAllSales().contains(sale2), is(true));
        assertThat(saleService.getAllSales().contains(sale3), is(true));
    }
}

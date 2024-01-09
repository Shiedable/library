package eu.deltasource.training.library.service;

import eu.deltasource.training.library.exceptions.*;
import eu.deltasource.training.library.model.Book;
import eu.deltasource.training.library.model.Sale;
import eu.deltasource.training.library.repository.SaleRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SaleServiceTest {

    private SaleService saleService;
    private SaleRepository mockedSaleRepository;
    private BookService mockedBookService;

    @BeforeAll
    public void initialize() {
        mockedSaleRepository = Mockito.mock(SaleRepository.class);
        mockedBookService = Mockito.mock(BookService.class);
        saleService = new SaleService(mockedSaleRepository, mockedBookService);
    }


    @Test
    public void givenEmptyStringSaleDate_WhenAddingSale_ThenThrowInvalidSaleException() {
        //Given
        String saleDateString = "";
        Optional<Integer> quantity = Optional.of(10);
        long bookId = 1;

        //When

        //Then
        assertThrows(InvalidSaleException.class, () -> saleService.addSale(saleDateString, quantity, bookId));
    }

    @Test
    public void givenNullStringSaleDate_WhenAddingSale_ThenThrowInvalidSaleException() {
        //Given
        String saleDateString = null;
        Optional<Integer> quantity = Optional.of(10);
        long bookId = 1;

        //When
        Mockito.when(mockedBookService.getBookById(bookId)).thenReturn(Optional.of(new Book()));

        //Then
        assertThrows(InvalidSaleException.class, () -> saleService.addSale(saleDateString, quantity, bookId));
    }

    @Test
    public void givenInvalidStringSaleDate_WhenAddingSale_ThenThrowInvalidSaleException() {
        //Given
        String saleDateString = "ima li prodajbi";
        Optional<Integer> quantity = Optional.of(10);
        long bookId = 1;

        //When
        Mockito.when(mockedBookService.getBookById(bookId)).thenReturn(Optional.of(new Book()));

        //Then
        assertThrows(InvalidSaleException.class, () -> saleService.addSale(saleDateString, quantity, bookId));
    }

    @Test
    public void givenNegativeQuantity_WhenAddingSale_ThenThrowInvalidSaleException() {
        //Given
        String saleDateString = "2000-01-01";
        Optional<Integer> quantity = Optional.of(-10);
        long bookId = 1;

        //When
        Mockito.when(mockedBookService.getBookById(bookId)).thenReturn(Optional.of(new Book()));

        //Then
        assertThrows(InvalidSaleException.class, () -> saleService.addSale(saleDateString, quantity, bookId));
    }

    @Test
    public void givenNegativeIndex_WhenDeletingSale_ThenThrowEntityNotFoundException() {
        //Given
        long saleId = -1;

        //When

        //Then
        assertThrows(EntityNotFoundException.class, () -> saleService.deleteSaleById(saleId));
    }

    @Test
    public void givenIndexThatDoesNotExist_WhenDeletingSale_ThenThrowIdNotFoundException() {
        //Given
        long saleId = 9999;

        //When

        //Then
        assertThrows(EntityNotFoundException.class, () -> saleService.deleteSaleById(saleId));
    }

    @Test
    public void givenNegativeIndex_WhenUpdatingSale_ThenThrowEntityNotFoundException() {
        //Given
        String saleDateString = "2000-01-01";
        Optional<Integer> quantity = Optional.of(10);
        long saleId = -1;
        long bookId = 1;

        //When

        //Then
        assertThrows(EntityNotFoundException.class,
                () -> saleService.updateSaleById(saleId, saleDateString, quantity, bookId));
    }

    @Test
    public void givenIndexThatDoesNotExist_WhenUpdatingSale_ThenThrowEntityNotFoundException() {
        //Given
        String saleDateString = "2000-01-01";
        Optional<Integer> quantity = Optional.of(10);
        long saleId = 9999;
        long bookId = 1;

        //When

        //Then
        assertThrows(EntityNotFoundException.class,
                () -> saleService.updateSaleById(saleId, saleDateString, quantity, bookId));
    }

    @Test
    public void givenInvalidStringSaleDate_WhenUpdatingSale_ThenThrowEntityNotFoundException() {
        //Given
        String saleDateString = "go6o mi e";
        Optional<Integer> quantity = Optional.of(10);
        long saleId = 1;
        long bookId = 1;

        //When

        //Then
        assertThrows(EntityNotFoundException.class,
                () -> saleService.updateSaleById(saleId, saleDateString, quantity, bookId));
    }

    @Test
    public void givenNegativeQuantity_WhenUpdatingSale_ThenThrowInvalidSaleException() {
        //Given
        String saleDateString = "2000-01-01";
        Optional<Integer> quantity = Optional.of(-10);
        long saleId = 1;
        long bookId = 1;

        //When
        Mockito.when(mockedSaleRepository.findById(bookId)).thenReturn(Optional.of(new Sale()));

        //Then
        assertThrows(InvalidSaleException.class,
                () -> saleService.updateSaleById(saleId, saleDateString, quantity, bookId));
    }

    @Test
    public void givenNegativeIndex_WhenGettingASale_ThenThrowEntityNotFoundException() {
        //Given
        long saleId = -1;

        //When

        //Then
        assertThrows(EntityNotFoundException.class, () -> saleService.deleteSaleById(saleId));
    }

    @Test
    public void givenNonExistentIndex_WhenGettingASale_ThenThrowEntityNotFoundException() {
        //Given
        long saleId = 9999;

        //When

        //Then
        assertThrows(EntityNotFoundException.class, () -> saleService.deleteSaleById(saleId));
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
        Book book =  new Book();
        Sale sale1 = new Sale("2000-01-01", 10, book);
        Sale sale2 = new Sale("2000-02-02", 100, book);
        Sale sale3 = new Sale("2000-03-03", 1000, book);
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

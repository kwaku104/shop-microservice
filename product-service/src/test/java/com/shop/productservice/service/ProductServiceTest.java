package com.shop.productservice.service;

import com.shop.productservice.dto.ProductRequest;
import com.shop.productservice.dto.ProductResponse;
import com.shop.productservice.model.Product;
import com.shop.productservice.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    private AutoCloseable closeable;
    private ProductService productService;

    @Captor
    private ArgumentCaptor<Product> productCaptor;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository);
    }

    @AfterEach
    public void tearDown () throws Exception {
        closeable.close();
    }

    @Test
    void givenProductRequest_whenCreateProduct_thenProductIsSaved() {
        // Given
        ProductRequest productRequest = new ProductRequest("PS5", "This is a PS5", BigDecimal.valueOf(499.0));

        // When
        productService.createProduct(productRequest);

        // Then
        verify(productRepository, times(1)).save(productCaptor.capture());
        Product savedProduct = productCaptor.getValue();
        assertThat(savedProduct.getName(), is(productRequest.getName()));
        assertThat(savedProduct.getDescription(), is(productRequest.getDescription()));
        assertThat(savedProduct.getPrice(), is(productRequest.getPrice()));
    }

    @Test
    public void givenNoProducts_whenGetAllProducts_thenReturnsEmptyList() {
        // Given
        given(productRepository.findAll()).willReturn(Collections.emptyList());

        // Then
        assertThat(productService.getAllProducts(), is(empty()
        ));
    }

    @Test
    void givenProductsExist_whenGetAllProducts_thenReturnsAllProducts() {
        Product product1 = new Product(null, "PS5", "This is a PS5", BigDecimal.valueOf(499.0));
        Product product2 = new Product(null, "TV", "This is a TV", BigDecimal.valueOf(600.0));
        Product product3 = new Product(null, "Laptop", "This is a Laptop", BigDecimal.valueOf(499.0));
        List<Product> products = new ArrayList<>(Arrays.asList(product1, product2, product3));

        ProductResponse productResponse1 = new ProductResponse(null, "PS5", "This is a PS5", BigDecimal.valueOf(499.0));
        ProductResponse productResponse2 = new ProductResponse(null, "TV", "This is a TV", BigDecimal.valueOf(600.0));
        ProductResponse productResponse3 = new ProductResponse(null, "Laptop", "This is a Laptop", BigDecimal.valueOf(499.0));
        List<ProductResponse> productResponses = new ArrayList<>(Arrays.asList(productResponse1, productResponse2, productResponse3));

        given(productRepository.findAll()).willReturn(products);

        assertThat(productService.getAllProducts(), is(productResponses));
    }
}
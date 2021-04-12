package ar.com.manflack.manflackshops.domain.service.impl;

import java.util.Arrays;
import java.util.List;

import ar.com.manflack.manflackshops.app.dto.ProductDTO;
import ar.com.manflack.manflackshops.app.dto.ProductDTOFixture;
import ar.com.manflack.manflackshops.app.filter.ProductFilter;
import ar.com.manflack.manflackshops.domain.UtilitiesComponent;
import ar.com.manflack.manflackshops.domain.exception.ManyFiltersException;
import ar.com.manflack.manflackshops.domain.exception.NotImplementedFilterOrder;
import ar.com.manflack.manflackshops.domain.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@EnableWebMvc
public class ProductServiceImplTest
{
    @InjectMocks
    ProductServiceImpl service;

    @Mock
    ProductRepository repository;

    @Mock
    UtilitiesComponent utilities;

    @Test
    public void getAll_OK() throws NotImplementedFilterOrder, ManyFiltersException
    {
        List<ProductDTO> productLists = Arrays.asList(ProductDTOFixture.withDefaults1(),
                ProductDTOFixture.withDefaults2(),
                ProductDTOFixture.withDefaults3(),
                ProductDTOFixture.withDefaults4());

        when(repository.getAll()).thenReturn(productLists);
        when(utilities.filterProducts(any(), any())).thenReturn(productLists.stream());

        List<ProductDTO> response = service.getAllProducts(new ProductFilter());
        // el profe brian (alias el brashan)
        assertNotNull(response);
        assertEquals(4, response.size());
    }
}

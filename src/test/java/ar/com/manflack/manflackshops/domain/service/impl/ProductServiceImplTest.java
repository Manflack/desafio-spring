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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/*
    All the explanation of this annotations are in the Controller.
    The general idea to test a service is, given all class that depend,
    mock all them, I test only the code of one method. If the code it's large,
    and segmented by methods calls, we are enable to test anyway.
    All the external method calls that aren't part of my service class, will be mocked
 */
@RunWith(MockitoJUnitRunner.class)
@EnableWebMvc
public class ProductServiceImplTest
{
    @InjectMocks
    ProductServiceImpl service;

    // We need test only ProductServiceImpl, then means that
    // repository and utilities will not be called
    @Mock
    ProductRepository repository;

    @Mock
    UtilitiesComponent utilities;

    /* In case that we want set an attribute, a value, we can create:
       @Before
       public void setup()
       {
            ReflectionTestUtils.setField(service, "pspId", "0088");
            name of the service field, name of the attribute to mock, value to set
        }
    */

    @Test
    public void getAll_OK() throws NotImplementedFilterOrder, ManyFiltersException
    {
        List<ProductDTO> productLists = Arrays.asList(ProductDTOFixture.withDefaults1(),
                ProductDTOFixture.withDefaults2(),
                ProductDTOFixture.withDefaults3(),
                ProductDTOFixture.withDefaults4());

        when(repository.getAll()).thenReturn(productLists);
        when(utilities.filterProducts(any(), any())).thenReturn(productLists.stream());

        // we call the service normally, but all ATTRIBUTES of the class
        // aren't initialized, except the services that we define before with
        // the @Mock annotation, what Mockito inject a bean for supervise the calls method
        List<ProductDTO> response = service.getAllProducts(new ProductFilter());

        // el profe brian (alias el brashan)
        assertNotNull(response);
        assertEquals(4, response.size());

        verify(repository, times(1)).getAll();
        verify(utilities, times(1)).filterProducts(any(), any());
    }
}

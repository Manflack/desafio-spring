package ar.com.manflack.manflackshops.app.rest;

import java.util.Arrays;
import java.util.List;

import ar.com.manflack.manflackshops.app.dto.ProductDTO;
import ar.com.manflack.manflackshops.app.dto.ProductDTOFixture;
import ar.com.manflack.manflackshops.domain.UtilitiesComponent;
import ar.com.manflack.manflackshops.domain.service.impl.ProductServiceImpl;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/*
    Declare the class with Mockito runner, instead of being initialized
    entirely by spring, Mockito and his magic will initialize the application
    The EnableWebMvc i understand that enable the view controller response, idk, magic
 */
@RunWith(MockitoJUnitRunner.class)
@EnableWebMvc
public class ProductControllerTest
{
    /*
        Important! Read first what does the annotation mock before this explanation.

        The InjectMocks annotation Inject the "false" dependencies declared with
        @Mock annotation, to the class to Inject, in this case, the controller.
        This annotation only we'll use it to the class TO test.
     */
    @InjectMocks
    ProductController productController;

    /*
        The @Mock annotation inject a false dependency. This it's orchestrated by
        a Mockito "container", that supervise all the method's call to the class/dependency.
        Why is supervised? Because when we use the mockito static method 'when.thenReturn',
        Mockito detect this call to his dependency and will return the defined object
     */
    @Mock
    ProductServiceImpl service;

    /*
        In this case, I use @Mock again, because my controller call a class called 'UtilitiesComponent',
        if I not declare this class with @Mock, my test will throw a NullPointerException, because my
        class it's null.
     */
    @Mock
    UtilitiesComponent utilities;

    private MockMvc mvc;

    private ObjectMapper mapper;

    /*
        The @Before annotation means that the method setup, will be executed BEFORE
        anything, its mean, before calls @Test methods
     */
    @Before
    public void setup()
    {
        // Register the controller instance to the Mvc calls
        mvc = MockMvcBuilders.standaloneSetup(productController)
                //.setControllerAdvice(new RestExceptionHandler())
                .build();

        // Magic to transform object expressed in String arrays to Object Arrays
        mapper = new ObjectMapper().findAndRegisterModules()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    /*
    
     */
    @Test
    public void getAll_OK() throws Exception
    {
        List<ProductDTO> productLists = Arrays.asList(ProductDTOFixture.withDefaults1(),
                ProductDTOFixture.withDefaults2(),
                ProductDTOFixture.withDefaults3(),
                ProductDTOFixture.withDefaults4());

        when(service.getAllProducts(any())).thenReturn(productLists);

        MvcResult result = mvc.perform(get("/api/v1/articles").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertNotNull(response);

        List<ProductDTO> listResponse = Arrays.asList(mapper.readValue(response, ProductDTO[].class));

        assertNotNull(listResponse);
        assertEquals(4, listResponse.size());
    }
}

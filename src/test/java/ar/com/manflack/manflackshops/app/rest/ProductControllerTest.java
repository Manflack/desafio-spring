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
import static org.mockito.Mockito.*;
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
        After the class instance was intialized, Mockito runs all the
        method that are VOID and have the annotation @Test
     */
    @Test
    public void getAll_OK() throws Exception
    {
        // Create an arraylist that contain 4 products
        List<ProductDTO> productLists = Arrays.asList(ProductDTOFixture.withDefaults1(),
                ProductDTOFixture.withDefaults2(),
                ProductDTOFixture.withDefaults3(),
                ProductDTOFixture.withDefaults4());

        // When the controller is executed, in this sentence
        // we says "when the service.method is executed with ANY
        // type of argument, then return my product list declared above
        when(service.getAllProducts(any())).thenReturn(productLists);

        // In this sentence, we simulate a call of kind "Postman", type
        // GET, the URL, what content type is, and what we expect of the
        // response
        MvcResult result = mvc.perform(get("/api/v1/articles").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertNotNull(response);

        // convert the response json string to an object array
        List<ProductDTO> listResponse = Arrays.asList(mapper.readValue(response, ProductDTO[].class));

        // first assert the listResponse to verify that isn't null
        // if is null, mockito throw an exception, instead we get a
        // NullPointerException
        assertNotNull(listResponse);
        // ATTENTION, the assert is component by EXPECTED, ACTUAL, DELTA
        // for convention, we put STATIC values in the expected argument,
        // and the "variables/responses" in the ACTUAL
        assertEquals(4, listResponse.size());


        // we assert that the service, was called 1 times,
        // and was the method getProductFilter given ANY parameters
        verify(utilities, times(1)).getProductFilter(any(), any(), any(), any(), any(), any(), any(), any());
        verify(service, times(1)).getAllProducts(any());
    }
}

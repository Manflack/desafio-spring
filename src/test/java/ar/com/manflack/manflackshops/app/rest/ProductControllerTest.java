package ar.com.manflack.manflackshops.app.rest;

import java.util.Arrays;
import java.util.List;

import ar.com.manflack.manflackshops.app.dto.ProductDTO;
import ar.com.manflack.manflackshops.app.dto.ProductDTOFixture;
import ar.com.manflack.manflackshops.domain.service.impl.ProductServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({ SpringExtension.class })
@AutoConfigureMockMvc
public class ProductControllerTest
{
    @Autowired
    private MockMvc mvc;

    @Mock
    ProductServiceImpl service;

    @Test
    public void getAll_OK() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();

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

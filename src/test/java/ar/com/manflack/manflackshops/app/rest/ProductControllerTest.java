package ar.com.manflack.manflackshops.app.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith( { SpringExtension.class } )
@AutoConfigureMockMvc
public class ProductControllerTest
{
    @Autowired
    private MockMvc mvc;

    //@Test
    public void getAll_OK() throws Exception
    {
        MvcResult result =
                mvc.perform(get("/api/v1/articles").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        result.getResponse().getContentAsString();
    }
}

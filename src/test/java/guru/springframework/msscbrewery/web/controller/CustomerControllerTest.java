package guru.springframework.msscbrewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbrewery.services.CustomerService;
import guru.springframework.msscbrewery.web.model.CustomerDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    @MockBean
    CustomerService customerService;
    CustomerDto customerDto;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Before
    public void preSetup(){
        customerDto=  CustomerDto.builder().id(UUID.randomUUID()).name("John Doe").build();
    }

    @Test
    public void getCustomer() throws Exception {
given(customerService.getCustomerById(any(UUID.class))).willReturn(customerDto);
    mockMvc.perform(get("/api/v1/customer/" + customerDto.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(customerDto.getId().toString())))
            .andExpect(jsonPath("$.name", is("John Doe")));
    }

    @Test
    public void createCustomer() throws Exception {
        given(customerService.saveNewCustomer(any(CustomerDto.class))).willReturn(customerDto);
        String customerDtoString = objectMapper.writeValueAsString(customerDto);
        mockMvc.perform(post("/api/v1/customer/createCustomer")
                .contentType(MediaType.APPLICATION_JSON).content(customerDtoString))
                .andExpect(status().isCreated());
    }
@Test
    public void updateCustomer() throws Exception{
        String customerDtoString = objectMapper.writeValueAsString(customerDto);
        mockMvc.perform(put("/api/v1/customer/" + customerDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerDtoString))
                .andExpect(status().isNoContent());

        then(customerService).should().updateCustomer(any(),any());
         }
}

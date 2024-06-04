package guru.springframework.msscbrewery.web.controller;

import guru.springframework.msscbrewery.services.CustomerService;
import guru.springframework.msscbrewery.web.model.CustomerDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by jt on 2019-04-21.
 */

@RequestMapping("api/v1/customer")
@RestController
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("customerId")  UUID customerId){

        return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);
    }

    @PostMapping("/createCustomer")
    public ResponseEntity handlePost(@RequestBody CustomerDto customerDto){
        CustomerDto customerDto1=customerService.saveNewCustomer(customerDto);
        HttpHeaders headers=new HttpHeaders();
        headers.add("Location","api/v1/customer/"+customerDto1.getId().toString());
            return new ResponseEntity(headers, HttpStatus.CREATED);
    }

@PutMapping({"/{customerId}"})
    public ResponseEntity updateCustomer(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDto customerDto){
   customerService.updateCustomer(customerId,customerDto);
   return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

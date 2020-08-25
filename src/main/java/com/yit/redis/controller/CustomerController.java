package com.yit.redis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yit.redis.model.Customer;
import com.yit.redis.repository.CustomerService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    public ResponseEntity<?> getCustomers() {
        List<Customer> customers = customerService.retrieveCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping(params = "name")
    public ResponseEntity<?> getCustomers(@RequestParam String name) {
        List<Customer> customers = customerService.retrieveCustomersByName(name);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable String id) {
        Optional<?> customer = customerService.retrieveCustomers(id);
        if(!customer.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/save")
    public ResponseEntity<?> postCustomers(@RequestBody Customer body) {
    	
    	body.setCreateDate(new Date());
        Customer customer = customerService.createCustomer(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putCustomers(@PathVariable String id, @RequestBody Customer body) {
        Optional<?> customer = customerService.updateCustomer(id, body);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomers(@PathVariable String id) {
        if(!customerService.deleteCustomer(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}

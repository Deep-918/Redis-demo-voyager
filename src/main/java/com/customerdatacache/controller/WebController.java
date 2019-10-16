package com.customerdatacache.controller;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.customerdatacache.model.Customer;
import com.customerdatacache.repository.RedisRepository;

@RestController
@RequestMapping("/")
public class WebController {
    
    @Autowired
    private RedisRepository redisRepository;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping(value = "/getAllCustomersFromCache",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Customer>> getAllCustomersFromCache() throws JsonProcessingException {
        System.out.println("Entering getAllCustomersFromCache");
        List<Customer> allCustomers = redisRepository.findAllCustomers();
        return new ResponseEntity<>(allCustomers,HttpStatus.OK);
    }

    @RequestMapping(value = "/addCustomerInCache", method = RequestMethod.POST)
    public ResponseEntity<String> addCustomerInCache(
            @RequestBody Customer customer) {
        System.out.println("Incomming request: "+customer.getFirstName()+" and "+customer.getCustomer_id());
        redisRepository.add(customer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/getOneCustomerFromCache/{customerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getOneCustomerFromCache(
            @PathVariable String customerId) {
        System.out.println("Getting customer record for : "+customerId);
        Customer customerRecord;
        customerRecord = redisRepository.findCustomer(Long.valueOf(customerId));
        return new ResponseEntity(customerRecord, HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteCustomerFromCache/{customerId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCustomerFromCache(@PathVariable Long key) {
        System.out.println("Deleting customer...");
        redisRepository.deleteCustomer(key);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

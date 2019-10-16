package com.customerdatacache.repository;

import java.util.List;
import java.util.Map;

import com.customerdatacache.model.Customer;

public interface RedisRepository {

    /**
     * Return all customer
     */
    List<Customer> findAllCustomers();

    /**
     * Add key-value pair to Redis.
     */
    Customer add(Customer customer);

    /**
     * Delete a key-value pair in Redis.
     */
    void deleteCustomer(Long id);
    
    /**
     * find a customer
     */
    Customer findCustomer(Long id);
    
}

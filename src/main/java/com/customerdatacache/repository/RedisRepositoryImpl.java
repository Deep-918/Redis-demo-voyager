package com.customerdatacache.repository;

import com.customerdatacache.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;

@Repository
public class RedisRepositoryImpl implements RedisRepository {
    private static final String KEY = "Customer";
    
    private RedisTemplate<String, Customer> redisTemplate;
    private HashOperations<String, Long, Customer> hashOperations;
    
    @Autowired
    public RedisRepositoryImpl(RedisTemplate<String, Customer> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }
    
    public Customer add(final Customer customer) {
        hashOperations.put(KEY, customer.getCustomer_id(), customer);
        return customer;
    }

    public void deleteCustomer(final Long id) {
        hashOperations.delete(KEY, id);
    }
    
    public Customer findCustomer(final Long id){

        Customer customer = (Customer) hashOperations.get(KEY, id);
        return customer;
    }
    
    public List<Customer> findAllCustomers(){
        Map entries = hashOperations.entries(KEY);
        return new ArrayList<Customer>(entries.values());

    }

  
}

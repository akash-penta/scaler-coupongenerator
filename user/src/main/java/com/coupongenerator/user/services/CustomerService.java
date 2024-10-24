package com.coupongenerator.user.services;

import com.coupongenerator.user.entities.Customer;
import com.coupongenerator.user.entities.User;
import com.coupongenerator.user.exceptions.CustomerNotFoundException;
import com.coupongenerator.user.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer getCustomer(
            String name,
            String phoneNo,
            String email
    ) {
        Optional<Customer> optionalCustomer = customerRepository.findByPhoneNo(phoneNo);

        Date currentDate = new Date();

        Customer customer = null;
        if(optionalCustomer.isEmpty()) {
            customer = new Customer();

            if(name != null) {
                customer.setName(name);
            }
            customer.setPhoneNo(phoneNo);
            if(email != null) {
                customer.setEmail(email);
            }
            customer.setCreatedAt(currentDate);
            customer.setModifiedAt(currentDate);

            customerRepository.save(customer);
        }
        else {
            customer = optionalCustomer.get();

            if(name != null) {
                customer.setName(name);
            }
            if(email != null) {
                customer.setEmail(email);
            }
            customer.setModifiedAt(currentDate);

            customerRepository.save(customer);
        }

        return customer;
    }

    public Customer getCustomer(String phoneNo) throws CustomerNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findByPhoneNo(phoneNo);

        if(optionalCustomer.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found with phone no.:" + phoneNo);
        }

        return optionalCustomer.get();
    }
}

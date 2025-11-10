package com.quantafic.JWTSecurity.Service;

import com.quantafic.JWTSecurity.Model.CustomerPrincipal;
import com.quantafic.JWTSecurity.Model.Customers;
import com.quantafic.JWTSecurity.Repo.Customer_profileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements UserDetailsService {
    @Autowired
    private Customer_profileRepository customerProfileRepository;

    @Override
    public UserDetails loadUserByUsername(String customerId) throws UsernameNotFoundException {
        Customers customer = getCustomerEntity(customerId);
        return new CustomerPrincipal(customer);
    }

    //Get Customer entity by ID
    public Customers getCustomerEntity(String customerId) {
        return customerProfileRepository.findById(customerId)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));
    }

    //Save Customer entity
    public Customers saveCustomer(Customers customer) {
        return customerProfileRepository.save(customer);
    }

}

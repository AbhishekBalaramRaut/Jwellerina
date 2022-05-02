package com.jwellerina.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jwellerina.documents.Customer;
import com.jwellerina.repositories.CustomerRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	CustomerRepository consumerRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Customer c = consumerRepository.findOneByUsername(username);
		if(c == null) {
			throw new UsernameNotFoundException("User not found");
		} else {
			return new User(username,c.getPassword(),new ArrayList<>());
		}
	}

}

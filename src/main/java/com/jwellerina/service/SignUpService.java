package com.jwellerina.service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jwellerina.documents.Customer;
import com.jwellerina.model.CustomerDto;
import com.jwellerina.model.JwtResponse;
import com.jwellerina.repositories.CustomerRepository;
import com.jwellerina.utils.ErrorCodeConstants;
import com.jwellerina.utils.GeneralConstants;

@Service
public class SignUpService implements SignUpServiceIntf {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private MongoTemplate mt;

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	/**
	 * Sign up method
	 */
	@Override
	public Map<String, Object> singUp(CustomerDto customerDto) {
		Customer c = modelMapper.map(customerDto, Customer.class);
		Map<String, Object> result = new HashMap<>();
		String code = checkDuplicateEmailOrMobile(customerDto);
		if (code != null) {
			result.put(GeneralConstants.CODE, code);
			return result;
		}
		c.setId(generateCustomerId());
		try {
			c = this.customerRepository.save(c);
			authenticate(c.getUsername(), c.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
			result.put(GeneralConstants.CODE, ErrorCodeConstants.SIGN_UP_FAILED);
			return result;
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(c.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		result.put(GeneralConstants.CODE, GeneralConstants.SUCCESS_CODE);
		result.put(GeneralConstants.DATA, new JwtResponse(token));

		return result;
	}

	private String checkDuplicateEmailOrMobile(CustomerDto customerDto) {
		String email = customerDto.getEmail();
		Customer c = null;
//		if(StringUtils.isEmpty(email)) {
//			return ErrorCodeConstants.EMAIL_IS_REQUIRED;
//		}
		if (!StringUtils.isEmpty(email)) {
			if (validateEmail(email) != true) {
				return ErrorCodeConstants.INVALID_EMAIL;
			}
			c = customerRepository.findOneByEmail(customerDto.getEmail());
			if (c != null) {
				return ErrorCodeConstants.DUPLICATE_EMAIL;
			}
		}

		String mobile = customerDto.getMobile();
		String strPattern = "^[0-9]{10}$";
		if (StringUtils.isEmpty(mobile)) {
			return ErrorCodeConstants.MOBILE_IS_REQUIRED;
		}
		if (mobile.matches(strPattern) != true) {
			return ErrorCodeConstants.INVALID_MOBILE;
		}
		String username = customerDto.getUsername();
		if (StringUtils.isEmpty(username)) {
			return ErrorCodeConstants.INVALID_USERNAME;
		}
		if (!(username.length() > 6 && username.length() < 15)) {
			return ErrorCodeConstants.INVALID_USERNAME;
		}

		c = customerRepository.findOneByMobile(customerDto.getMobile());
		if (c != null) {
			return ErrorCodeConstants.DUPLICATE_MOBILE;
		}
		c = customerRepository.findOneByUsername(customerDto.getUsername());
		if (c != null) {
			return ErrorCodeConstants.DUPLICATE_USERNAME;
		}
		return null;
	}

	public boolean validateEmail(String email) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.matches();
	}

	private Long generateCustomerId() {
		Query query = new Query();
		query.with(Sort.by(Sort.Direction.DESC, "_id"));
		query.limit(1);
		Customer maxEntity = mt.findOne(query, Customer.class);
		if (maxEntity == null) {
			return 1L;
		} else {
			return (maxEntity.getId() + 1);
		}
	}

	/**
	 * Login method
	 */
	@Override
	public Map<String, Object> signIn(CustomerDto customerDto) {
		Map<String, Object> result = new HashMap<>();
		try {
			authenticate(customerDto.getUsername(), customerDto.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
			result.put(GeneralConstants.CODE, ErrorCodeConstants.WRONG_USER_PWD);
			return result;
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(customerDto.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		result.put(GeneralConstants.CODE, GeneralConstants.SUCCESS_CODE);
		result.put(GeneralConstants.DATA, new JwtResponse(token));

		return result;
	}

	/**
	 * Common method to authenticate
	 * 
	 * @param username
	 * @param password
	 * @throws Exception
	 */
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}

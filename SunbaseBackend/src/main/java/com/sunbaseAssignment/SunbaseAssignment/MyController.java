package com.sunbaseAssignment.SunbaseAssignment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
public class MyController {

    private String mytoken = null;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;





    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticateUser(@RequestBody AuthenticationRequest request, HttpServletRequest httpServletRequest) throws JsonProcessingException {


        // Setting up headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Converting the request body to JSON manually
        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(request);
        } catch (Exception e) {
            return new ResponseEntity<>("Error converting request to JSON", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Setting up the request body
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);

        // Making the request to the authentication API
        String authenticationApiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                authenticationApiUrl,
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        // Checking the response status and return the result
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            // Authentication successful
            String token = responseEntity.getBody();

//            converting token string to json
            TokenModel tokenModel = new ObjectMapper().readValue(token, TokenModel.class);

            // initialising token
            mytoken = "Bearer "+tokenModel.getAccess_token();


            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            // Authentication failure
            return new ResponseEntity<>(responseEntity.getBody(), responseEntity.getStatusCode());
        }
    }


    @PostMapping("/create-customer")
    public ResponseEntity<String> createCustomer(@RequestBody Customer customerRequest,HttpServletRequest request) {
        // Setting up headers with the provided Bearer token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", mytoken);

        // Converting the request body to JSON manually
        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(customerRequest);
        } catch (Exception e) {
            return new ResponseEntity<>("Error converting request to JSON", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Setting up the request body
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);

        // Making the request to create a customer using the provided Bearer token
        String createCustomerApiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=create";
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                createCustomerApiUrl,
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        // Check the response status and return the result
        if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
            // Customer creation successful
            return new ResponseEntity<>(responseEntity.getBody(), HttpStatus.CREATED);
        } else {
            // Customer creation failure
            return new ResponseEntity<>(responseEntity.getBody(), responseEntity.getStatusCode());
        }
    }


    @GetMapping("/get-customer-list")
    public ResponseEntity<List<Customer>> getCustomerList() {

        // Setting up headers with the provided Bearer token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", mytoken);

        // Setting up the request entity
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        // Making the request to get the list of customers using the provided Bearer token
        String getCustomerListApiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";
        ResponseEntity<List<Customer>> responseEntity = restTemplate.exchange(
                getCustomerListApiUrl,
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<List<Customer>>() {
                }
        );

        // Checking the response status and return the result
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            // Customer list retrieval successful
            return new ResponseEntity<>(responseEntity.getBody(), HttpStatus.OK);
        } else {
            // Customer list retrieval failure
            return new ResponseEntity<>(responseEntity.getStatusCode());
        }
    }
    @PostMapping("/delete-customer")
    public ResponseEntity<String> deleteCustomer(@RequestParam String uuid) {
        // Setting up headers with the stored Bearer token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", mytoken);


        // Setting up the request entity
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        // Making the request to delete a customer using the provided Bearer token
        String deleteCustomerApiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=delete";
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                deleteCustomerApiUrl +"&uuid="+uuid,
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        // Checking the response status and return the result
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            // Customer deletion successful
            return new ResponseEntity<>(responseEntity.getBody(), HttpStatus.OK);
        } else {
            // Customer deletion failure
            return new ResponseEntity<>(responseEntity.getBody(), responseEntity.getStatusCode());
        }
    }

    @PostMapping("/update-customer")
    public ResponseEntity<String> updateCustomer(@RequestParam String uuid, @RequestBody Customer updatedCustomer) {
        // Set up headers with the stored Bearer token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", mytoken);


        // Converting the request body to JSON manually
        String jsonRequestBody;
        try {
            jsonRequestBody = objectMapper.writeValueAsString(updatedCustomer);
        } catch (Exception e) {
            return new ResponseEntity<>("Error converting request to JSON", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Setting up the request entity
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonRequestBody, headers);

        // Making the request to update a customer using the provided Bearer token
        String updateCustomerApiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=update";
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                updateCustomerApiUrl +"&uuid="+uuid,
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        // Checking the response status and return the result
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            // Customer update successful
            return new ResponseEntity<>(responseEntity.getBody(), HttpStatus.OK);
        } else {
            // Customer update failure
            return new ResponseEntity<>(responseEntity.getBody(), responseEntity.getStatusCode());
        }
    }



}



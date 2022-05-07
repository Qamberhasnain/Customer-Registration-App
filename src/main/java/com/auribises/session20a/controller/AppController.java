package com.auribises.session20a.controller;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.auribises.session20a.model.Customer;
import com.auribises.session20a.repository.CustomerRepository;

@Controller
@RequestMapping(path="/customer-api")
public class AppController {
	
	@Autowired
	CustomerRepository customerRepository;
	
	@PostMapping(path="/add")
	public ResponseEntity<Customer> addCustomer(@RequestParam String name, @RequestParam String phone, @RequestParam String email){
		
		System.out.println("[AppController]: Add Customer Started");
		
		try {
			Customer customer = new Customer(null, name, phone, email);
			customerRepository.save(customer);
			System.out.println("[AppController]: Customer Added");
			// JSON Response for the Customer Creation :)
			return new ResponseEntity<Customer>(customer, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println("[AppController]: Customer Failed to Add: "+e);
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/login")
	public String loginUser(@RequestParam String email, @RequestParam String password, Model model) {
		
		System.out.println("User Details for Login: "+email+" "+password);
		String template = "";
		
		if(email.equals("admin@example.com") && password.equals("password123")) {
			model.addAttribute("title", email);
			model.addAttribute("message", "Thank You for Login!  Its:"+new Date());
			template = "success";
		}else {
			model.addAttribute("message", "Invalid Credentials. Please Try Again");
			template = "error";
		}
		
		
		return template;
	}
	@GetMapping(path="/customers")
	public String getAllCustomer(Model model){
		try {
			ArrayList<Customer> customers = new ArrayList<Customer>();
			customerRepository.findAll().forEach((customer)->customers.add(customer));
			model.addAttribute("message",new ResponseEntity<ArrayList<Customer>>(customers, HttpStatus.OK));
			return "success";
		} catch (Exception e) {
			System.err.println("[AppController]: Customer Failed to Add: "+e);
			e.printStackTrace();
			return "error";
		}
	}
	
	/*@GetMapping(path="/update/{id}")
	public ResponseEntity<Customer> updateCustomer(
			@PathVariable("id") Integer id,
			@RequestParam String name, @RequestParam String phone, @RequestParam String email){
		
		System.out.println("[AppController]: Add Customer Started");
		
		try {
			Customer customer = new Customer(id, name, phone, email);
			customerRepository.save(customer); // same method to perform update and insert :)
			System.out.println("[AppController]: Customer Added");
			// JSON Response for the Customer Creation :)
			return new ResponseEntity<Customer>(customer, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println("[AppController]: Customer Failed to Add: "+e);
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}*/
	
	@GetMapping(path="/update/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Integer id){
		
		System.out.println("[AppController]: Add Customer Started");
		
		try {
			Customer customer = new Customer(id, "John Jackson", "+91 99999 12345", "john.jackson@example.com");
			customerRepository.save(customer); // same method to perform update and insert :)
			System.out.println("[AppController]: Customer Updated");
			// JSON Response for the Customer Creation :)
			return new ResponseEntity<Customer>(customer, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println("[AppController]: Customer Failed to Update: "+e);
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path="/delete/{id}")
	public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") Integer id){
		
		System.out.println("[AppController]: Add Customer Started");
		
		try {
			Customer customer = new Customer();
			customer.setId(id);
			
			// Get the Customer from the Database to be deleted :)
			Customer cRef = customerRepository.findById(id).get();
			
			customerRepository.delete(cRef);
			
			System.out.println("[AppController]: Customer Deleted");
			// JSON Response for the Customer Creation :)
			return new ResponseEntity<Customer>(cRef, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println("[AppController]: Customer Failed to Update: "+e);
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}

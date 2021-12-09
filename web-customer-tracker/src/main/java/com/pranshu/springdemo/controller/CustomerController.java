package com.pranshu.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pranshu.springdemo.entity.Customer;
import com.pranshu.springdemo.service.CustomerService;
import com.pranshu.springdemo.util.SortUtil;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	// need to inject the Customer Service
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/list")
	public String listCustomers(@RequestParam(required = false) String sort,Model theModel) {
	
		List<Customer> theCustomers = null;
		int theSortField = SortUtil.LAST_NAME;
		
		// TODO: if sorting is given
		if(sort != null) {
			theSortField = Integer.parseInt(sort);
		}
		// get the customers using the sort type field
		theCustomers = customerService.getCustomers(theSortField);
		
		// add the customers to the model
		theModel.addAttribute("customers", theCustomers);
		
		return "list-customers";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		
		Customer theCustomer = new Customer();
		
		theModel.addAttribute("customer",theCustomer);
		
		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer (@ModelAttribute("customer") Customer theCustomer) {
		
		System.out.println("Customer To Save : "  + theCustomer);
		
		// save the customer using our service
		
		customerService.saveCustomer(theCustomer);
		
		return "redirect:/customer/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId,
									Model theModel) {
		// get the customer from the service
		Customer theCustomer = customerService.getCustomer(theId);
		// set customer as a model attribute to pre-populate the form
		theModel.addAttribute("customer", theCustomer);
		// send over to our form
		
		
		return "customer-form";
	}
	
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int theId) {
		
		// delete the customer 
		
		customerService.deleteCustomer(theId);
		
		return "redirect:/customer/list";
	}
	
	@GetMapping("/search")
	public String searchCustomer(@RequestParam("theSearchName")String theSearchName,
									Model theModel) {
		
		// search customer using the service
		List<Customer> theCustomers = customerService.serachCustomers(theSearchName);
		// add the list of customers to our model
		theModel.addAttribute("customers",theCustomers);
		// return the list-customer.jsp page
		return "list-customers";
	}
	
	
}

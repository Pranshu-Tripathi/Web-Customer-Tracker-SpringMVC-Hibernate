package com.pranshu.springdemo.dao;

import java.util.List;

import com.pranshu.springdemo.entity.Customer;

public interface CustomerDAO {
	
	public List <Customer> getCustomers(int theSortField);

	public void saveCustomer(Customer theCustomer);

	public Customer getCustomer(int theId);

	public Object deleteCustomer(int theId);

	public List<Customer> searchCustomers(String theSearchName);
	
}

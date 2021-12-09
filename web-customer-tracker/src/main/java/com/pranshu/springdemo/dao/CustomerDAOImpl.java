package com.pranshu.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pranshu.springdemo.entity.Customer;
import com.pranshu.springdemo.util.SortUtil;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	@Override
	public List<Customer> getCustomers(int theSortField) {
		
		// get the current hibernate session
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		// create a query 
		
		// update : sort by last name using HQL
		
		String sortBy = "lastName";
		if(theSortField == SortUtil.FIRST_NAME) {
			sortBy = "firstName";
		}
		else if(theSortField == SortUtil.LAST_NAME) {
			sortBy = "lastName";
		}
		else if(theSortField == SortUtil.EMAIL) {
			sortBy = "email";
		}
		Query<Customer> theQuery = 
				currentSession.createQuery("from Customer order by "+sortBy,
											Customer.class);
		
		// get the result list - execute query
		
		List<Customer> customers = theQuery.getResultList();
 		 
		// return the result
		
		return customers;
	}

	public CustomerDAOImpl() {
		
	}

	@Override
	public void saveCustomer(Customer theCustomer) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		// save the customer if not exist else update it
		currentSession.saveOrUpdate(theCustomer);
	}

	@Override
	public Customer getCustomer(int theId) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		// get the customer from the database using the Id
		Customer theCustomer = currentSession.get(Customer.class, theId);
		// return the retrieved customer
		
		return theCustomer;
	}

	@Override
	public Object deleteCustomer(int theId) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// delete the customer from the database
		Query<?> theQuery =
				currentSession.createQuery("delete from Customer where id=:customerId");
		
		theQuery.setParameter("customerId",theId);
		
		theQuery.executeUpdate();
		return null;
	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		// create the appropriate query
		
		Query<Customer> theQuery = null;
		
		if(theSearchName != null && theSearchName.trim().length() > 0) {
			// requested name is valid and should be processed
			theQuery = currentSession.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName",Customer.class);
			theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%" );
		}
		else {
			// requested name is not valid
			
			// will return the complete list
			
			theQuery = currentSession.createQuery("from Customer",Customer.class);
		}
		// execute the query and get the list
		List<Customer> theCustomers = theQuery.getResultList();
		// return the list
		return theCustomers;
	}
		
}

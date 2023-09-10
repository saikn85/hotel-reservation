package services.customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import common.EmailValidator;
import models.customer.Customer;

public class CustomerService {
    private static final CustomerService SINGLETON = new CustomerService();
    private final Map<String, Customer> customers = new HashMap<>();

    private CustomerService() {
    }

    public static CustomerService getCustomerService() {
        return SINGLETON;
    }

    public void addCustomer(final String email, final String firstName, final String lastName) {
        if (EmailValidator.isValidEmail(email))
            customers.put(email, new Customer(firstName, lastName, email));

        throw new IllegalArgumentException("Invalid Email Address Pattern");
    }

    public Customer getCustomer(final String customerEmail) {
        return customers.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}

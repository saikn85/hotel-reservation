package services.customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import common.EmailValidator;
import models.customer.Customer;

public class CustomerService {
    //region Static Setup
    private static final CustomerService _SINGLETON = new CustomerService();
    private final Map<String, Customer> _customers = new HashMap<>();
    //endregion

    //region Constructor
    private CustomerService() {
    }

    public static CustomerService getCustomerService() {
        return _SINGLETON;
    }
    //endregion

    //region Public Methods
    public void addCustomer(final String email, final String firstName, final String lastName) {
        if (EmailValidator.isValidEmail(email)) {
            _customers.put(email, new Customer(firstName, lastName, email));
            return;
        }

        throw new IllegalArgumentException("Invalid Email Address Pattern");
    }

    public Customer getCustomer(final String customerEmail) {
        return _customers.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers() {
        return _customers.values();
    }
    //endregion
}

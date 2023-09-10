package models.customer;

public class Customer {

    private final String firstName;
    private final String lastName;
    private final String email;

    public Customer(final String firstName, final String lastName, final String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return "First Name: " + this.firstName
                + " Last Name: " + this.lastName
                + " Email: " + this.email;
    }
}

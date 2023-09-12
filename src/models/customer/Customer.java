package models.customer;

import java.util.Objects;

public class Customer {
    //region Static Setup
    private static int _maxFirstNameLength = 10;
    private static int _maxLastNameLength = 9;
    private static int _maxEmailLength = 0;
    private static int _maxCustomerLength = 0;
    private static String _customerFormat = "%" + Customer.getMaxFirstNameLength() + "s | %"
            + Customer.getMaxLastNameLength() + "s | %"
            + Customer.getMaxEmailLength() + "s";
    //endregion

    //region Instance Variables
    private final String _firstName;
    private final String _lastName;
    private final String _email;
    //endregion

    //region Constructor
    public Customer(final String firstName, final String lastName, final String email) {
        super();

        this._firstName = firstName;
        this._lastName = lastName;
        this._email = email;

        this.computeLengths();
    }
    //endregion

    //region Getters - Static Variables
    public static int getMaxFirstNameLength() {
        return Customer._maxFirstNameLength;
    }
    public static int getMaxLastNameLength() {
        return Customer._maxLastNameLength;
    }
    public static int getMaxEmailLength() {
        return Customer._maxEmailLength;
    }
    public static int getMaxCustomerLength() {
        return Customer._maxCustomerLength;
    }
    public static String getCustomerFormat() {
        return Customer._customerFormat;
    }
    //endregion

    //region Getters
    public final String getEmail() {
        return _email;
    }
    //endregion

    //region Overrides
    @Override
    public String toString() {
        return String.format(Customer.getCustomerFormat(), _firstName, _lastName, _email);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Customer customer)) return false;
        return Objects.equals(_firstName, customer._firstName) &&
                Objects.equals(_lastName, customer._lastName) &&
                Objects.equals(_email, customer._email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_firstName, _lastName, _email);
    }
    //endregion

    //region Private Helpers
    private void computeLengths() {

        if (this._firstName.length() > Customer._maxFirstNameLength)
            Customer._maxFirstNameLength = this._firstName.length();

        if (this._lastName.length() > Customer._maxLastNameLength)
            Customer._maxLastNameLength = this._lastName.length();

        if (this._email.length() > Customer._maxEmailLength)
            Customer._maxEmailLength = this._email.length();

        _customerFormat = "%" + Customer.getMaxFirstNameLength() + "s | %"
                + Customer.getMaxLastNameLength() + "s | %"
                + Customer.getMaxEmailLength() + "s";

        int sum = Customer._maxFirstNameLength + Customer._maxLastNameLength + Customer._maxEmailLength;
        if (sum > Customer._maxCustomerLength) {
            Customer._maxCustomerLength = sum + 6;
        }
    }
    //endregion
}
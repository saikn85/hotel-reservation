package models.customer;

public class Customer {

    private static int _maxFirstNameLength = 10;
    private static int _maxLastNameLength = 9;
    private static int _maxEmailLength = 0;
    private static int _maxCustomerLength = 0;
    private static String _customerFormat = "%" + Customer.getMaxFirstNameLength() + "s | %"
            + Customer.getMaxLastNameLength() + "s | %"
            + Customer.getMaxEmailLength() + "s";

    private final String _firstName;
    private final String _lastName;
    private final String _email;

    public Customer(final String firstName, final String lastName, final String email) {
        super();

        this._firstName = firstName;
        this._lastName = lastName;
        this._email = email;

        this.computeLengths();
    }

    public final String getEmail() {
        return this._email;
    }

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

    @Override
    public String toString() {
        return String.format(Customer.getCustomerFormat(), this._firstName, this._lastName, this._email);
    }

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
}
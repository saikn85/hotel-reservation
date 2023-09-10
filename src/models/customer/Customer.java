package models.customer;

public class Customer {

    private static int _maxFirstNameLength = 10;
    private static int _maxLastNameLength = 9;
    private static int _maxEmailLength = 0;
    private static int _maxCustomerLenght = 0;

    private final String _firstName;
    private final String _lastName;
    private final String _email;

    public Customer(final String firstName, final String lastName, final String email) {
        super();

        this._firstName = firstName;
        this._lastName = lastName;
        this._email = email;

        this.computeLenghts();
    }

    // #region Getters

    public String getEmail() {
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

    public static int getMaxCustomerLenght() {
        return Customer._maxCustomerLenght;
    }

    // #endregion

    // #region Override(s)

    @Override
    public String toString() {
        String format = "%" + Customer.getMaxFirstNameLength() + "s | %" + Customer.getMaxLastNameLength() + "s | %"
                + Customer.getMaxEmailLength() + "s";
        return String.format(format, _firstName, _lastName, _email);
    }

    // #endregion

    // #region Private Helpers

    private void computeLenghts() {

        if (this._firstName.length() > Customer._maxFirstNameLength)
            Customer._maxFirstNameLength = this._firstName.length();

        if (this._lastName.length() > Customer._maxLastNameLength)
            Customer._maxLastNameLength = this._lastName.length();

        if (this._email.length() > Customer._maxEmailLength)
            Customer._maxEmailLength = this._email.length();

        Customer._maxCustomerLenght = this.toString().length();
    }

    // #endregion
}

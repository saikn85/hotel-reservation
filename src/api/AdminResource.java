package api;

import java.util.Collection;
import models.customer.Customer;
import models.room.FreeRoom;
import models.room.IRoom;
import models.room.Room;
import models.room.enums.RoomType;
import services.customer.CustomerService;
import services.reservation.ReservationService;

public class AdminResource {
    private static final AdminResource SINGLETON = new AdminResource();
    private final CustomerService _customerService = CustomerService.getCustomerService();
    private final ReservationService _reservationService = ReservationService.getReservationService();

    private AdminResource() {
    }

    public static AdminResource getAdminResource() {
        return SINGLETON;
    }

    public Customer getCustomer(String email) {
        return _customerService.getCustomer(email);
    }

    public void addRoom(IRoom room) {
        _reservationService.addRoom(room);
    }

    public Collection<IRoom> getAllRooms() {
        return _reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers() {
        return _customerService.getAllCustomers();
    }

    public void displayAllReservations() {
        _reservationService.printAllReservation();
    }

    public void populateApplicationData() {
        _customerService.addCustomer("madmax@domain.com", "Mad", "Max");
        _customerService.addCustomer("tonystark@domain.com", "Tony", "Stark");
        _customerService.addCustomer("narutoUzumaki@domain.com", "Naruto", "Uzumaki");
        _customerService.addCustomer("orochimaru@hell.com", "Orochimaru", "");
        _customerService.addCustomer("tsunadeSenju@domain.com", "Tsuande", "Senju");

        _reservationService.addRoom(new Room("101", 150.0d, RoomType.DOUBLE));
        _reservationService.addRoom(new Room("102", 75.0d, RoomType.SINGLE));
        _reservationService.addRoom(new Room("103", 200.0d, RoomType.DOUBLE));
        _reservationService.addRoom(new FreeRoom("104", RoomType.SINGLE));

        _reservationService.addRoom(new Room("200", 175.0d, RoomType.SINGLE));
        _reservationService.addRoom(new Room("210", 350.0d, RoomType.DOUBLE));
        _reservationService.addRoom(new FreeRoom("215", RoomType.DOUBLE));
        _reservationService.addRoom(new Room("265", 265.0d, RoomType.DOUBLE));
    }
}

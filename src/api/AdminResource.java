package api;

import java.util.Collection;
import java.util.List;

import models.customer.Customer;
import models.room.IRoom;
import services.customer.CustomerService;
import services.reservation.ReservationService;

public class AdminResource {
    private static final AdminResource SINGLETON = new AdminResource();
    private final CustomerService customerService = CustomerService.getCustomerService();
    private final ReservationService reservationService = ReservationService.getReservationService();

    private AdminResource() {
    }

    public static AdminResource getAdminResource() {
        return SINGLETON;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void addRoom(List<IRoom> rooms) {
        rooms.forEach(reservationService::addRoom);
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void displayAllReservations() {
        reservationService.printAllReservation();
    }
}

package api;

import java.util.Collection;

import models.customer.Customer;
import models.reservation.Reservation;
import models.room.FreeRoom;
import models.room.IRoom;
import models.room.Room;
import models.room.enums.RoomType;
import services.customer.CustomerService;
import services.reservation.ReservationService;
import services.room.RoomService;

public class AdminResource {
    //region Static Setup
    private static final AdminResource SINGLETON = new AdminResource();
    private final CustomerService _customerService = CustomerService.getCustomerService();
    private final RoomService _roomService = RoomService.getRoomService();
    private final ReservationService _reservationService = ReservationService.getReservationService();
    //endregion

    //region Constructor
    private AdminResource() {
    }

    public static AdminResource getAdminResource() {
        return SINGLETON;
    }
    //endregion

    //region Public Methods
    public Customer getCustomer(String email) {
        return _customerService.getCustomer(email);
    }

    public void addRoom(IRoom room) {
        _roomService.addRoom(room);
    }

    public Collection<IRoom> getAllRooms() {
        return _roomService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers() {
        return _customerService.getAllCustomers();
    }

    public Collection<Reservation> getAllReservations() {
        return _reservationService.getAllReservations();
    }

    public void populateApplicationData() {
        _customerService.addCustomer("madmax@domain.com", "Mad", "Max");
        _customerService.addCustomer("tonystark@domain.com", "Tony", "Stark");
        _customerService.addCustomer("narutoUzumaki@domain.com", "Naruto", "Uzumaki");
        _customerService.addCustomer("orochimaru@hell.com", "Orochimaru", "");
        _customerService.addCustomer("tsunadeSenju@domain.com", "Tsuande", "Senju");

        _roomService.addRoom(new Room("101", 150.0d, RoomType.DOUBLE));
        _roomService.addRoom(new Room("102", 75.0d, RoomType.SINGLE));
        _roomService.addRoom(new Room("103", 200.0d, RoomType.DOUBLE));
        _roomService.addRoom(new FreeRoom("104", RoomType.SINGLE));

        _roomService.addRoom(new Room("200", 175.0d, RoomType.SINGLE));
        _roomService.addRoom(new Room("210", 350.0d, RoomType.DOUBLE));
        _roomService.addRoom(new FreeRoom("215", RoomType.DOUBLE));
        _roomService.addRoom(new Room("265", 265.0d, RoomType.DOUBLE));
    }
    //endregion
}
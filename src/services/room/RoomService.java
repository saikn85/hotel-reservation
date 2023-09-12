package services.room;

import models.room.IRoom;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RoomService {
    //region Static Setup
    private static final RoomService _SINGLETON = new RoomService();
    private final Map<String, IRoom> rooms = new HashMap<>();
    //endregion

    //region Constructor
    private RoomService() {
    }

    public static RoomService getRoomService() {
        return _SINGLETON;
    }
    //endregion

    //region Public Methods
    public void addRoom(final IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(final String roomNumber) {
        return rooms.get(roomNumber);
    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }
    //endregion
}

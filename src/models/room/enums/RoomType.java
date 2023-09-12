package models.room.enums;

public enum RoomType {
    SINGLE("1"),
    DOUBLE("2");

    public final String label;

    private RoomType(String label) {
        this.label = label;
    }

    // This was picked after understanding on how to assign custom values
    // to enums from this source https://www.baeldung.com/java-enum-values
    public static RoomType getRoomTypeLabel(String label) {
        for (RoomType roomType : values()) {
            if (roomType.label.equals(label)) {
                return roomType;
            }
        }
        throw new IllegalArgumentException("Invalid Room Type");
    }
}
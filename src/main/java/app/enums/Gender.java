package app.enums;

public enum Gender {
    NON_BINARY(0, "Non-Binary"),
    FEMALE(1, "Female"),
    MALE(2, "Male");


    private final int id;
    private final String description;

    Gender(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public static String getDescriptionById(int id) {
        for (Gender gender : values()) {
            if (gender.id == id) {
                return gender.description;
            }
        }
        return NON_BINARY.description;
    }
}
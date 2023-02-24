package ma.emsi.solvefinity.model.emun;

public enum VerificationStatus {
    VERIFIED(1, "Verified"),
    SOURCE_VERIFIED(2, "Source Verified"),
    NOT_VERIFIED(3, "Not Verified");

    private final int id;
    private final String name;

    VerificationStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static VerificationStatus getById(int id) {
        for (VerificationStatus e : values()) {
            if (e.id == id) return e;
        }
        return null;
    }

    public static VerificationStatus getByName(String name) {
        for (VerificationStatus e : values()) {
            if (e.name.equals(name)) return e;
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

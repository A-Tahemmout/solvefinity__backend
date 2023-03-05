package ma.emsi.solvefinity.model.emun;

public enum LoanStatus {
    PENDING(1, "Pending"),
    APPROVED(2, "Approved"),
    REJECTED(3, "Rejected"),
    CANCELED(4, "Canceled"),
    PAID(5, "Paid"),
    UNKNOWN(0, "Unknown");

    private final int id;
    private final String name;

    LoanStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static LoanStatus getById(int id) {
        for (LoanStatus e : values()) {
            if (e.id == id) return e;
        }
        return null;
    }

    public static LoanStatus getByName(String name) {
        for (LoanStatus e : values()) {
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

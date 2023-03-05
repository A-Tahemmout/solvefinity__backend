package ma.emsi.solvefinity.model.emun;

public enum LoanPurpose {
    CAR(0, "Car"),
    CREDIT_CARD(1, "Credit Card"),
    DEBT_CONSOLIDATION(2, "Debt Consolidation"),
    EDUCATIONAL(3, "Educational"),
    HOME_IMPROVEMENT(4, "Home Improvement"),
    HOUSE(5, "House"),
    MAJOR_PURCHASE(6, "Major Purchase"),
    MEDICAL(7, "Medical"),
    MOVING(8, "Moving"),
    OTHER(9, "Other"),
    RENEWABLE_ENERGY(10, "Renewable Energy"),
    SMALL_BUSINESS(11, "Small Business"),
    VACATION(12, "Vacation"),
    WEDDING(13, "Wedding");

    private final int id;
    private final String name;

    LoanPurpose(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static LoanPurpose getById(int id) {
        for (LoanPurpose e : values()) {
            if (e.id == id) return e;
        }
        return null;
    }

    public static LoanPurpose getByName(String name) {
        for (LoanPurpose e : values()) {
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

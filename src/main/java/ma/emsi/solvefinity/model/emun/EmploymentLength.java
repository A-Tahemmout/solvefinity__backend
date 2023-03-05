package ma.emsi.solvefinity.model.emun;

public enum EmploymentLength {
    LESS_THAN_ONE_YEAR(1, "< 1 year"),
    ONE_YEAR(2, "1 year"),
    TWO_YEARS(3, "2 years"),
    THREE_YEARS(4, "3 years"),
    FOUR_YEARS(5, "4 years"),
    FIVE_YEARS(6, "5 years"),
    SIX_YEARS(7, "6 years"),
    SEVEN_YEARS(8, "7 years"),
    EIGHT_YEARS(9, "8 years"),
    NINE_YEARS(10, "9 years"),
    TEN_YEARS(11, "10 years"),
    MORE_THAN_TEN_YEARS(12, "> 10 years"),
    UNKNOWN(0, "Unknown");

    private final int id;
    private final String name;

    EmploymentLength(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static EmploymentLength getById(int id) {
        for (EmploymentLength e : values()) {
            if (e.id == id) return e;
        }
        return null;
    }

    public static EmploymentLength getByName(String name) {
        for (EmploymentLength e : values()) {
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

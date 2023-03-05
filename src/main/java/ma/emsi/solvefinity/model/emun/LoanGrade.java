package ma.emsi.solvefinity.model.emun;

public enum LoanGrade {
    E(1, "E"),
    D(2, "D"),
    C(3, "C"),
    B(4, "B"),
    A(5, "A"),
    UNKNOWN(0, "Unknown");

    private final int id;
    private final String name;

    LoanGrade(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static LoanGrade getById(int id) {
        for (LoanGrade e : values()) {
            if (e.id == id) return e;
        }
        return null;
    }

    public static LoanGrade getByName(String name) {
        for (LoanGrade e : values()) {
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

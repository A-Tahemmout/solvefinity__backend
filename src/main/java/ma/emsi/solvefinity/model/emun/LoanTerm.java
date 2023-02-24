package ma.emsi.solvefinity.model.emun;

public enum LoanTerm {
    THIRTY_SIX(1, "36 months"),
    SIXTY(2, "60 months");

    private final int id;
    private final String name;

    LoanTerm(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static LoanTerm getById(int id) {
        for (LoanTerm e : values()) {
            if (e.id == id) return e;
        }
        return null;
    }

    public static LoanTerm getByName(String name) {
        for (LoanTerm e : values()) {
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

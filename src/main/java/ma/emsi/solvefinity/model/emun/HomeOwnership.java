package ma.emsi.solvefinity.model.emun;

public enum HomeOwnership {
    RENT(1, "Rent"),
    MORTGAGE(2, "Mortgage"),
    OWN(3, "Own"),
    OTHER(0, "Other");

    private final int id;
    private final String name;

    HomeOwnership(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static HomeOwnership getById(int id) {
        for (HomeOwnership e : values()) {
            if (e.id == id) return e;
        }
        return null;
    }

    public static HomeOwnership getByName(String name) {
        for (HomeOwnership e : values()) {
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

package se.nackademin.bankomatdb.model;

// TODO
public final class DTOCustomer {
    private final int id;
    private final String name;

    public DTOCustomer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getCustomerId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

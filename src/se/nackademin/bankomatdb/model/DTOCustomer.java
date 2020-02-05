package se.nackademin.bankomatdb.model;

// TODO
public final class DTOCustomer {
    private final int id;
    private final String name;
    private final String personalId;
    private final String pin;

    public DTOCustomer(int id, String name, String personalId, String pin) {
        this.id = id;
        this.name = name;
        this.personalId = personalId;
        this.pin = pin;
    }

    public int getCustomerId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DTOCustomer updated(String newName, String newPin) {
        return new DTOCustomer(id, (newName != null) ? newName : name, personalId, (newPin != null) ? newPin : pin);
    }
}

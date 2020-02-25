package se.nackademin.bankomatdb.model;

import java.util.Objects;

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

    public String getPersonalId() {
        return personalId;
    }

    public String getPin() {
        return pin;
    }

    public DTOCustomer updated(String newName, String newPin) {
        return new DTOCustomer(this.getCustomerId(), newName, this.getPersonalId(), newPin);
    }

    @Override
    public String toString() {
        return String.format("Kundnummer %d, %s %s", getCustomerId(), getPersonalId(), getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DTOCustomer that = (DTOCustomer) o;
        return getCustomerId() == that.getCustomerId() &&
                getName().equals(that.getName()) &&
                getPersonalId().equals(that.getPersonalId()) &&
                getPin().equals(that.getPin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomerId(), getName(), getPersonalId(), getPin());
    }
}

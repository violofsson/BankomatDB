package se.nackademin.bankomatdb.adminpanel.repository;

import se.nackademin.bankomatdb.*;
import se.nackademin.bankomatdb.model.DTOAccount;
import se.nackademin.bankomatdb.model.DTOCustomer;
import se.nackademin.bankomatdb.model.DTOLoan;
import se.nackademin.bankomatdb.model.DTOTransaction;

import java.io.FileInputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

// Baserat på Jaaneks databas
public class JRepository implements Repository {
    Properties connectionProperties = new Properties();

    public JRepository() throws DatabaseConnectionException {
        try {
            connectionProperties.load(new FileInputStream("src/se/nackademin/JConnection.properties"));
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @Override
    public DTOCustomer addCustomer(String name, String personalId, String pin) throws DatabaseConnectionException {
        String openStatement = "INSERT INTO Kund (Namn, Personnummer, Pin) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(openStatement, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, personalId);
            ps.setString(3, pin);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next(); // Garanterat då affectedRows > 0
                return new DTOCustomer(rs.getInt("Kundnummer"), rs.getString("Namn"), rs.getString("Personnummer"), rs.getString("Pin"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @Override
    public DTOCustomer updateCustomer(DTOCustomer customer) throws DatabaseConnectionException, NoSuchCustomerException {
        String updateQuery = "UPDATE Kund SET Namn = ?, Personnummer = ?, Pin = ? WHERE Kundnummer = ? ";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(updateQuery, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPersonalId());
            ps.setString(3, customer.getPin());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                return new DTOCustomer(rs.getInt("Kundnummer"), rs.getString("Namn"), rs.getString("Personnummer"), rs.getString("Pin"));
            } else {
                throw new NoSuchCustomerException("Failed to update customer with id" + customer.getCustomerId());
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @Override
    public boolean deleteCustomer(int customerId) throws DatabaseConnectionException {
        return deleteEntityById(customerId, "DELETE FROM Kund WHERE Kund.Kundnummer = ?");
    }

    @Override
    public DTOAccount openAccount(int customerId, double interestRate) throws DatabaseConnectionException, NoSuchCustomerException {
        String openStatement = "INSERT INTO Konto (Kund, Saldo, Räntesats) VALUES (?, ?, ?) ";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(openStatement, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, customerId);
            ps.setDouble(2, 0);
            ps.setDouble(3, interestRate);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next(); // Garanterat då affectedRows > 0
                return new DTOAccount(rs.getInt("Kontonummer"), rs.getInt("Kund"), rs.getDouble("Saldo"), rs.getDouble("Räntesats"));
            } else {
                return null; // TODO
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new NoSuchCustomerException(e);
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @Override
    public boolean closeAccount(int accountId) throws DatabaseConnectionException {
        return deleteEntityById(accountId, "DELETE FROM Konto WHERE Konto.Kontonummer = ?");
    }

    // TODO
    @Override
    public DTOAccount deposit(int accountId, double amount) throws DatabaseConnectionException, NoSuchAccountException {
        return null;
    }

    // TODO
    @Override
    public DTOAccount withdraw(int accountId, double amount) throws DatabaseConnectionException, NoSuchAccountException, InsufficientFundsException {
        return null;
    }

    @Override
    public DTOAccount setAccountInterestRate(int accountId, double newInterestRate) throws DatabaseConnectionException, NoSuchAccountException {
        String updateQuery = "UPDATE Konto SET Räntesats = ? WHERE Kontonummer = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(updateQuery, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDouble(1, newInterestRate);
            ps.setInt(2, accountId);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                return new DTOAccount(rs.getInt("Kontonummer"), rs.getInt("Kund"), rs.getDouble("Saldo"), rs.getDouble("Räntesats"));
            } else {
                throw new NoSuchAccountException("Failed to update account " + accountId);
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    // TODO
    @Override
    public DTOLoan approveLoan(int customerId, double sum, double interestRate, LocalDate deadline) throws DatabaseConnectionException, NoSuchCustomerException {
        return null;
    }

    // TODO
    @Override
    public DTOLoan updateLoan(DTOLoan loan) throws DatabaseConnectionException, NoSuchLoanException {
        return null;
    }

    @Override
    public Collection<DTOCustomer> getCustomerData() throws DatabaseConnectionException {
        String customerQuery = "SELECT Kund.Kundnummer AS id, Kund.Namn AS customer_name, Kund.Personnummer AS personal_id, Kund.Pin AS pin FROM Kund";
        List<DTOCustomer> customers = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(customerQuery)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                customers.add(new DTOCustomer(rs.getInt("id"), rs.getString("customer_name"), rs.getString("personal_id"), rs.getString("pin")));
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to load customer data", e);
        }
        return customers;
    }

    @Override
    public Collection<DTOAccount> getAccountData(DTOCustomer customer) throws DatabaseConnectionException, NoSuchCustomerException {
        int customerId = customer.getCustomerId();
        String accountQuery = "SELECT Konto.Kontonummer AS id, Konto.Saldo AS balance, Konto.Räntesats AS interest " +
                "FROM Konto WHERE Konto.Kund = ?";
        List<DTOAccount> accounts = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(accountQuery)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                accounts.add(new DTOAccount(rs.getInt("id"), customerId, rs.getDouble("balance"), rs.getDouble("interest")));
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to load account data", e);
        }
        return accounts;
    }

    @Override
    public Collection<DTOLoan> getLoanData(DTOCustomer customer) throws DatabaseConnectionException, NoSuchCustomerException {
        int customerId = customer.getCustomerId();
        List<DTOLoan> loans = new ArrayList<>();
        try (Connection conn = getConnection()) {
            // Hämta låndata när det fixats
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to load loan data", e);
        }
        return loans;
    }

    @Override
    public Collection<DTOTransaction> getTransactionHistory(DTOAccount account) throws DatabaseConnectionException, NoSuchAccountException {
        int accountId = account.getAccountId();
        String transactionQuery = "SELECT Transaktion.TransaktionID AS id, " +
                "Transaktion.Saldoförändring AS net_balance, " +
                "Transaktion.Tidpunkt AS transaction_time " +
                "FROM Transaktion WHERE Transaktion.Konto = ?";
        List<DTOTransaction> transactions = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(transactionQuery)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                transactions.add(new DTOTransaction(rs.getInt("id"), accountId,
                        rs.getDouble("net_balance"),
                        rs.getTimestamp("transaction_time").toLocalDateTime()));
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to load transaction data", e);
        }
        return transactions;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionProperties.getProperty("connectionURL"), connectionProperties.getProperty("username"), connectionProperties.getProperty("password"));
    }

    private boolean deleteEntityById(int id, String statement) throws DatabaseConnectionException {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(statement)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            return (affectedRows != 0);
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e);
        }
    }
}

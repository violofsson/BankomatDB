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
// TODO
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
        return null;
    }

    @Override
    public DTOCustomer updateCustomer(DTOCustomer customer) throws DatabaseConnectionException {
        return null;
    }

    @Override
    public void deleteCustomer(int customerId) throws DatabaseConnectionException, NoSuchCustomerException {

    }

    @Override
    public DTOAccount openAccount(int customerId, double interestRate) throws DatabaseConnectionException, NoSuchCustomerException {
        return null;
    }

    @Override
    public void closeAccount(int accountId) throws DatabaseConnectionException, NoSuchCustomerException {

    }

    @Override
    public DTOAccount deposit(int accountId, double amount) throws DatabaseConnectionException, NoSuchAccountException {
        return null;
    }

    @Override
    public DTOAccount withdraw(int accountId, double amount) throws DatabaseConnectionException, NoSuchAccountException, InsufficientFundsException {
        return null;
    }

    @Override
    public DTOAccount setAccountInterestRate(int accountId, double newInterestRate) throws DatabaseConnectionException, NoSuchAccountException {
        return null;
    }

    @Override
    public DTOLoan approveLoan(int customerId, double sum, double interestRate, LocalDate deadline) throws DatabaseConnectionException, NoSuchCustomerException {
        return null;
    }

    @Override
    public DTOLoan updateLoan(DTOLoan loan) throws DatabaseConnectionException, NoSuchLoanException {
        return null;
    }

    @Override
    public Collection<DTOCustomer> getCustomerData() throws DatabaseConnectionException {
        List<DTOCustomer> customers = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String customerQuery = "SELECT Kund.Kundnummer AS id, Kund.Namn AS customer_name, Kund.Personnummer AS personal_id, Kund.Pin AS pin FROM Kund";
            PreparedStatement ps = conn.prepareStatement(customerQuery);
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
        List<DTOAccount> accounts = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String accountQuery = "SELECT Konto.Kontonummer AS id, Konto.Saldo AS balance, Konto.Räntesats AS interest " +
                    "FROM Konto WHERE Konto.Kund = ?";
            PreparedStatement ps = conn.prepareStatement(accountQuery);
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
        List<DTOTransaction> transactions = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String transactionQuery = "SELECT Transaktion.TransaktionID AS id, " +
                    "Transaktion.Saldoförändring AS net_balance, " +
                    "Transaktion.Tidpunkt AS transaction_time " +
                    "FROM Transaktion WHERE Transaktion.Konto = ?";
            PreparedStatement ps = conn.prepareStatement(transactionQuery);
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
}

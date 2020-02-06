package se.nackademin.bankomatdb.atm.repository;

import se.nackademin.bankomatdb.*;
import se.nackademin.bankomatdb.model.DTOAccount;
import se.nackademin.bankomatdb.model.DTOCustomer;
import se.nackademin.bankomatdb.model.DTOLoan;
import se.nackademin.bankomatdb.model.DTOTransaction;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class VRepository implements ATMRepository {
    Properties connectionProperties = new Properties();

    public VRepository() throws DatabaseConnectionException {
        try {
            connectionProperties.load(new FileInputStream("src/se/nackademin/VConnection.properties"));
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @Override
    public List<DTOAccount> getCustomerAccounts(int customerId) throws DatabaseConnectionException {
        List<DTOAccount> accounts = new ArrayList<>();
        String accountQuery = "SELECT id, balance, interest_rate " +
                "FROM account_data WHERE owner_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(accountQuery)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                accounts.add(new DTOAccount(rs.getInt("id"), customerId, rs.getDouble("balance"), rs.getDouble("interest_rate")));
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to load account data", e);
        }
        return accounts;
    }

    // TODO
    @Override
    public List<DTOLoan> getCustomerLoans(int customerId) throws DatabaseConnectionException {
        String loanQuery = "SELECT id, original_amount, granted, interest_rate FROM loan_data WHERE debtor_id = ?";
        List<DTOLoan> loans = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(loanQuery)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                loans.add(new DTOLoan(rs.getInt("id"),
                        customerId,
                        rs.getDouble("original_amount"),
                        rs.getDouble("interest_rate"),
                        rs.getDate("granted").toLocalDate(),
                        rs.getDate("deadline").toLocalDate()));
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to load loan data", e);
        }
        return loans;
    }

    @Override
    public List<DTOTransaction> getTransactionHistory(int accountId) throws DatabaseConnectionException, NoSuchAccountException {
        List<DTOTransaction> transactions = new ArrayList<>();
        String transactionQuery = "SELECT id, net_balance, transaction_time " +
                "FROM transaction_data WHERE account_id = ?";
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

    @Override
    public DTOCustomer login(String identification, String pin) throws DatabaseConnectionException, InvalidCredentialsException {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT customer_id, name " +
                     "FROM customer_data WHERE personal_id = ? AND pin = ?")) {
            ps.setString(1, identification);
            ps.setString(2, pin);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new DTOCustomer(rs.getInt("id"), rs.getString("customer_name"), identification, pin);
            } else {
                throw new InvalidCredentialsException();
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    // TODO
    @Override
    public boolean withdraw(int accountId, int amount) throws DatabaseConnectionException, InsufficientFundsException, NoSuchAccountException {
        return false;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionProperties.getProperty("connectionURL"), connectionProperties.getProperty("username"), connectionProperties.getProperty("password"));
    }
}

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

// Baserat på Jaaneks databas
// TODO Kontrollera att alla metoder fungerar
public class JRepository implements ATMRepository {
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
    public List<DTOAccount> getCustomerAccounts(int customerId) throws DatabaseConnectionException {
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
    public List<DTOLoan> getCustomerLoans(int customerId) throws DatabaseConnectionException {
        List<DTOLoan> loans = new ArrayList<>();
        try (Connection conn = getConnection()) {
            // Hämta låndata när det fixats
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to load loan data", e);
        }
        return loans;
    }

    @Override
    public List<DTOTransaction> getTransactionHistory(int accountId) throws DatabaseConnectionException, NoSuchAccountException {
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

    @Override
    public DTOCustomer login(String identification, String pin) throws DatabaseConnectionException, InvalidCredentialsException {
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT Kund.Kundnummer AS id, Kund.Namn AS customer_name " +
                    "FROM Kund WHERE Kund.Personnummer = ? AND Kund.Pin = ?");
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

    @Override
    public boolean withdraw(int accountId, int amount) throws DatabaseConnectionException, InsufficientFundsException, NoSuchAccountException {
        return false;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionProperties.getProperty("connectionURL"), connectionProperties.getProperty("username"), connectionProperties.getProperty("password"));
    }
}

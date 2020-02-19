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

public class VRepository implements Repository {
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
    public DTOCustomer addCustomer(String name, String personalId, String pin) throws DatabaseConnectionException, InvalidInsertException {
        String openStatement = "INSERT INTO customer_data (name, personal_id, pin) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(openStatement, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, personalId);
            ps.setString(3, pin);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next(); // Garanterat då affectedRows > 0
                return new DTOCustomer(rs.getInt(1), name, personalId, pin);
            } else {
                throw new InvalidInsertException("Failed to insert new customer into database");
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @Override
    public DTOCustomer updateCustomer(DTOCustomer customer, String newName, String newPin) throws DatabaseConnectionException, NoSuchRecordException {
        String updateQuery = "UPDATE customer_data SET name = ?, pin = ? WHERE customer_id = ? ";
        customer = customer.updated(newName, newPin);
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(updateQuery, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPin());
            ps.setInt(3, customer.getCustomerId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                return customer;
            } else {
                throw new NoSuchRecordException("Found no customer with id" + customer.getCustomerId());
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @Override
    public boolean deleteCustomer(int customerId) throws DatabaseConnectionException {
        return deleteEntityById(customerId, "DELETE FROM customer_data WHERE customer_id = ?");
    }

    @Override
    public DTOAccount openAccount(int customerId, double initialBalance, double interestRate) throws DatabaseConnectionException, InvalidInsertException, NoSuchRecordException {
        String openStatement = "INSERT INTO account_data (owner_id, balance, interest_rate) VALUES (?, ?, ?) ";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(openStatement, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, customerId);
            ps.setDouble(2, initialBalance);
            ps.setDouble(3, interestRate);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next(); // Garanterat då affectedRows > 0
                return new DTOAccount(rs.getInt(1), customerId, initialBalance, interestRate);
            } else {
                throw new InvalidInsertException("Failed to insert new account into database");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new NoSuchRecordException("Invalid customer id", e);
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @Override
    public boolean closeAccount(int accountId) throws DatabaseConnectionException {
        return deleteEntityById(accountId, "DELETE FROM account_data WHERE account_id = ?");
    }

    // TODO
    @Override
    public DTOTransaction deposit(int accountId, double amount) throws DatabaseConnectionException, NoSuchRecordException {
        if (amount < 0) {
            throw new IllegalArgumentException();
        }
        try (Connection conn = getConnection();
             PreparedStatement updateStatement = conn.prepareStatement("UPDATE account_data SET balance = balance + ? WHERE id = ?");
             PreparedStatement readStatement = conn.prepareStatement("SELECT id, account_id, net_balance, transaction_time FROM transaction_data WHERE id = ?")) {
            updateStatement.setDouble(1, amount);
            updateStatement.setInt(2, accountId);
            int affectedRows = updateStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new NoSuchRecordException("Account number " + accountId + " not found.");
            } else {
                ResultSet rs = updateStatement.getGeneratedKeys();
                rs.next();
                int transactionId = rs.getInt("id");
                readStatement.setInt(1, transactionId);
                rs = readStatement.executeQuery();
                if (rs.next()) {
                    return new DTOTransaction(rs.getInt("id"),
                            rs.getInt("account_id"),
                            rs.getDouble("net_balance"),
                            rs.getTimestamp("transaction_time").toLocalDateTime());
                } else {
                    throw new NoSuchRecordException("No transaction entry generated");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    // TODO
    @Override
    public DTOTransaction withdraw(int accountId, double amount) throws DatabaseConnectionException, NoSuchRecordException, InsufficientFundsException {
        if (amount < 0) {
            throw new IllegalArgumentException();
        }
        try (Connection conn = getConnection();
             PreparedStatement updateStatement = conn.prepareStatement("UPDATE account_data SET balance = balance - ? WHERE id = ?");
             PreparedStatement readStatement = conn.prepareStatement("SELECT id, account_id, net_balance, transaction_time FROM transaction_data WHERE id = ?")) {
            updateStatement.setDouble(1, amount);
            updateStatement.setInt(2, accountId);
            int affectedRows = updateStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new NoSuchRecordException("Account number " + accountId + " not found.");
            } else {
                ResultSet rs = updateStatement.getGeneratedKeys();
                rs.next();
                int transactionId = rs.getInt("id");
                readStatement.setInt(1, transactionId);
                rs = readStatement.executeQuery();
                if (rs.next()) {
                    return new DTOTransaction(rs.getInt("id"),
                            rs.getInt("account_id"),
                            rs.getDouble("net_balance"),
                            rs.getTimestamp("transaction_time").toLocalDateTime());
                } else {
                    throw new NoSuchRecordException("No transaction entry generated");
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new InsufficientFundsException(e);
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @Override
    public DTOAccount setAccountInterestRate(int accountId, double newInterestRate) throws DatabaseConnectionException, NoSuchRecordException {
        String updateQuery = "UPDATE account_data SET balance = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(updateQuery, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDouble(1, newInterestRate);
            ps.setInt(2, accountId);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                // TODO Korrigera
                return new DTOAccount(rs.getInt(1), rs.getInt("owner_id"), rs.getDouble("balance"), newInterestRate);
            } else {
                throw new NoSuchRecordException("No account with id " + accountId);
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    // TODO
    @Override
    public DTOLoan approveLoan(int customerId, double sum, double interestRate, LocalDate deadline) throws DatabaseConnectionException, InvalidInsertException, NoSuchRecordException {
        String insertQuery = "INSERT INTO loan_data (debtor_id, original_amount, granted, deadline, interest_rate) VALUES (?, ?, ?, ?, ?)";
        String readQuery = "SELECT debtor_id, original_amount, granted, deadline, interest_rate FROM loan_data WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement insert = conn.prepareStatement(insertQuery);
             PreparedStatement read = conn.prepareStatement(readQuery)) {
            LocalDate granted = LocalDate.now();
            insert.setInt(1, customerId);
            insert.setDouble(2, sum);
            insert.setDate(3, Date.valueOf(granted));
            insert.setDate(4, Date.valueOf(deadline));
            insert.setDouble(5, interestRate);
            int affextedRows = insert.executeUpdate();
            if (affextedRows > 0) {
                ResultSet rs = insert.getGeneratedKeys();
                rs.next();
                int loanId = rs.getInt(1);
                rs = read.executeQuery();
                rs.next();
                return new DTOLoan(loanId,
                        rs.getInt("debtor_id"),
                        rs.getDouble("original_amount"),
                        rs.getDouble("interest_rate"),
                        rs.getDate("granted").toLocalDate(),
                        rs.getDate("deadline").toLocalDate());
            } else {
                throw new InvalidInsertException("Failed to insert new loan into database");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new NoSuchRecordException("Customer not found", e);
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to approve new loan", e);
        }
    }

    @Override
    public DTOLoan updateLoan(DTOLoan loan, double newInterestRate, LocalDate newDeadline) throws DatabaseConnectionException, NoSuchRecordException {
        String updateQuery = "UPDATE loan_data SET interest_rate = ?, deadline = ? WHERE id = ?";
        try (Connection coon = getConnection();
             PreparedStatement ps = coon.prepareStatement(updateQuery)) {
            ps.setDouble(1, newInterestRate);
            ps.setDate(2, Date.valueOf(newDeadline));
            ps.setInt(3, loan.getLoanId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                return loan.updated(newInterestRate, newDeadline);
            } else {
                throw new NoSuchRecordException("Loan not found");
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    @Override
    public Collection<DTOCustomer> getCustomerData() throws DatabaseConnectionException {
        String customerQuery = "SELECT customer_id, name, personal_id, pin FROM customer_data";
        List<DTOCustomer> customers = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(customerQuery)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                customers.add(new DTOCustomer(rs.getInt("customer_id"), rs.getString("name"), rs.getString("personal_id"), rs.getString("pin")));
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to load customer data", e);
        }
        return customers;
    }

    @Override
    public Collection<DTOAccount> getAccountData(DTOCustomer customer) throws DatabaseConnectionException, NoSuchRecordException {
        int customerId = customer.getCustomerId();
        String accountQuery = "SELECT id, balance, interest_rate " +
                "FROM account_data WHERE owner_id = ?";
        List<DTOAccount> accounts = new ArrayList<>();
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

    @Override
    public Collection<DTOLoan> getLoanData(DTOCustomer customer) throws DatabaseConnectionException, NoSuchRecordException {
        int customerId = customer.getCustomerId();
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
    public Collection<DTOTransaction> getTransactionHistory(DTOAccount account) throws DatabaseConnectionException, NoSuchRecordException {
        int accountId = account.getAccountId();
        String transactionQuery = "SELECT id, net_balance, transaction_time " +
                "FROM transaction_data WHERE account_id = ?";
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

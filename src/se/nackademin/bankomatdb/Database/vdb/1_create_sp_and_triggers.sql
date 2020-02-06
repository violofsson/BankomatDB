USE vo_bank;

DROP TRIGGER IF EXISTS after_account_data_update;
DELIMITER //

CREATE TRIGGER after_account_data_update
    AFTER UPDATE ON account_data
    for each row
BEGIN
    IF old.balance <> new.balance THEN
        INSERT INTO transaction_data (transaction_time, net_balance, account_id) VALUES
        (current_timestamp(), (new.balance-old.balance), old.id);
    END IF;
END //
DELIMITER ;
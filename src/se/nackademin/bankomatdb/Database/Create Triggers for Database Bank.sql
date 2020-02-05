use Bank;

drop trigger if exists after_konto_update;
DELIMITER // 

create trigger after_konto_update
	After update on Konto
    for each row
begin
	if old.Saldo <> new.Saldo then
		insert into Transaktion(Tidpunkt, Saldoförändring, Konto) values 
		(current_timestamp(), (new.Saldo-old.Saldo), old.Kontonummer);
	end if;
END //
DELIMITER ;
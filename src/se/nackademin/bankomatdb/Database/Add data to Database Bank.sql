use Bank;

insert into Kund(Namn, Personnummer, Pin) values
("test01", 01, 0001),
("test02", 02, 0002),
("test03", 03, 0003),
("test04", 04, 0004),
("test05", 05, 0005);

insert into Konto(Saldo, Räntesats, Kund) values
(1000, 1, 1),
(1000, 1, 2),
(1000, 1, 3),
(1000, 1, 4),
(1000, 1, 5);
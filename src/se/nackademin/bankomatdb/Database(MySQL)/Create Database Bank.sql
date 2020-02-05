drop database if exists Bank;
create database Bank;
use Bank;

create table if not exists Kund(
	Kundnummer int not null auto_increment,
    Namn varchar(30) not null,
    Personnummer int not null,
    Pin int,
    primary key(Kundnummer)
);

create table if not exists Konto(
	Kontonummer int not null auto_increment,
    Saldo int not null,
    Räntesats int not null,
    primary key(Kontonummer)
);

create table if not exists Äger(
	Kund int not null,
    Konto int not null,
    foreign key(Kund) references Kund(Kundnummer),
    foreign key(Konto) references Konto(Kontonummer)
);

create table if not exists Transaktion(
	TransaktionID int not null auto_increment,
    Tidpunkt datetime not null,
    Saldoförändring int not null,
    primary key(TransaktionID)
);

create table if not exists Historik(
	Konto int not null,
    Transaktion int not null,
    foreign key(Konto) references Konto(Kontonummer),
    foreign key(Transaktion) references Transaktion(TransaktionID)
);

create table if not exists Lån(
	ID int not null auto_increment,
    Saldo int not null,
    Räntesats int not null,
    Betalplan int not null,
    Beviljades datetime not null,
    primary key(ID)
);
	
create table if not exists Tar_ut(
	Kund int not null,
    Lån int not null,
    foreign key(Kund) references Kund(Kundnummer),
    foreign key(Lån) references Lån(ID)
);
CREATE DATABASE IF NOT EXISTS baza;
USE baza;

CREATE TABLE IF NOT EXISTS pracownicy (
    id INT AUTO_INCREMENT PRIMARY KEY,
    imie VARCHAR(50) NOT NULL,
    nazwisko VARCHAR(50) NOT NULL,
    stanowisko VARCHAR(50) NOT NULL
);

INSERT INTO pracownicy (imie, nazwisko, stanowisko) VALUES
('Jan', 'Kowalski', 'Programista'),
('Anna', 'Nowak', 'Tester'),
('Piotr', 'Wiśniewski', 'Administrator');

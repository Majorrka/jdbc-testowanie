package Zarzadzanie;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Zarzadzanie extends JFrame {

    private JPanel panel1;
    private JPanel panelFormularza;
    private JTextField poleImie;
    private JTextField poleNazwisko;
    private JTextField poleStanowisko;
    private JButton przyciskDodaj;
    private JTable tabela;

    DefaultTableModel modelTabeli;

    String url = "jdbc:mysql://localhost:3306/baza";
    String user = "root";
    String password = "";

    public Zarzadzanie() {
        setTitle("INF.04 - System Obsługi Pracowników");
        setContentPane(panel1);
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] kolumny = {"ID", "Imię", "Nazwisko", "Stanowisko"};
        modelTabeli = new DefaultTableModel(kolumny, 0);
        tabela.setModel(modelTabeli);

        przyciskDodaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodajPracownika();
            }
        });

        odswiezTabele();
    }

    public void odswiezTabele() {
        modelTabeli.setRowCount(0);

        String query = "SELECT id, imie, nazwisko, stanowisko FROM pracownicy";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String imie = rs.getString("imie");
                String nazwisko = rs.getString("nazwisko");
                String stanowisko = rs.getString("stanowisko");

                modelTabeli.addRow(new Object[]{id, imie, nazwisko, stanowisko});
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Błąd połączenia z bazą danych: " + ex.getMessage(),
                    "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void dodajPracownika() {
        String imie = poleImie.getText();
        String nazwisko = poleNazwisko.getText();
        String stanowisko = poleStanowisko.getText();

        if (imie.trim().isEmpty() || nazwisko.trim().isEmpty() || stanowisko.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Wypełnij wszystkie pola!",
                    "Błąd walidacji", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String query = "INSERT INTO pracownicy (imie, nazwisko, stanowisko) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, imie);
            stmt.setString(2, nazwisko);
            stmt.setString(3, stanowisko);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Pracownik został dodany!");

            poleImie.setText("");
            poleNazwisko.setText("");
            poleStanowisko.setText("");

            odswiezTabele();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Błąd zapisu do bazy: " + ex.getMessage(),
                    "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

        public static void main(String[] args) {
            Zarzadzanie okno = new Zarzadzanie();
            okno.setVisible(true);
        }
}

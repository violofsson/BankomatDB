package se.nackademin.bankomatdb.Database;

import java.sql.*;


public class Test {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://35.228.210.231:3306/Bank",
                "root",
                "159357");
             Statement stmt = con.createStatement()
        ) {
            ResultSet rs;

            rs = stmt.executeQuery("select * from Kund");
            StringBuilder sb = new StringBuilder();
            System.out.println("Kundnummer | Namn | Persunnummer | Pin");
            while (rs.next()) {
                sb.append(rs.getInt("Kundnummer")).append(" | ");
                sb.append(rs.getString("Namn")).append(" | ");
                sb.append(rs.getInt("Personnummer")).append(" | ");
                sb.append(rs.getInt("Pin")).append("\n");
            }
            System.out.println(sb);
            sb.delete(0, sb.length());

            System.out.println("-----------------");

            rs = stmt.executeQuery("select * from Konto");
            System.out.println("Kontonummer | Saldo | Räntesats | Kund");
            while (rs.next()) {
                sb.append(rs.getInt("Kontonummer")).append(" | ");
                sb.append(rs.getString("Saldo")).append(" | ");
                sb.append(rs.getInt("Räntesats")).append(" | ");
                sb.append(rs.getInt("Kund")).append("\n");
            }
            System.out.println(sb);
        }
    }
}
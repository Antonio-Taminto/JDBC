import java.sql.*;

public class Main {
    public static void main(String[] args){

        try (Connection conn = DatabaseManager.getConnection()) {
            // Creazione di un nuovo autore
            inserisciAutore(conn, "John Doe");

            // Creazione di un nuovo libro associato all'autore
            inserisciLibro(conn, "The Java Guide", 1);

            // Recupero e stampa dei libri di un autore
            stampaLibriDiAutore(conn, 1);

            inserisciAutore(conn,"NOMEAUTORE");

            inserisciLibro(conn,"LIBRODIAUTORE",2);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void inserisciAutore(Connection conn, String nome) throws SQLException {
        String sql = "INSERT INTO autori (nome) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.executeUpdate();
        }
    }

    private static void inserisciLibro(Connection conn, String titolo, int autoreId) throws SQLException {
        String sql = "INSERT INTO libri (titolo, autore_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titolo);
            stmt.setInt(2, autoreId);
            stmt.executeUpdate();
        }
    }

    private static void stampaLibriDiAutore(Connection conn, int autoreId) throws SQLException {
        String sql = "SELECT * FROM libri WHERE autore_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, autoreId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Titolo: " + rs.getString("titolo"));
                }
            }
        }
    }
}

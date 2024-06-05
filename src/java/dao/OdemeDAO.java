package dao;

import entity.Sepet;
import entity.Urun;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import util.DBConnection;

public class OdemeDAO {

    private Connection db;

    public OdemeDAO() {
    }

    public Connection getConn() {
        if (db == null) {
            DBConnection conn = new DBConnection();
            db = conn.getConnection();
        }
        return db;
    }

    public void odeme(int musteri_id, double tutar, LocalDate tarih) {
        try {
            String query = "INSERT INTO odeme (musteri_id, odeme_tutari, odeme_tarihi) VALUES (?, ?, ?)";
            PreparedStatement st = this.getConn().prepareStatement(query);
            st.setInt(1, musteri_id);
            st.setDouble(2, tutar);
            st.setDate(3, Date.valueOf(tarih));
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void kartOdeme(String ad, long kartNo, int ay, int yil, int cvv) {
        try {
            Statement st = this.getConn().createStatement();
            ResultSet rs = st.executeQuery("SELECT max(id) as id FROM odeme");
            rs.next();
            int odeme_id = rs.getInt("id");
            String dateString = yil + "-" + ay + "-01";
            Date expiryDate = Date.valueOf(dateString);
            String query = "INSERT INTO kart_odeme (odeme_id,kart_numarasi, kart_sahibinin_ad, son_kullanim_tarihi, cvv) VALUES (?,?, ?, ?, ?)";
            PreparedStatement pst = this.getConn().prepareStatement(query);
            pst.setLong(1, odeme_id);
            pst.setLong(2, kartNo);
            pst.setString(3, ad);
            pst.setDate(4, expiryDate);
            pst.setInt(5, cvv);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int kuponArama(String kupon) {
        int kuponDegeri = 0;
        try {
            String query = "SELECT indirim_miktari FROM kupon WHERE kod = ?";
            PreparedStatement st = this.getConn().prepareStatement(query);
            st.setString(1, kupon);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                kuponDegeri = rs.getInt("indirim_miktari");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return kuponDegeri;
    }

}

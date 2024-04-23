package dao;

import entity.Kullanici;
import entity.Musteri;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import util.DBConnection;

public class MusteriDAO {

    private Connection db;

    public MusteriDAO() {

    }

    public Connection getConn() {
        if (db == null) {
            DBConnection conn = new DBConnection();
            db = conn.getConnection();
        }
        return db;
    }

    public void create(Kullanici k) {
        try {
            Statement st = this.getConn().createStatement();
            String query = "INSERT INTO musteri VALUES (DEFAULT, '" + k.getAd()
                    + "', '" + k.getSoyad() + "', '" + k.getEposta() + "', '" + k.getSifre()
                    + "', '" + k.getAdres() + "', '" + k.getTelNo() + "')";
            st.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Musteri getMusteri(Kullanici k) {
        Musteri musteri = null;
        try {
            Statement st = this.getConn().createStatement();
            String query = "SELECT * FROM musteri WHERE eposta='" + k.getEposta() + "' and sifre= '" + k.getSifre() + "'";
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                musteri = new Musteri(rs.getInt("id"), rs.getString("ad"),
                        rs.getString("soyad"), rs.getString("eposta"),
                        rs.getString("sifre"), rs.getString("tel_no"),
                        rs.getString("adres"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return musteri;
    }

    public boolean getMusteri(String email) {
        try {
            Statement st = this.getConn().createStatement();
            String query = "SELECT * FROM musteri WHERE eposta='" + email + "'";
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
}

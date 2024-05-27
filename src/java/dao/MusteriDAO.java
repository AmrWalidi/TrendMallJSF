package dao;

import entity.Kullanici;
import entity.Musteri;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
            String query = "INSERT INTO musteri (ad, soyad, eposta, sifre, adres, tel_no) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement st = this.getConn().prepareStatement(query);
            st.setString(1, k.getAd());
            st.setString(2, k.getSoyad());
            st.setString(3, k.getEposta());
            st.setString(4, k.getSifre());
            st.setString(5, k.getAdres());
            st.setString(6, k.getTelNo());
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void update(Kullanici k) {
        try {
            String query = "UPDATE musteri SET sifre = ?, adres = ?, tel_no = ? WHERE id = ?";
            PreparedStatement st = this.getConn().prepareStatement(query);
            st.setString(1, k.getSifre());
            st.setString(2, k.getAdres());
            st.setString(3, k.getTelNo());
            st.setInt(4, k.getId());
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Musteri getMusteri(Kullanici k) {
        Musteri musteri = null;
        try {
            String query = "SELECT * FROM musteri WHERE eposta= ? and sifre= ? ";
            PreparedStatement st = this.getConn().prepareStatement(query);
            st.setString(1, k.getEposta());
            st.setString(2, k.getSifre());
            ResultSet rs = st.executeQuery();
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
    
    public Musteri getMusteri(int id) {
        Musteri musteri = null;
        try {
            String query = "SELECT * FROM musteri WHERE id = ?";
            PreparedStatement st = this.getConn().prepareStatement(query);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
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

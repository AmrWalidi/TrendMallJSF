package dao;

import entity.Kullanici;
import entity.Satici;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import util.DBConnection;

public class SaticiDAO {

    private Connection db;

    public SaticiDAO() {

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
            String query = "INSERT INTO satici VALUES (DEFAULT, '" + k.getAd()
                    + "', '" + k.getSoyad() + "', '" + k.getEposta() + "', '" + k.getSifre()
                    + "', '" + k.getAdres() + "', '" + k.getTelNo() + "')";
            st.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void update(Kullanici k) {
        try {
            Statement st = this.getConn().createStatement();
                    
            String query ="UPDATE satici SET sifre = '" + k.getSifre() + "'"
                    + ", adres = '" + k.getAdres() + "',"
                    + " tel_no = '" + k.getTelNo() + "'"
                    + " WHERE id = '" + k.getId() + "'";
            st.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Satici getSatici(Kullanici k) {
        Satici satici = null;
        try {
            String query = "SELECT * FROM satici WHERE eposta= ? and sifre= ? ";
            PreparedStatement st = this.getConn().prepareStatement(query);
            st.setString(1, k.getEposta());
            st.setString(2, k.getSifre());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                satici = new Satici(rs.getInt("id"), rs.getString("ad"),
                        rs.getString("soyad"), rs.getString("eposta"),
                        rs.getString("sifre"), rs.getString("tel_no"),
                        rs.getString("adres"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return satici;
    }

    public boolean getSatici(String email) {
        try {
            Statement st = this.getConn().createStatement();
            String query = "SELECT * FROM satici WHERE eposta='" + email + "'";
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

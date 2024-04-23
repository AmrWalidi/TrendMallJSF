/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Kullanici;
import entity.Satici;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import util.DBConnection;

/**
 *
 * @author HP
 */
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

    public Satici getSatici(Kullanici k) {
        Satici satici = null;
        try {
            Statement st = this.getConn().createStatement();
            String query = "SELECT * FROM satici WHERE eposta='" + k.getEposta() + "' and sifre= '" + k.getSifre() + "'";
            ResultSet rs = st.executeQuery(query);
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

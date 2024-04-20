/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Kullanici;
import entity.Musteri;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import util.DBConnection;

/**
 *
 * @author HP
 */
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

    public boolean getMusteri(Kullanici k) {
        try {
            Statement st = this.getConn().createStatement();
            String query = "SELECT * FROM musteri WHERE eposta='" + k.getEposta() + "' and sifre= '" + k.getSifre() + "'";
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
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

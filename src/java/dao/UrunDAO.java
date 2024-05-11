package dao;

import entity.Kategori;
import entity.Satici;
import entity.Urun;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.DBConnection;

public class UrunDAO {

    private Connection db;
    private SaticiDAO saticiDao;

    public Connection getConn() {
        if (db == null) {
            DBConnection conn = new DBConnection();
            db = conn.getConnection();
        }
        return db;
    }

    public SaticiDAO getSaticiDao() {
        if (this.saticiDao == null) {
            saticiDao = new SaticiDAO();
        }
        return saticiDao;
    }

    public int getUrunSayisi() {
        int adet = 0;
        try {
            Statement st = this.getConn().createStatement();
            String query = "SELECT count(*) FROM urun ";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                adet = rs.getInt("count");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return adet;
    }

    public List<Urun> getUrunler(int counter) {
        List<Urun> urunler = new ArrayList<>();
        try {
            Statement st1 = this.getConn().createStatement();
            String query1 = "SELECT * FROM urun offset " + (counter - 1) * 5 + " limit 5";
            ResultSet rs1 = st1.executeQuery(query1);
            while (rs1.next()) {
                Statement st2 = this.getConn().createStatement();
                String query2 = "SELECT * FROM urun_kategori where urun_id = " + rs1.getInt("id");
                ResultSet rs2 = st2.executeQuery(query2);
                List<Kategori> kategoriler = new ArrayList<>();
                while (rs2.next()) {
                    Statement st3 = this.getConn().createStatement();
                    String query3 = "SELECT * FROM kategori where id = " + rs2.getInt("kategori_id");
                    ResultSet rs3 = st3.executeQuery(query3);
                    while (rs3.next()) {
                        kategoriler.add(new Kategori(rs3.getInt("id"), rs3.getString("ad")));
                    }
                }
                Satici s = getSaticiDao().getSatici(rs1.getInt("satici_id"));
                urunler.add(new Urun(rs1.getInt("id"), rs1.getString("ad"), s, kategoriler, rs1.getInt("miktar"), rs1.getFloat("fiyat")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return urunler;
    }
}

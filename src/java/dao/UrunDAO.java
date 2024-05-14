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

    public Kategori getKategori(int id) {
        Kategori kategori = null;
        try {
            Statement st = this.getConn().createStatement();
            String queary = "SELECT * FROM kategori WHERE id = " + id;
            ResultSet rs = st.executeQuery(queary);
            while (rs.next()) {
                kategori = new Kategori(rs.getInt("Id"), rs.getString("ad"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return kategori;
    }

    public List<Kategori> getKategoriler() {
        List<Kategori> kategoriler = new ArrayList<>();
        try {
            Statement st = this.getConn().createStatement();
            String queary = "SELECT * FROM kategori";
            ResultSet rs = st.executeQuery(queary);
            while (rs.next()) {
                kategoriler.add(new Kategori(rs.getInt("Id"), rs.getString("ad")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return kategoriler;
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

    public int getUrunSayisi(List<Kategori> kategoriler) {
        int adet = 0;
        try {
            Statement st = getConn().createStatement();
            String query = "SELECT COUNT(DISTINCT urun_id) FROM urun_kategori WHERE kategori_id IN (";
            for (int i = 0; i < kategoriler.size(); i++) {
                query += kategoriler.get(i).getId() + " ,";
            }
            query = query.substring(0, query.length() - 1);
            query += ")";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                adet = rs.getInt("count");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return adet;
    }

    public int getUrunSayisi(String urun) {
        int adet = 0;
        try {
            Statement st = getConn().createStatement();
            String query = "SELECT COUNT(*) FROM urun WHERE ad = '" + urun + "'";
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

    public List<Urun> getUrunler(int count, List<Kategori> kategoriler) {
        List<Urun> urunler = new ArrayList<>();
        try {
            Statement st = this.getConn().createStatement();
            String query = "SELECT * FROM urun  WHERE id in (SELECT urun_id FROM urun_kategori WHERE kategori_id in(";
            for (int i = 0; i < kategoriler.size(); i++) {
                query += kategoriler.get(i).getId() + " ,";
            }
            query = query.substring(0, query.length() - 1);
            query += ")) offset " + (count - 1) * 5 + " limit 5";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Satici s = getSaticiDao().getSatici(rs.getInt("satici_id"));
                urunler.add(new Urun(rs.getInt("id"), rs.getString("ad"), s, kategoriler, rs.getInt("miktar"), rs.getFloat("fiyat")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return urunler;
    }

    public List<Urun> getUrunler(int counter, String urun) {
        List<Urun> urunler = new ArrayList<>();
        try {
            Statement st1 = this.getConn().createStatement();
            String query1 = "SELECT * FROM urun WHERE ad like '%" + urun + "%'offset " + (counter - 1) * 5 + " limit 5";
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

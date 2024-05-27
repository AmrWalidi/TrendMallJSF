package dao;

import entity.Kategori;
import entity.Satici;
import entity.Urun;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

    private List<Kategori> getUrunKategorileri(int urun_id) {
        List<Kategori> kategoriler = new ArrayList<>();
        try {
            Statement st = this.getConn().createStatement();
            String query = "SELECT * FROM kategori WHERE id in (SELECT kategori_id FROM urun_kategori WHERE urun_id = " + urun_id + ")";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                kategoriler.add(new Kategori(rs.getInt("id"), rs.getString("ad")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return kategoriler;
    }

    public List<Urun> getUrunler(int counter) {
        List<Urun> urunler = new ArrayList<>();
        try {
            Statement st = this.getConn().createStatement();
            String query = "SELECT * FROM urun offset " + (counter - 1) * 5 + " limit 5";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                List<Kategori> kategoriler = this.getUrunKategorileri(rs.getInt("id"));
                Satici s = getSaticiDao().getSatici(rs.getInt("satici_id"));
                urunler.add(new Urun(rs.getInt("id"), rs.getString("ad"), s, kategoriler, rs.getInt("miktar"), rs.getFloat("fiyat"), rs.getBytes("image")));
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
                urunler.add(new Urun(rs.getInt("id"), rs.getString("ad"), s, kategoriler, rs.getInt("miktar"), rs.getFloat("fiyat"),rs.getBytes("image")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return urunler;
    }

    public List<Urun> getUrunler(int counter, String urun) {
        List<Urun> urunler = new ArrayList<>();
        try {
            Statement st = this.getConn().createStatement();
            String query = "SELECT * FROM urun WHERE ad like '%" + urun + "%' offset " + (counter - 1) * 5 + " limit 5";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                List<Kategori> kategoriler = this.getUrunKategorileri(rs.getInt("id"));
                Satici s = getSaticiDao().getSatici(rs.getInt("satici_id"));
                urunler.add(new Urun(rs.getInt("id"), rs.getString("ad"), s, kategoriler, rs.getInt("miktar"), rs.getFloat("fiyat"), rs.getBytes("image")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return urunler;
    }
    
    public Urun getUrun(int id) {
        Urun urun = null;
        try {
            Statement st = this.getConn().createStatement();
            String query = "SELECT * FROM urun WHERE id = " + id;
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                List<Kategori> kategoriler = this.getUrunKategorileri(rs.getInt("id"));
                Satici s = getSaticiDao().getSatici(rs.getInt("satici_id"));
                urun = new Urun(rs.getInt("id"), rs.getString("ad"), s, kategoriler, rs.getInt("miktar"), rs.getFloat("fiyat"), rs.getBytes("image"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return urun;
    }
    

    public void urunEkle(Urun urun, Satici satici) {
        try {
            PreparedStatement st = this.getConn().prepareStatement("INSERT INTO urun VALUES (default, ?, ?, ?, ?, ?)");
            st.setInt(1, satici.getId());
            st.setString(2, urun.getAd());
            st.setInt(3, urun.getMiktar());
            st.setDouble(4, urun.getFiyat());
            st.setBytes(5, urun.bytesConverter(urun.getImage()));
            st.executeUpdate();
            Statement kategoriStatement = this.getConn().createStatement();
            ResultSet rs = kategoriStatement.executeQuery("select max(id) as id FROM urun");
            rs.next();
            int id = rs.getInt("id");
            for (Kategori k : urun.getKategoriler()) {
                String query = "INSERT INTO urun_kategori VALUES(" + id + "," + k.getId() + ")";
                kategoriStatement.executeUpdate(query);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public List<Urun> getSaticiUrunler(Satici satici) {
        List<Urun> urunler = new ArrayList<>();
        try {
            String query = "SELECT * FROM urun WHERE satici_id = ?";
            PreparedStatement ps = this.getConn().prepareStatement(query);
            ps.setInt(1, satici.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                List<Kategori> kategoriler = this.getUrunKategorileri(rs.getInt("id"));
                Satici s = getSaticiDao().getSatici(rs.getInt("satici_id"));
                urunler.add(new Urun(rs.getInt("id"), rs.getString("ad"), s, kategoriler, rs.getInt("miktar"), rs.getFloat("fiyat"), rs.getBytes("image")));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return urunler;
    }
    
    public byte[] urunImage(String id) {
        byte[] image = null;
        try {
            Statement st = this.getConn().createStatement();
            String sql = "SELECT image FROM urun where id = " + id;
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {

                image = rs.getBytes("image");
            }
        } catch (SQLException e) {
            e.getMessage();
        }

        return image;
    }
    
    public void delete(Urun urun) {
        try {
            Statement st = this.getConn().createStatement();
            String query = "DELETE FROM urun_kategori WHERE urun_id = "+ urun.getId();
            st.executeUpdate(query);
            query = "DELETE FROM urun WHERE id = " + urun.getId();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
     public void urunDuzenle(Urun urun) {
        try {
            PreparedStatement st = this.getConn().prepareStatement("UPDATE urun SET ad=?, miktar=?, fiyat=?, image=? WHERE id=?");
            st.setString(1, urun.getAd());
            st.setInt(2, urun.getMiktar());
            st.setDouble(3, urun.getFiyat());
            st.setBytes(4, urun.bytesConverter(urun.getImage()));
            st.setInt(5, urun.getId());
            st.executeUpdate();
            Statement deleteStatement = this.getConn().createStatement();
            String query = "DELETE FROM urun_kategori WHERE urun_id=" + urun.getId();
            deleteStatement.executeUpdate(query);
            for (Kategori k : urun.getKategoriler()) {
                query = "INSERT INTO urun_kategori VALUES(" + urun.getId() + "," + k.getId() + ")";
                deleteStatement.executeUpdate(query);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

     

}

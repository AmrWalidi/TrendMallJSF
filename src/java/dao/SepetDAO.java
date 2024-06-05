package dao;

import entity.Sepet;
import entity.Urun;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.DBConnection;

public class SepetDAO {

    private Connection db;
    private MusteriDAO mDao;
    private UrunDAO uDao;

    public SepetDAO() {

    }

    public MusteriDAO getmDao() {
        if (mDao == null) {
            mDao = new MusteriDAO();
        }
        return mDao;
    }

    public UrunDAO getuDao() {
        if (uDao == null) {
            uDao = new UrunDAO();
        }
        return uDao;
    }

    public Connection getConn() {
        if (db == null) {
            DBConnection conn = new DBConnection();
            db = conn.getConnection();
        }
        return db;
    }

    public Sepet createSepet(int musteriId) {
        try {
            Statement st = this.getConn().createStatement();
            String query = "INSERT INTO sepet (musteri_id) VALUES (" + musteriId + ")";
            st.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return getSepet(musteriId);
    }

    public void sepeteEkle(int sepetId, Urun u) {
        try {
            Statement st = this.getConn().createStatement();
            String query = "INSERT INTO sepet_urun VALUES (" + sepetId + ", " + u.getId() + ")";
            st.executeUpdate(query);
            query = "UPDATE sepet set toplam_ucret = toplam_ucret + " + u.getFiyat() + " WHERE id = " + sepetId;
            st.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Sepet getSepet(int musteriId) {
        Sepet s = new Sepet();
        try {
            Statement st = this.getConn().createStatement();
            String query = "SELECT * FROM Sepet Where musteri_id = " + musteriId;
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                s = new Sepet(rs.getInt("id"), this.getmDao().getMusteri(musteriId), this.getSepetUrunler(rs.getInt("id")), rs.getFloat("toplam_ucret"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return s;
    }

    public List<Urun> getSepetUrunler(int sepetId) {
        List<Urun> urunler = new ArrayList<>();
        try {
            Statement st = this.getConn().createStatement();
            String query = "SELECT * FROM Sepet_urun Where sepet_id = " + sepetId;
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Urun u = this.getuDao().getUrun(rs.getInt("urun_id"));
                urunler.add(u);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return urunler;
    }

    public int getUrunAdet(int sepetId, int urunId) {
        int adet = 0;
        try {
            Statement st = this.getConn().createStatement();
            String query = "SELECT adet FROM Sepet_urun Where sepet_id = " + sepetId + " and urun_id = " + urunId;
            ResultSet rs = st.executeQuery(query);
            rs.next();
            adet = rs.getInt("adet");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return adet;
    }

    public void urunSayisiArtirir(int sepetId, Urun u) {
        try {
            Statement st = this.getConn().createStatement();
            String query = "UPDATE sepet_urun SET adet = adet + 1 WHERE sepet_id= " + sepetId + " and urun_Id = " + u.getId();
            st.executeUpdate(query);
            query = "UPDATE sepet set toplam_ucret = toplam_ucret + " + u.getFiyat() + " WHERE id = " + sepetId;
            st.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void urunSayisiAzaltir(int sepetId, Urun u) {
        try {
            Statement st = this.getConn().createStatement();
            String query = "UPDATE sepet_urun SET adet = adet - 1 WHERE sepet_id= " + sepetId + " and urun_Id = " + u.getId();
            st.executeUpdate(query);
            query = "UPDATE sepet set toplam_ucret = toplam_ucret - " + u.getFiyat() + " WHERE id = " + sepetId;
            st.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void sepettenCikar(int sepetId, Urun u){
        try {
            Statement st = this.getConn().createStatement();
            ResultSet rs = st.executeQuery("SELECT adet FROM sepet_urun WHERE urun_id = " + u.getId() + " and sepet_id = " + sepetId);
            rs.next();
            int adet = rs.getInt("adet");
            String query = "DELETE FROM sepet_urun WHERE urun_id = " + u.getId();
            st.executeUpdate(query);
            query = "UPDATE sepet set toplam_ucret = toplam_ucret - " + (adet * u.getFiyat()) + " WHERE id = " + sepetId;
            st.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void sepetKaldir(int id){
         try {
            Statement st = this.getConn().createStatement();
            st.executeUpdate("DELETE FROM sepet_urun WHERE sepet_id = " + id);
            st.executeUpdate("DELETE FROM sepet WHERE id = " + id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}

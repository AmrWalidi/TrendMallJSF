package dao;

import entity.Sepet;
import entity.SepetUrun;
import entity.SepetUrunId;
import entity.Urun;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Local
@Stateless
public class SepetDAO extends AbstractDAO<Sepet> implements Serializable {

    public SepetDAO() {
        super(Sepet.class);
    }

    public void sepeteEkle(Sepet s, Urun u) {
        SepetUrun su = new SepetUrun(s, u, 1);
        s.getUrunler().add(su);
        s.setToplamUcret(s.getToplamUcret() + u.getFiyat());
        this.update(s);
    }

    public Sepet getSepetByMusteriId(int musteriId) {
        Query query = this.em.createQuery("SELECT s FROM Sepet s WHERE s.musteri.id = " + musteriId, Sepet.class);
        try {
            return (Sepet) query.getSingleResult();
        } catch (NoResultException e) {
            return new Sepet();
        }
    }

    public List<Urun> getSepetUrunler(int id) {
        List<Urun> urunler = new ArrayList<>();
        Query query = this.em.createQuery("SELECT su FROM SepetUrun su WHERE su.sepet.id = " + id, SepetUrun.class);
        try {
            List<SepetUrun> suList = query.getResultList();
            for (SepetUrun su : suList) {
                urunler.add(su.getUrun());
            }
            return urunler;
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    public int getUrunAdet(int sepetId, int urunId) {
        SepetUrun sepetUrun = this.em.find(SepetUrun.class, new SepetUrunId(sepetId, urunId));
        if (sepetUrun != null) {
            return sepetUrun.getAdet();
        }
        return 0;
    }

    public void urunSayisiArtirir(Sepet s, Urun u) {
        SepetUrun sepetUrun = this.em.find(SepetUrun.class, new SepetUrunId(s.getId(), u.getId()));
        for (SepetUrun su : s.getUrunler()) {
            if (su.getUrun().getId() == u.getId()) {
                sepetUrun = su;
                break;
            }
        }
        sepetUrun.setAdet(sepetUrun.getAdet() + 1);
        s.setToplamUcret(s.getToplamUcret() + u.getFiyat());
        this.update(s);
    }

    public void urunSayisiAzaltir(Sepet s, Urun u) {
        SepetUrun sepetUrun = this.em.find(SepetUrun.class, new SepetUrunId(s.getId(), u.getId()));
        for (SepetUrun su : s.getUrunler()) {
            if (su.getUrun().getId() == u.getId()) {
                sepetUrun = su;
                break;
            }
        }
        sepetUrun.setAdet(sepetUrun.getAdet() - 1);
        s.setToplamUcret(s.getToplamUcret() - u.getFiyat());
        this.update(s);
    }

    public void sepettenCikar(Sepet s, Urun u) {
        try {
            int adet = 0;
            SepetUrun sepetUrun = this.em.find(SepetUrun.class, new SepetUrunId(s.getId(), u.getId()));
            if (sepetUrun != null) {
                adet = sepetUrun.getAdet();
            }
            s.setToplamUcret(s.getToplamUcret() - (adet * u.getFiyat()));
            for (SepetUrun su : s.getUrunler()) {
                if (su.getUrun().getId() == u.getId()) {
                    sepetUrun = su;
                    break;
                }
            }
            s.getUrunler().remove(sepetUrun);
            this.update(s);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
//    public void sepetKaldir(int id){
//         try {
//            Statement st = this.getConn().createStatement();
//            st.executeUpdate("DELETE FROM sepet_urun WHERE sepet_id = " + id);
//            st.executeUpdate("DELETE FROM sepet WHERE id = " + id);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }

}

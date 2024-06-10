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
        try {
            Query query = this.em.createQuery("SELECT su FROM SepetUrun su WHERE su.sepet.id = " + s.getId() + " and su.urun.id = " + u.getId() + "", SepetUrun.class);
            SepetUrun su = (SepetUrun) query.getSingleResult();
            su.setAdet(su.getAdet() + 1);
            this.em.merge(su);
        } catch (NoResultException ex) {
            SepetUrun su = new SepetUrun(s, u, 1);
            this.em.persist(su);

        }
        Sepet sepet = this.getById(s.getId());
        sepet.setToplamUcret(sepet.getToplamUcret() + u.getFiyat());
        this.update(sepet);
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

    public void urunSayisiArtirir(int sepetId, Urun u) {
        Query query = this.em.createQuery("SELECT su FROM SepetUrun su WHERE su.sepet.id = " + sepetId + " and su.urun.id = " + u.getId(), SepetUrun.class);
        SepetUrun su = (SepetUrun) query.getSingleResult();
        su.setAdet(su.getAdet() + 1);
        this.em.merge(su);
        Sepet sepet = this.getById(sepetId);
        sepet.setToplamUcret(sepet.getToplamUcret() + u.getFiyat());
        this.update(sepet);
    }

    public void urunSayisiAzaltir(int sepetId, Urun u) {
        Query query = this.em.createQuery("SELECT su FROM SepetUrun su WHERE su.sepet.id = " + sepetId + " and su.urun.id = " + u.getId(), SepetUrun.class);
        SepetUrun su = (SepetUrun) query.getSingleResult();
        su.setAdet(su.getAdet() - 1);
        this.em.merge(su);
        Sepet sepet = this.getById(sepetId);
        sepet.setToplamUcret(sepet.getToplamUcret() - u.getFiyat());
        this.update(sepet);
    }

    public void sepettenCikar(int sepetId, Urun u) {
        try {
            int adet = 0;
            SepetUrun sepetUrun = this.em.find(SepetUrun.class, new SepetUrunId(sepetId, u.getId()));
            if (sepetUrun != null) {
                adet = sepetUrun.getAdet();
            }
            this.em.remove(sepetUrun);
            Sepet sepet = this.getById(sepetId);
            sepet.setToplamUcret(sepet.getToplamUcret() - (adet * u.getFiyat()));
            this.update(sepet);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}

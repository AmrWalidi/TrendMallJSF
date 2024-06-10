package dao;

import entity.Kategori;
import entity.Satici;
import entity.Urun;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Local
@Stateless
public class UrunDAO extends AbstractDAO implements Serializable {

    @Inject
    private KategoriDAO kategoriDAO;

    public UrunDAO() {
        super(Urun.class);
    }

    public int getUrunSayisi() {
        Query query = this.em.createQuery("SELECT COUNT(u) FROM Urun u", Urun.class);
        Long count = (Long) query.getSingleResult();
        return count.intValue();
    }

    public int getUrunSayisi(List<Kategori> kategoriler) {
        int adet = 0;
        for (Kategori k : kategoriler) {
            Kategori kategori = this.kategoriDAO.getById(k.getId());
            adet += kategori.getUrunler().size();
        }
        return adet;
    }

    public int getUrunSayisi(String urun) {
        Query query = this.em.createQuery("SELECT COUNT(u) FROM Urun u WHERE u.ad like '%" + urun + "%' ", Urun.class);
        Long count = (Long) query.getSingleResult();
        return count.intValue();
    }

    public List<Urun> getUrunler(int counter) {
        try {
            int pageSize = 5;
            int offset = (counter - 1) * pageSize;

            Query query = this.em.createQuery("SELECT u FROM Urun u", Urun.class)
                    .setFirstResult(offset)
                    .setMaxResults(pageSize);
            return query.getResultList();
        } catch (NoResultException ex) {
            return new ArrayList<>();
        }
    }

    public List<Urun> getUrunler(int count, List<Kategori> kategoriler) {
        List<Urun> urunler = new ArrayList<>();
        for (Kategori k : kategoriler) {
            for (Urun u : k.getUrunler()) {
                urunler.add(u);
            }
        }
        return urunler;
    }

    public List<Urun> getSaticiUrunler(Satici s) {
        try {
            Query query = this.em.createQuery("SELECT u FROM Urun u WHERE u.satici.id = " + s.getId(), Urun.class);
            return query.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }

    }

    public List<Urun> getUrunler(int counter, String urun) {
        Query query = this.em.createQuery("SELECT u FROM Urun u WHERE u.ad like '%" + urun + "%' ", Urun.class);
        return query.getResultList();
    }

    public byte[] urunImage(String id) {
        try {
            Query query = this.em.createQuery("SELECT u.image FROM Urun u WHERE u.id = " + id, Urun.class);
            return (byte[]) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}

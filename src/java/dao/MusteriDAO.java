package dao;

import entity.Kullanici;
import entity.Musteri;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import java.io.Serializable;

@Local
@Stateless
public class MusteriDAO extends AbstractDAO<Musteri> implements Serializable {


    public MusteriDAO() {
        super(Musteri.class);
    }

    public Musteri getMusteri(Kullanici k) {
        Query q = em.createQuery("select m from Musteri m where m.eposta = '" + k.getEposta() + "' and m.sifre = '" + k.getSifre() + "' ", Musteri.class);
        try {
            return (Musteri) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public boolean getMusteri(String email) {
        Query q = em.createQuery("select m from Musteri m where m.eposta = '" + email + "' " , Musteri.class);
        try {
            Musteri m = (Musteri) q.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
}

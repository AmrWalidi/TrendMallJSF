package dao;

import entity.Kullanici;
import entity.Satici;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import java.io.Serializable;

@Local
@Stateless
public class SaticiDAO extends AbstractDAO<Satici> implements Serializable{


    public SaticiDAO() {
        super(Satici.class);
    }

    public Satici getSatici(Kullanici k) {
        Query q = em.createQuery("select s from Satici s where s.eposta = '" + k.getEposta() + "' and s.sifre = '" + k.getSifre() + "' ", Satici.class);
        try {
            return (Satici) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public boolean getSatici(String email) {
        Query q = em.createQuery("select s from Satici s where s.eposta = '" + email + "' " , Satici.class);
        try {
            Satici s = (Satici) q.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
}

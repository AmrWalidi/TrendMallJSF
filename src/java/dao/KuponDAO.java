package dao;

import entity.Kupon;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import java.io.Serializable;

@Local
@Stateless
public class KuponDAO extends AbstractDAO<Kupon> implements Serializable {

    public KuponDAO() {
        super(Kupon.class);
    }

    public double kuponArama(String kupon) {
        Query query = this.em.createQuery("SELECT k.indirimMiktari FROM Kupon k WHERE k.kod = :kupon", Kupon.class);
        query.setParameter("kupon", kupon);
        return (double) query.getSingleResult();
    }
}

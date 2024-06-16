package dao;

import entity.Kart;
import entity.Musteri;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Local
@Stateless
public class KartDAO extends AbstractDAO<Kart> implements Serializable {

    public KartDAO() {
        super(Kart.class);
    }
    
    public List<Kart> getEntities(Musteri m){
        Query query = this.em.createQuery("SELECT k FROM Kart k WHERE k.musteri.id = " + m.getId() , Kart.class);
        if (query.getResultList() == null)
            return new ArrayList<>();
        return query.getResultList(); 
    }
}

package dao;

import entity.Siparis;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import java.io.Serializable;

@Local
@Stateless
public class SiparisDAO extends AbstractDAO<Siparis> implements Serializable {

    public SiparisDAO() {
        super(Siparis.class);
    }
}

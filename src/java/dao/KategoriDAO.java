package dao;

import entity.Kategori;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import java.io.Serializable;

@Local
@Stateless
public class KategoriDAO extends AbstractDAO<Kategori> implements Serializable {

    public KategoriDAO() {
        super(Kategori.class);
    }
}

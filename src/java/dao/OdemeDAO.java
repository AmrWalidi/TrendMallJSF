package dao;

import entity.Odeme;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import java.io.Serializable;
import java.sql.*;

@Local
@Stateless
public class OdemeDAO extends AbstractDAO<Odeme> implements Serializable {

    private Connection db;

    public OdemeDAO() {
        super(Odeme.class);
    }
}

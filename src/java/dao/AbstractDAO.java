package dao;

import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.io.Serializable;
import java.util.List;

@Local
@Stateless
public abstract class AbstractDAO<T> implements Serializable {

    @PersistenceContext(unitName = "TrendMallPU")
    protected EntityManager em;

    private Class<T> entityClass;

    public AbstractDAO() {
    }

    public AbstractDAO(Class<T> ec) {
        this.entityClass = ec;
    }

    public void create(T entity) {
        em.persist(entity);
    }

    public void update(T entity) {
        em.merge(entity);
    }

    public List<T> getEntities() {
        Query q = em.createQuery("select c from " + entityClass.getSimpleName() + " c order by c.id desc", entityClass);
        return q.getResultList();
    }

    public void delete(T entity) {
        em.remove(em.merge(entity));
        em.flush();
    }

    public T getById(int id) {
        return (T) em.find(entityClass, id);
    }

}

package home.patterns.model.dao;

import home.patterns.access.IAccessRoleAccessor;
import home.patterns.model.entity.ApplicationEntity;
import home.patterns.model.facade.IPersistenceUnitAccessor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public abstract class AbstractUniversalDao<T extends ApplicationEntity, AR>
        implements IAccessRoleAccessor<AR>{
    private final AR accessRole;
    private final Class<T> entityClass;
    private final IPersistenceUnitAccessor<AR> puAccessor;


    public AbstractUniversalDao(Class<T> entityClass, AR accessRole, IPersistenceUnitAccessor<AR> pua) {
        this.entityClass = entityClass;
        this.accessRole = accessRole;
        this.puAccessor = pua;
    }

    protected EntityManagerFactory getEntityManagerFactory() {
        return puAccessor.getEMFbyAccessRole(this);
    }

    private EntityManager getEntityManager() {
        return this.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public AR getAccessRole() {
        return accessRole;
    }

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
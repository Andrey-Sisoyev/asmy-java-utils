package home.patterns.model.dao;

import java.util.List;

import home.log.WithLog;
import home.patterns.access.IAccessRoleAccessor;
import home.patterns.model.entity.ApplicationEntity;
import home.patterns.model.facade.IPersistenceUnitAccessor;
import home.utils.reflection.ObjectsHierarchy;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class GeneralDao<T extends ApplicationEntity> extends WithLog {

    private final Class<T> entityClass;
    private final EntityManager em;
    private static final String ERR__NOPUACCESS = "No access to persistence unit for role '%1$s' is presumed by general DAO for entity '%2$s'.";
    private static final String ERR__NULLACCESS = "Access role shouldn't be NULL.";

    public <AR extends Object> GeneralDao(Class<T> entityClass, IPersistenceUnitAccessor<AR> pua, IAccessRoleAccessor<AR> ara) {
        super(new ObjectsHierarchy(GeneralDao.class, entityClass));

        this.entityClass = entityClass;
        AR ar = ara.getAccessRole();
        if(ar == null) this.errorP(new NullPointerException(ERR__NULLACCESS));
        if(! pua.roleHasAccessToEMF(ar))
            this.errorP(new SecurityException(String.format(ERR__NOPUACCESS, new Object[] { ar.toString(), entityClass.getSimpleName() } )));

        this.em = pua.getEMFbyAccessRole(ar).createEntityManager();
    }

    private EntityManager getEntityManager() {
        return this.em;
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
        CriteriaQuery<T> cq = getEntityManager().getCriteriaBuilder().createQuery(entityClass);
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        CriteriaQuery<T> cq = getEntityManager().getCriteriaBuilder().createQuery(entityClass);
        cq.select(cq.from(entityClass));
        Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}

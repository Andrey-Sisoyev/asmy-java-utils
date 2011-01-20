package home.patterns.model.facade;

import home.patterns.access.IAccessRoleAccessor;
import javax.persistence.EntityManagerFactory;

public interface IPersistenceUnitAccessor<AR> {
    public EntityManagerFactory getEMFbyAccessRole(IAccessRoleAccessor<AR> ara);
    public EntityManagerFactory getEMFbyAccessRole(AR ar);
    public boolean roleHasAccessToEMF(IAccessRoleAccessor<AR> ara);
    public boolean roleHasAccessToEMF(AR ar);

}

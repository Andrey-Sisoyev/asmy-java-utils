package home.patterns.model.facade;

import home.patterns.access.IAccessRoleAccessor;
import javax.persistence.EntityManagerFactory;

public interface IPersistenceUnitAccessor<AR> {
    public EntityManagerFactory getEMFbyAccessRole(IAccessRoleAccessor<AR> ar);

}

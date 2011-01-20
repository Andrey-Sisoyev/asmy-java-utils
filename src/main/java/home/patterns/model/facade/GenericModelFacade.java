package home.patterns.model.facade;

import home.patterns.access.IAccessRoleAccessor;
import java.util.EnumMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GenericModelFacade<AR extends Enum<AR>>
        implements IPersistenceUnitAccessor<AR> {

    private final Map<AR, EntityManagerFactory> emfsByAccessRole;
    
    public GenericModelFacade(Class<AR> clazz, Map<AR, String> puNames) {
        emfsByAccessRole = new EnumMap(clazz);
        String puName;
        for(Map.Entry<AR,String> entry : puNames.entrySet()) {
            puName = entry.getValue();
            if(puName == null) emfsByAccessRole.put(entry.getKey(), null);
            else {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory(puName);
                emfsByAccessRole.put(
                    entry.getKey()
                  , emf
                  );
            }
        }
    }

    @Override
    public EntityManagerFactory getEMFbyAccessRole(IAccessRoleAccessor<AR> ara) {
        return this.getEMFbyAccessRole(ara.getAccessRole());
    }

    @Override
    public EntityManagerFactory getEMFbyAccessRole(AR ar) {
        return this.emfsByAccessRole.get(ar);
    }

    @Override
    public boolean roleHasAccessToEMF(IAccessRoleAccessor<AR> ara) {
        return this.roleHasAccessToEMF(ara.getAccessRole());
    }

    @Override
    public boolean roleHasAccessToEMF(AR ar) {
        return this.emfsByAccessRole.get(ar) != null;
    }

    @Override
    protected void finalize() throws Throwable {
        for(Map.Entry<AR,EntityManagerFactory> en : emfsByAccessRole.entrySet()) {
            en.getValue().close();
        }
        super.finalize();
    }
}

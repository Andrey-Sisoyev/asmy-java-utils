package home.patterns.model.facade;

import home.patterns.access.IAccessRoleAccessor;
import java.util.EnumMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ModelFacade<AR extends Enum<AR>>
        implements IPersistenceUnitAccessor<AR> {

    private final Map<AR, EntityManagerFactory> emfsByAccessRole;
    
    public ModelFacade(Class<AR> clazz, Map<AR, String> puNames) {
        emfsByAccessRole = new EnumMap(clazz);
        for(Map.Entry<AR,String> entry : puNames.entrySet()) {
            emfsByAccessRole.put(
                    entry.getKey()
                  , Persistence.createEntityManagerFactory(entry.getValue())
                  );
        }
    }

    @Override
    public EntityManagerFactory getEMFbyAccessRole(IAccessRoleAccessor<AR> ara) {
        AR ar = ara.getAccessRole();
        return emfsByAccessRole.get(ar);
    }
}

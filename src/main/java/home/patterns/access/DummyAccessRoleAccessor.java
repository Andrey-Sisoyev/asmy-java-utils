package home.patterns.access;

public class DummyAccessRoleAccessor<AR> implements IAccessRoleAccessor<AR> {
    private AR ar;

    public DummyAccessRoleAccessor(AR ar) {
        this.ar = ar;
    }

    @Override public AR getAccessRole() { return ar; }

}

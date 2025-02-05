package spring.ecosystem.rest_api_template.enums;

import java.util.Arrays;
import java.util.Set;

public enum Role {
    ADMIN(Set.of(Permission.values())), // Admin tiene todos los permisos
    MANAGER(Set.of(Permission.READ_USERS, Permission.MANAGE_ROLES, Permission.VIEW_REPORTS)),
    USER(Set.of(Permission.READ_SERVICES, Permission.BOOK_APPOINTMENTS, Permission.CANCEL_APPOINTMENTS)),
    GUEST(Set.of(Permission.READ_SERVICES));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
}

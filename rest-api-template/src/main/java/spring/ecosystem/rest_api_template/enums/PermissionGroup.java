package spring.ecosystem.rest_api_template.enums;

import java.util.Set;

public enum PermissionGroup {

    USER_MANAGEMENT(Set.of(
            Permission.USER_CREATE, Permission.USER_EDIT, Permission.USER_DELETE,
            Permission.USER_CHANGE_ROLE, Permission.USER_BLOCK, Permission.USER_UNBLOCK,
            Permission.USER_RESET_PASSWORD)),
    SECURITY_MANAGEMENT(Set.of(
            Permission.AUTH_CONFIGURE_2FA, Permission.AUTH_VIEW_LOGS, Permission.AUTH_MANAGE_API_KEYS)),
    CONTENT_MANAGEMENT(Set.of(
            Permission.CONTENT_CREATE, Permission.CONTENT_EDIT, Permission.CONTENT_DELETE,
            Permission.CONTENT_PUBLISH, Permission.CONTENT_APPROVE)),
    SYSTEM_ADMINISTRATION(Set.of(
            Permission.SYSTEM_CONFIGURE, Permission.SYSTEM_MANAGE_INTEGRATIONS,
            Permission.SYSTEM_BACKUP_RESTORE, Permission.SYSTEM_VIEW_LOGS));

    private final Set<Permission> permissions;

    PermissionGroup(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

}

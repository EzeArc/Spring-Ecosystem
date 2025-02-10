package spring.ecosystem.rest_api_template.enums;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    SUPER_ADMIN(Set.of(PermissionGroup.USER_MANAGEMENT, PermissionGroup.SECURITY_MANAGEMENT,
            PermissionGroup.CONTENT_MANAGEMENT, PermissionGroup.SYSTEM_ADMINISTRATION)),
    ADMIN(Set.of(PermissionGroup.USER_MANAGEMENT, PermissionGroup.CONTENT_MANAGEMENT,
            PermissionGroup.SYSTEM_ADMINISTRATION)),
    MODERATOR(Set.of(PermissionGroup.CONTENT_MANAGEMENT)),
    USER(Set.of(PermissionGroup.CONTENT_MANAGEMENT)),
    GUEST(Set.of());

    private final Set<PermissionGroup> permissionGroups;

    Role(Set<PermissionGroup> permissionGroups) {
        this.permissionGroups = permissionGroups;
    }

    public Set<Permission> getPermissions() {
        return permissionGroups.stream()
                .flatMap(group -> group.getPermissions().stream())
                .collect(Collectors.toSet());
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}

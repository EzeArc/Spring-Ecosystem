package spring.ecosystem.rest_api_template.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Permission implements GrantedAuthority {
    // User Management
    USER_CREATE, USER_EDIT, USER_DELETE, USER_CHANGE_ROLE,
    USER_BLOCK, USER_UNBLOCK, USER_RESET_PASSWORD,

    // Authentication & Security
    AUTH_LOGIN, AUTH_LOGOUT, AUTH_VIEW_LOGS,
    AUTH_CONFIGURE_2FA, AUTH_VIEW_FAILED_ATTEMPTS, AUTH_MANAGE_API_KEYS,

    // Content Management
    CONTENT_CREATE, CONTENT_EDIT, CONTENT_DELETE,
    CONTENT_PUBLISH, CONTENT_APPROVE, CONTENT_VIEW_RESTRICTED,

    // System Configuration
    SYSTEM_CONFIGURE, SYSTEM_MANAGE_INTEGRATIONS,
    SYSTEM_BACKUP_RESTORE, SYSTEM_VIEW_LOGS,

    // Notifications & Messaging
    NOTIFICATION_SEND, NOTIFICATION_CONFIGURE, NOTIFICATION_VIEW_HISTORY;

    @Override
    public String getAuthority() {
        return name();
    }
}

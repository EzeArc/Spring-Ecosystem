DELETE FROM users;

INSERT INTO users (id, user_name, first_name, last_name, email, password, role, created_at, updated_at, created_by, updated_by, is_active) VALUES
    (gen_random_uuid(), 'user1', 'Juan', 'Pérez', 'juan1@example.com', 'password123', 'USER', now(), now(), 'admin', 'admin', true),
    (gen_random_uuid(), 'user2', 'Ana', 'López', 'ana@example.com', 'password123', 'USER', now(), now(), 'admin', 'admin', true),
    (gen_random_uuid(), 'user3', 'Carlos', 'García', 'carlos@example.com', 'password123', 'USER', now(), now(), 'admin', 'admin', true),
    (gen_random_uuid(), 'user4', 'Laura', 'Martínez', 'laura@example.com', 'password123', 'ADMIN', now(), now(), 'admin', 'admin', true),
    (gen_random_uuid(), 'user5', 'Miguel', 'Ramírez', 'miguel@example.com', 'password123', 'USER', now(), now(), 'admin', 'admin', true),
    (gen_random_uuid(), 'user6', 'María', 'Fernández', 'maria@example.com', 'password123', 'ADMIN', now(), now(), 'admin', 'admin', true),
    (gen_random_uuid(), 'user7', 'Pablo', 'Sánchez', 'pablo@example.com', 'password123', 'USER', now(), now(), 'admin', 'admin', true),
    (gen_random_uuid(), 'user8', 'Elena', 'Torres', 'elena@example.com', 'password123', 'USER', now(), now(), 'admin', 'admin', true),
    (gen_random_uuid(), 'user9', 'Ricardo', 'Díaz', 'ricardo@example.com', 'password123', 'USER', now(), now(), 'admin', 'admin', true),
    (gen_random_uuid(), 'user10', 'Sofía', 'Mendoza', 'sofia@example.com', 'password123', 'ADMIN', now(), now(), 'admin', 'admin', true);

-- insert catalog
INSERT INTO catalog (name, type)
VALUES ('Cédula', 'IDENTITY_TYPE');
INSERT INTO catalog (name, type)
VALUES ('RUC', 'IDENTITY_TYPE');
INSERT INTO catalog (name, type)
VALUES ('Pasaporte', 'IDENTITY_TYPE');

DO
$$
    DECLARE
        ruc_id     int8;
        company_id uuid;
        role_id    int4;
        user_id    uuid;
        page_id    int4;
    BEGIN
        SELECT id INTO ruc_id FROM catalog WHERE name = 'RUC';

        -- insert companies
        INSERT INTO companies (company_name, identifier, phone, email, high_street, side_street, identity_type_id,
                               identity)
        VALUES ('Merx Cargo Solutions', '1793217494001', '0962328651', 'merxcargo@outlook.com', 'Carlos Andrade Marin',
                'Florencio Espinoza', ruc_id, '1793217494001')
        RETURNING id INTO company_id;

        -- insert configurations
        INSERT INTO configurations (id, company_id, logo, primary_color, secondary_color)
        VALUES (uuid_generate_v4(), company_id, '', '#2C4080', '#D99943');

        -- insert roles
        INSERT INTO roles (role)
        VALUES ('SUPER_ADMIN')
        RETURNING id INTO role_id;

        -- insert users
        INSERT INTO users (id, company_id, role_id, name, last_name, email, phone, identity_type_id, identity,
                           created_at)
        VALUES (uuid_generate_v4(), company_id, role_id, 'Jimmy', 'Atiencia', 'merxcargo@outlook.com', '0962328651',
                ruc_id, '1793217494001', now())
        RETURNING id INTO user_id;

        -- insert security
        INSERT INTO security (id, user_id, password, intents, validated, enabled, blocked, password_expires_at)
        VALUES (uuid_generate_v4(), user_id, '$2a$10$IOJRQbZbgU8cEB6.Ql0SH.itAhcOnX8P1HMi.T/HUJSuLyEThORFG', 0, true,
                true, false, now() + INTERVAL '6 months');

        -- Insertar primer usuario con validated = false
        INSERT INTO users (id, company_id, role_id, name, last_name, email, phone, identity_type_id, identity,
                           created_at)
        VALUES (uuid_generate_v4(), company_id, role_id, 'Nombre', 'Apellido', 'user@example.com', '123456789',
                ruc_id, '12345678901', now())
        RETURNING id INTO user_id;

        INSERT INTO security (id, user_id, password, intents, validated, enabled, blocked, password_expires_at)
        VALUES (uuid_generate_v4(), user_id, '$2a$10$IOJRQbZbgU8cEB6.Ql0SH.itAhcOnX8P1HMi.T/HUJSuLyEThORFG', 0, false,
                true, true, now() + INTERVAL '6 months');

        -- Insertar segundo usuario con enabled = false
        INSERT INTO users (id, company_id, role_id, name, last_name, email, phone, identity_type_id, identity,
                           created_at)
        VALUES (uuid_generate_v4(), company_id, role_id, 'Nombre2', 'Apellido2', 'user2@example.com', '987654321',
                ruc_id, '98765432101', now())
        RETURNING id INTO user_id;

        INSERT INTO security (id, user_id, password, intents, validated, enabled, blocked, password_expires_at)
        VALUES (uuid_generate_v4(), user_id, '$2a$10$IOJRQbZbgU8cEB6.Ql0SH.itAhcOnX8P1HMi.T/HUJSuLyEThORFG', 0, true,
                false, true, now() + INTERVAL '6 months');

        -- Insertar tercer usuario con blocked = true
        INSERT INTO users (id, company_id, role_id, name, last_name, email, phone, identity_type_id, identity,
                           created_at)
        VALUES (uuid_generate_v4(), company_id, role_id, 'Nombre3', 'Apellido3', 'user3@example.com', '555555555',
                ruc_id, '55555555501', now())
        RETURNING id INTO user_id;

        INSERT INTO security (id, user_id, password, intents, validated, enabled, blocked, password_expires_at)
        VALUES (uuid_generate_v4(), user_id, '$2a$10$IOJRQbZbgU8cEB6.Ql0SH.itAhcOnX8P1HMi.T/HUJSuLyEThORFG', 0, true,
                true, true, now() + INTERVAL '6 months');

        -- Insertar cuarto usuario con history_passwords = null
        INSERT INTO users (id, company_id, role_id, name, last_name, email, phone, identity_type_id, identity,
                           created_at)
        VALUES (uuid_generate_v4(), company_id, role_id, 'Nombre4', 'Apellido4', 'user4@example.com', '555555555',
                ruc_id, '55555555502', now())
        RETURNING id INTO user_id;

        INSERT INTO security (id, user_id, password, intents, validated, enabled, blocked, password_expires_at)
        VALUES (uuid_generate_v4(), user_id, '$2a$10$IOJRQbZbgU8cEB6.Ql0SH.itAhcOnX8P1HMi.T/HUJSuLyEThORFG', 0, true,
                true, false, now() + INTERVAL '6 months');
        
        INSERT INTO pages(page_type, icon, "path", title, actions, previous_menu) 
        VALUES('view', 'icon-dashboard', '/dashboard', 'Dashboard', '["DELETE", "VIEW", "UPDATE"]'::jsonb, 1)
        RETURNING id INTO page_id;

        INSERT INTO pages_role(page_id, role_id, actions)VALUES(page_id, role_id, '["DELETE", "VIEW"]'::jsonb);

        INSERT INTO roles(role)
        VALUES ('ADMIN_COMPANIE')
        RETURNING id INTO role_id;

        INSERT INTO pages(page_type, icon, "path", title, actions, previous_menu) 
        VALUES('admin', 'icon-companie', '/companie', 'Companie', '["DELETE", "VIEW", "UPDATE","CREATE"]'::jsonb, 2)
        RETURNING id INTO page_id;

        INSERT INTO pages_role(page_id, role_id, actions)VALUES(page_id, role_id, '["CREATE", "VIEW"]'::jsonb);

    END
$$;


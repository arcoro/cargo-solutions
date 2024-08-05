CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS catalog (
                         id SERIAL8 PRIMARY KEY,
                         name VARCHAR(30) NOT NULL UNIQUE,
                         type VARCHAR(30) NOT NULL,
                         additional_code INT4 DEFAULT 0,
                         created_at TIMESTAMP DEFAULT now()
);

CREATE TABLE IF NOT EXISTS pages (
                       id SERIAL4 PRIMARY KEY,
                       page_type VARCHAR(10) NOT NULL,
                       icon VARCHAR(30) NOT NULL,
                       path VARCHAR(100) NOT NULL UNIQUE,
                       title VARCHAR(60) NOT NULL,
                       actions JSONB,
                       previous_menu INT4
);

CREATE TABLE IF NOT EXISTS roles (
                       id SERIAL4 PRIMARY KEY,
                       role VARCHAR(20) NOT NULL,
                       created_at TIMESTAMP DEFAULT now(),
                       updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS pages_role (
                            id SERIAL4 PRIMARY KEY,
                            page_id INT4 NOT NULL,
                            role_id INT4 NOT NULL,
                            actions JSONB,
                            CONSTRAINT fk_pages_role_page_id FOREIGN KEY (page_id) REFERENCES pages(id),
                            CONSTRAINT fk_pages_role_role_id FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE IF NOT EXISTS companies (
                           id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
                           company_name VARCHAR(80) NOT NULL,
                           identifier VARCHAR(40) NOT NULL,
                           phone VARCHAR(10) NOT NULL,
                           email VARCHAR(200) NOT NULL UNIQUE,
                           high_street VARCHAR(150) NOT NULL,
                           side_street VARCHAR(150) NOT NULL,
                           identity_type_id INT8,
                           identity VARCHAR(15) NOT NULL UNIQUE,
                           created_at TIMESTAMP DEFAULT now(),
                           updated_at TIMESTAMP,
                           CONSTRAINT fk_companies_identity_type_id FOREIGN KEY (identity_type_id) REFERENCES catalog(id)
);

CREATE TABLE IF NOT EXISTS configurations (
                                id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
                                company_id UUID NOT NULL,
                                logo VARCHAR(400) NOT NULL,
                                primary_color VARCHAR(8) NOT NULL,
                                secondary_color VARCHAR(8) NOT NULL,
                                created_at TIMESTAMP DEFAULT now(),
                                updated_at TIMESTAMP,
                                CONSTRAINT fk_configurations_company_id FOREIGN KEY (company_id) REFERENCES companies(id)
);

CREATE TABLE IF NOT EXISTS users (
                       id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
                       company_id UUID NOT NULL,
                       role_id INT4 NOT NULL,
                       name VARCHAR(30) NOT NULL UNIQUE,
                       last_name VARCHAR(30) NOT NULL UNIQUE,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       phone VARCHAR(10) NOT NULL,
                       identity_type_id INT8,
                       identity VARCHAR(15) NOT NULL UNIQUE,
                       created_at TIMESTAMP DEFAULT now(),
                       updated_at TIMESTAMP,
                       CONSTRAINT fk_users_role_id FOREIGN KEY (role_id) REFERENCES roles(id),
                       CONSTRAINT fk_users_company_id FOREIGN KEY (company_id) REFERENCES companies(id),
                       CONSTRAINT fk_users_identity_type_id FOREIGN KEY (identity_type_id) REFERENCES catalog(id)
);

CREATE TABLE IF NOT EXISTS security (
                          id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
                          user_id UUID NOT NULL,
                          password VARCHAR NOT NULL,
                          first_login TIMESTAMP,
                          last_login TIMESTAMP,
                          intents INT4 DEFAULT 0,
                          history_passwords JSONB,
                          validated boolean DEFAULT false,
                          enabled boolean DEFAULT false,
                          blocked boolean DEFAULT false,
                          password_expires_at TIMESTAMP,
                          verification_code VARCHAR(200),
                          recovery_code VARCHAR(200),
                          otp_code VARCHAR(200),
                          CONSTRAINT fk_security_user_id FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS consolidation (
                               id SERIAL8 PRIMARY KEY,
                               company_id UUID NOT NULL,
                               state INT8,
                               consolidation_name VARCHAR NOT NULL UNIQUE,
                               package_file VARCHAR,
                               manifest_file VARCHAR,
                               ingress_file VARCHAR,
                               state_file VARCHAR,
                               created_at TIMESTAMP DEFAULT now(),
                               updated_at TIMESTAMP,
                               CONSTRAINT fk_consolidation_company_id FOREIGN KEY (company_id) REFERENCES companies(id),
                               CONSTRAINT fk_consolidation_state FOREIGN KEY (state) REFERENCES catalog(id)
);

CREATE TABLE IF NOT EXISTS consolidation_state (
                                     id SERIAL4 PRIMARY KEY,
                                     consolidation_id INT8,
                                     das_state INT8,
                                     guide VARCHAR(50) NOT NULL UNIQUE,
                                     CONSTRAINT fk_consolidation_state_id FOREIGN KEY (consolidation_id) REFERENCES consolidation(id),
                                     CONSTRAINT fk_consolidation_state_das FOREIGN KEY (das_state) REFERENCES catalog(id)
);

CREATE TABLE IF NOT EXISTS packages_dispatch (
                                   id SERIAL4 PRIMARY KEY,
                                   consolidation_id INT8,
                                   import_type INT8,
                                   tracking VARCHAR(100) NOT NULL UNIQUE,
                                   guide VARCHAR(50) NOT NULL UNIQUE,
                                   payment_order INT8 NOT NULL,
                                   units INT4 DEFAULT 0,
                                   value DECIMAL DEFAULT 0,
                                   weight NUMERIC DEFAULT 0,
                                   description VARCHAR(500),
                                   phone VARCHAR(15),
                                   city_code INT4,
                                   province INT8,
                                   canton INT8,
                                   parish INT8,
                                   address VARCHAR(50),
                                   mail VARCHAR(80),
                                   identity VARCHAR(15) NOT NULL,
                                   consignee VARCHAR(80),
                                   created_at TIMESTAMP DEFAULT now(),
                                   updated_at TIMESTAMP,
                                   CONSTRAINT fk_packages_dispatch_consolidation_id FOREIGN KEY (consolidation_id) REFERENCES consolidation(id),
                                   CONSTRAINT fk_packages_dispatch_import_type FOREIGN KEY (import_type) REFERENCES catalog(id)
);

CREATE TABLE IF NOT EXISTS manifest_ecuapass (
                                   id SERIAL4 PRIMARY KEY,
                                   consolidation_id INT8,
                                   guide VARCHAR(50),
                                   master_guide VARCHAR(80),
                                   weight_kilos NUMERIC,
                                   package INT4,
                                   hsn VARCHAR(100),
                                   msn VARCHAR(100),
                                   mrn VARCHAR(300),
                                   created_at TIMESTAMP DEFAULT now(),
                                   updated_at TIMESTAMP,
                                   CONSTRAINT fk_manifest_ecuapass_consolidation_id FOREIGN KEY (consolidation_id) REFERENCES consolidation(id)
);

CREATE TABLE IF NOT EXISTS ingress_ecuapass (
                                  id SERIAL4 PRIMARY KEY,
                                  consolidation_id INT8,
                                  guide VARCHAR(50),
                                  capacity_person VARCHAR(80),
                                  type_capacity NUMERIC,
                                  das_number VARCHAR,
                                  consignee VARCHAR(100),
                                  ingress_date TIMESTAMP,
                                  ingress_weight NUMERIC,
                                  ingress_cuantity INT4,
                                  created_at TIMESTAMP DEFAULT now(),
                                  updated_at TIMESTAMP,
                                  CONSTRAINT fk_ingress_ecuapass_consolidation_id FOREIGN KEY (consolidation_id) REFERENCES consolidation(id)
);

CREATE TABLE IF NOT EXISTS state_ecuapass (
                                id SERIAL4 PRIMARY KEY,
                                consolidation_id INT8,
                                das_number VARCHAR,
                                declaration_date TIMESTAMP,
                                state INT4,
                                created_at TIMESTAMP DEFAULT now(),
                                updated_at TIMESTAMP,
                                CONSTRAINT fk_state_ecuapass_consolidation_id FOREIGN KEY (consolidation_id) REFERENCES consolidation(id)
);
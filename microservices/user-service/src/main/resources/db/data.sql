        DROP TABLE IF EXISTS users_roles;
        DROP TABLE IF EXISTS roles_perms;
        DROP TABLE IF EXISTS users;
        DROP TABLE IF EXISTS roles;
        DROP TABLE IF EXISTS perms;

        CREATE TABLE users (
          id BIGINT AUTO_INCREMENT  PRIMARY KEY,
          username VARCHAR(250) NOT NULL UNIQUE,
          password VARCHAR(250) NOT NULL,
          phone VARCHAR(11) DEFAULT NULL UNIQUE,
          email VARCHAR(50) DEFAULT NULL UNIQUE,
          enabled BOOLEAN NOT NULL
        );

        INSERT INTO users (username, password, enabled) VALUES
          ('admin', '$2a$10$184zbFuYbgB5Ou8okgUw3OqMmhh.jQXWRZ96xKk1lY6GmW4R8mGZK', true),
          ('user', '$2a$10$184zbFuYbgB5Ou8okgUw3OqMmhh.jQXWRZ96xKk1lY6GmW4R8mGZK', true),
          ('guest', '$2a$10$184zbFuYbgB5Ou8okgUw3OqMmhh.jQXWRZ96xKk1lY6GmW4R8mGZK', true);

        CREATE TABLE roles (
          id BIGINT AUTO_INCREMENT  PRIMARY KEY,
          name VARCHAR(250) NOT NULL
        );

        INSERT INTO roles (name) VALUES
          ('USER'),
          ('ADMIN'),
          ('MANAGER');


        CREATE TABLE users_roles (
          user_id BIGINT NOT NULL,
          role_id BIGINT NOT NULL,
          FOREIGN KEY(user_id) REFERENCES users(id),
          FOREIGN KEY(role_id) REFERENCES roles(id),
          PRIMARY KEY (user_id, role_id)
        );

        INSERT INTO users_roles (user_id, role_id) VALUES
          (1, 2),
          (2, 1),
          (2, 2),
          (2, 3),
          (3, 1);

        CREATE TABLE perms (
          id BIGINT AUTO_INCREMENT  PRIMARY KEY,
          name VARCHAR(250) NOT NULL
        );

        INSERT INTO perms (name) VALUES
          ('product:read'),
          ('product:write');

        CREATE TABLE roles_perms (
          role_id BIGINT NOT NULL,
          perm_id BIGINT NOT NULL,
          FOREIGN KEY(role_id) REFERENCES roles(id),
          FOREIGN KEY(perm_id) REFERENCES perms(id),
          PRIMARY KEY (role_id, perm_id)
        );

        INSERT INTO roles_perms (role_id, perm_id) VALUES
          (1, 1),
          (2, 1),
          (2, 2),
          (3, 1),
          (3, 2);
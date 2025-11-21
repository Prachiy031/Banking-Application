CREATE DATABASE keycloak CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE USER 'kcuser'@'localhost' IDENTIFIED BY 'kcpass';

GRANT ALL PRIVILEGES ON keycloak.* TO 'kcuser'@'localhost';

FLUSH PRIVILEGES;



CREATE USER IF NOT EXISTS root@'%' IDENTIFIED BY 'password';
SET PASSWORD FOR root@'%' = PASSWORD('password');
GRANT ALL ON *.* TO root@'%' WITH GRANT OPTION;


CREATE DATABASE IF NOT EXISTS api_db;
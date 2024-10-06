-- 계정 생성 후 데이터베이스 활용

-- 1) 새로운 계정 만들기
CREATE USER 'ohgiraffers'@'%' IDENTIFIED BY  'ohgiraffers';

-- 현재 존재하는 데이터베이스 확인
SHOW databases;

-- mysql 데이터베이스로 계정 정보 확인하기
USE mysql;	

SELECT * FROM user;	

-- 2) 데이터베이스 생성 후 계정에 권한 부여
-- 데이터베이스(스키마) 생성
CREATE DATABASE monsterdb;
-- CREATE SCHEMA menudb;

GRANT ALL PRIVILEGES ON monsterdb.* TO 'ohgiraffers'@'%';

SHOW GRANTS FOR 'ohgiraffers'@'%';

USE menudb;
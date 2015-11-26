--
-- PostgreSQL database dump
--

-- Dumped from database version 9.0.15
-- Dumped by pg_dump version 9.0.15
-- Started on 2014-04-03 21:54:02

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

CREATE ROLE jenkins LOGIN
  ENCRYPTED PASSWORD 'md53e61f7dcabc29ed8ac2be6bd03a0c388'
  SUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;

CREATE DATABASE flygow
  WITH OWNER = jenkins
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'pt_BR.UTF-8'
       LC_CTYPE = 'pt_BR.UTF-8'
       CONNECTION LIMIT = -1;



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

-- tabela dual para testes
create table dual();

--
-- TOC entry 1926 (class 0 OID 41140)
-- Dependencies: 186
-- Data for Name: attendant; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO attendant (id, address, birthdate, email, lastname, login, name, password, photoname, registrationdate) VALUES (1, 'DEFAULT ADDRESS', '1990-07-06', 'admin@admin.com', 'Flygow', 'admin', 'Admin', 'LCs0WPZpzkg2UYzLyi/Qqg==', null, now());


--
-- TOC entry 1925 (class 0 OID 32791)
-- Dependencies: 180
-- Data for Name: module; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO "module" (id, active, name, version, iconcls) VALUES (1, true, 'Registrations', '1', 'registration-icon');
INSERT INTO "module" (id, active, name, version, iconcls) VALUES (2, true, 'Orders', '1', 'order-icon');
INSERT INTO "module" (id, active, name, version, iconcls) VALUES (3, true, 'ControlPanel', '1', 'control-panel-icon');


--
-- TOC entry 1927 (class 0 OID 49158)
-- Dependencies: 190
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO role (id, description, name) VALUES (1, 'Authenticated Role', 'Authenticated');
INSERT INTO role (id, description, name) VALUES (2, 'Admin', 'Admin');

INSERT INTO role_module (roleid, moduleid) VALUES (2, 1);
INSERT INTO role_module (roleid, moduleid) VALUES (2, 2);
INSERT INTO role_module (roleid, moduleid) VALUES (2, 3);

INSERT INTO attendant_role (attendant_id, role_id) VALUES (1, 1);
INSERT INTO attendant_role (attendant_id, role_id) VALUES (1, 2);

-- INSERT ENUMS

INSERT INTO alertmessagetype (id, description, name) VALUES (1, 'To Payment', 'Attendant being called to request PAYMENT on the table');
INSERT INTO alertmessagetype (id, description, name) VALUES (2, 'To Attendance', 'Attendant being called to request SERVICE on the table');

INSERT INTO alertmessagestatus (id, description, name) VALUES (1, 'Opened', 'Alert opened');
INSERT INTO alertmessagestatus (id, description, name) VALUES (2, 'Attended', 'Alert attended');

INSERT INTO orderitemstatus (id, description, name) VALUES (1, 'In Attendance', 'In Attendance');
INSERT INTO orderitemstatus (id, description, name) VALUES (2, 'Accept', 'Accept');
INSERT INTO orderitemstatus (id, description, name) VALUES (3, 'Cancel', 'Cancel');

INSERT INTO tabletservicestatus (id, description, name) VALUES (1, 'In Attendance', 'In Attendance');
INSERT INTO tabletservicestatus (id, description, name) VALUES (2, 'Available', 'Available');
INSERT INTO tabletservicestatus (id, description, name) VALUES (3, 'Unavailable', 'Unavailable');

INSERT INTO orderstatus (id, description, name) VALUES (1, 'When Order is Open',  'Open');
INSERT INTO orderstatus (id, description, name) VALUES (2, 'When Order is Closed', 'Closed');
INSERT INTO orderstatus (id, description, name) VALUES (3, 'When Order is Finalized', 'Finalized');
INSERT INTO orderstatus (id, description, name) VALUES (4, 'When Order is Canceled', 'Canceled');

-- Completed on 2014-04-03 21:54:04

--
-- PostgreSQL database dump complete
--


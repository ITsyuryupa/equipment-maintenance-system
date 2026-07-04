-- =========================
-- EQUIPMENT TYPES
-- =========================
TRUNCATE TABLE equipment.equipment RESTART IDENTITY CASCADE;
TRUNCATE TABLE equipment.equipment_type RESTART IDENTITY CASCADE;

INSERT INTO equipment.equipment_type
(id, name, description, manufacturer, maintenance_interval_days, created_at, updated_at, deleted)
VALUES
    (1, 'Сервер', 'Серверное оборудование', 'Dell', 30, now(), now(), false),
    (2, 'Роутер', 'Сетевое оборудование', 'Cisco', 60, now(), now(), false),
    (3, 'Ноутбук', 'Рабочие ноутбуки сотрудников', 'HP', 90, now(), now(), false),
    (4, 'Принтер', 'Офисные принтеры', 'Canon', 120, now(), now(), false);


-- =========================
-- EQUIPMENT
-- =========================

INSERT INTO equipment.equipment
(id, equipment_type_id, name, inventory_number, location, status, created_at, decommissioned_at, deleted)
VALUES
    (1, 1, 'Главный сервер', 'INV-1001', 'МСК ДЦ', 'AVAILABLE', now(), null, false),

    (2, 1, 'Резервный сервер', 'INV-1002', 'МСК ДЦ', 'UNDER_MAINTENANCE', now(), null, false),

    (3, 2, 'Core Router', 'INV-2001', 'Офис 1', 'AVAILABLE', now(), null, false),

    (4, 3, 'Dev Laptop', 'INV-3001', 'Офис 2', 'AVAILABLE', now(), null, false),

    (5, 4, 'Printer HP', 'INV-4001', 'Офис 1', 'DECOMMISSIONED', now(), now(), false);
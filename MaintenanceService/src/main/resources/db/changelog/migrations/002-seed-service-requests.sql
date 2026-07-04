INSERT INTO maintenance.service_request
(id, equipment_id, title, description, status, priority, created_at, updated_at, completed_at, deleted)
VALUES
    (1, 1, 'Не работает сервер', 'Сервер не отвечает на запросы', 'NEW', 'HIGH', now(), now(), null, false),

    (2, 2, 'Плановое обслуживание', 'Провести профилактику оборудования', 'IN_PROGRESS', 'MEDIUM', now(), now(), null, false),

    (3, 3, 'Проблемы с сетью', 'Периодические потери связи', 'NEW', 'HIGH', now(), now(), null, false),

    (4, 4, 'Обновление ПО', 'Обновить ПО на ноутбуке', 'DONE', 'LOW', now(), now(), now(), false),

    (5, 5, 'Списание оборудования', 'Оборудование неисправно и списано', 'CANCELLED', 'LOW', now(), now(), null, false);
-- Устанавливаем sequence transports_id_seq на MAX(id) из таблицы transports
SELECT setval('transports_id_seq', (SELECT COALESCE(MAX(id), 1) FROM transports));
SELECT setval('notices_id_seq', (SELECT COALESCE(MAX(id), 1) FROM notices));
SELECT setval('stops_id_seq', (SELECT COALESCE(MAX(id), 1) FROM stops));
SELECT setval('museums_id_seq', (SELECT COALESCE(MAX(id), 1) FROM museums));
SELECT setval('complaints_id_seq', (SELECT COALESCE(MAX(id), 1) FROM complaints));
SELECT setval('users_id_seq', (SELECT COALESCE(MAX(id), 1) FROM users));
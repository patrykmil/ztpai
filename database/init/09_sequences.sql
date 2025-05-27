--------------------------------------------------------------------------------------------------
SELECT setval(pg_get_serial_sequence('avatar', 'avatar_id'), coalesce(max(avatar_id) + 1, 1), false)
FROM avatar;
SELECT setval(pg_get_serial_sequence('iuser', 'user_id'), coalesce(max(user_id) + 1, 1), false)
FROM iuser;
SELECT setval(pg_get_serial_sequence('type', 'type_id'), coalesce(max(type_id) + 1, 1), false)
FROM type;
SELECT setval(pg_get_serial_sequence('color', 'color_id'), coalesce(max(color_id) + 1, 1), false)
FROM color;
SELECT setval(pg_get_serial_sequence('set', 'set_id'), coalesce(max(set_id) + 1, 1), false)
FROM set;
SELECT setval(pg_get_serial_sequence('component', 'component_id'), coalesce(max(component_id) + 1, 1), false)
FROM component;
SELECT setval(pg_get_serial_sequence('tag', 'tag_id'), coalesce(max(tag_id) + 1, 1), false)
FROM tag;
--------------------------------------------------------------------------------------------------
create view component_details_view
            (component_id, component_name, user_id, username, hex, type_name, set_name, created_at, likes, tags, css,
             html)
as
SELECT c.component_id,
       c.component_name,
       c.user_id,
       u.username,
       co.hex,
       t.type_name                              AS typename,
       s.set_name                               AS setname,
       c.created_at,
       (SELECT count(*) AS count
        FROM "liked" l
        WHERE l.component_id = c.component_id)  AS likes,
       (SELECT jsonb_object_agg(tg.tag_name, co2.hex)
        FROM component_tag ct
                 JOIN tag tg USING (tag_id)
                 JOIN color co2 ON tg.color_id = co2.color_id
        WHERE ct.component_id = c.component_id) AS tags,
       c.css,
       c.html
FROM component c
         LEFT JOIN color co USING (color_id)
         LEFT JOIN set s USING (set_id)
         LEFT JOIN type t USING (type_id)
         LEFT JOIN iuser u ON c.user_id = u.user_id;
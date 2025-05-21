DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;

--------------------------------------------------------------------------------------------------
CREATE TABLE "public"."avatar" (
    "avatar_id" serial,
    "avatar_path" character varying(255) NOT NULL,
    PRIMARY KEY ("avatar_id")
);
--------------------------------------------------------------------------------------------------
CREATE TABLE "public"."iuser" (
  "user_id" serial,
  "email" character varying(30) NOT NULL UNIQUE,
  "username" character varying(30) NOT NULL,
  "password_hash" character varying(65) NOT NULL,
  "avatar_id" integer,
  "admin" boolean DEFAULT false,
  PRIMARY KEY ("user_id")
);

CREATE UNIQUE INDEX "iuser_iuser_email_key"
    ON "public"."iuser" ("email");

CREATE INDEX "iuser_idx_iuser_avatar_id"
    ON "public"."iuser" ("avatar_id");
--------------------------------------------------------------------------------------------------
CREATE TABLE "public"."set" (
    "set_id" serial,
    "set_name" character varying(30) NOT NULL,
    "user_id" integer,
    PRIMARY KEY ("set_id")
);

CREATE INDEX "set_idx_set_user_id"
    ON "public"."set" ("user_id");
--------------------------------------------------------------------------------------------------
CREATE TABLE "public"."type" (
    "type_id" serial,
    "type_name" character varying(30) NOT NULL,
    PRIMARY KEY ("type_id")
);
--------------------------------------------------------------------------------------------------
CREATE TABLE "public"."color" (
    "color_id" serial,
    "hex" character varying(6) NOT NULL,
    PRIMARY KEY ("color_id")
);
--------------------------------------------------------------------------------------------------
CREATE TABLE "public"."component" (
    "component_id" serial,
    "component_name" character varying(30) NOT NULL,
    "set_id" integer,
    "type_id" integer,
    "color_id" integer,
    "user_id" integer,
    "created_at" timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    "html" text,
    "css" text,
    PRIMARY KEY ("component_id")
);

CREATE INDEX "component_idx_component_set_id"
    ON "public"."component" ("set_id");

CREATE INDEX "component_idx_component_type_id"
    ON "public"."component" ("type_id");

CREATE INDEX "component_idx_component_color_id"
    ON "public"."component" ("color_id");

CREATE INDEX "component_idx_component_user_id"
    ON "public"."component" ("user_id");
--------------------------------------------------------------------------------------------------
CREATE TABLE "public"."tag" (
    "tag_id" serial,
    "tag_name" character varying(30) NOT NULL UNIQUE,
    "color_id" integer,
    PRIMARY KEY ("tag_id")
);

CREATE UNIQUE INDEX "tag_tag_tag_name_key"
    ON "public"."tag" ("tag_name");
--------------------------------------------------------------------------------------------------
CREATE TABLE "public"."component_tag" (
    "component_id" integer NOT NULL,
    "tag_id" integer NOT NULL,
    PRIMARY KEY ("component_id", "tag_id")
);
--------------------------------------------------------------------------------------------------
CREATE TABLE "public"."liked" (
    "user_id" integer NOT NULL,
    "component_id" integer NOT NULL,
    "liked_at" timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY ("user_id", "component_id")
);

CREATE INDEX "liked_idx_liked_component_id"
    ON "public"."liked" ("component_id");
--------------------------------------------------------------------------------------------------
CREATE TABLE "public"."message" (
    "message_id" serial,
    "user_id" integer NOT NULL,
    "title" character varying(80),
    "body" character varying(300),
    "link" character varying(40),
    "created_at" timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY ("message_id")
);

CREATE INDEX "message_idx_message_user_id"
    ON "public"."message" ("user_id");
--------------------------------------------------------------------------------------------------Insert HEX in uppercase trigger
CREATE OR REPLACE FUNCTION enforce_uppercase_hex()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.hex := UPPER(NEW.hex);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER enforce_uppercase_hex_trigger
    BEFORE INSERT OR UPDATE ON public.color
    FOR EACH ROW
EXECUTE FUNCTION enforce_uppercase_hex();
--------------------------------------------------------------------------------------------------Avatar inserts
INSERT INTO public.avatar (avatar_id, avatar_path)
VALUES (1, 'basic_green.svg');
INSERT INTO public.avatar (avatar_id, avatar_path)
VALUES (2, 'basic_orange.svg');
INSERT INTO public.avatar (avatar_id, avatar_path)
VALUES (3, 'basic_purple.svg');
INSERT INTO public.avatar (avatar_id, avatar_path)
VALUES (4, 'glasses_green.svg');
INSERT INTO public.avatar (avatar_id, avatar_path)
VALUES (5, 'glasses_orange.svg');
INSERT INTO public.avatar (avatar_id, avatar_path)
VALUES (6, 'glasses_purple.svg');
INSERT INTO public.avatar (avatar_id, avatar_path)
VALUES (7, 'hair_green.svg');
INSERT INTO public.avatar (avatar_id, avatar_path)
VALUES (8, 'hair_orange.svg');
INSERT INTO public.avatar (avatar_id, avatar_path)
VALUES (9, 'hair_purple.svg');
--------------------------------------------------------------------------------------------------User inserts
INSERT INTO public.iuser (user_id, email, username, password_hash, avatar_id, admin)
VALUES (1, 'iuadmin@iu.iu', 'admin1', '$2y$12$9cqkVFs2HxdhzN0ZPp8/1uudWjvDZT5YJVI4euVIUJjgPazullhtm', 3, true);
INSERT INTO public.iuser (user_id, email, username, password_hash, avatar_id)
VALUES (2, 'patryk@gmail.com', 'patryk', '$2a$12$AO9m5bhM66mJ6VYJmpcnDeyiB14EmM1FyDhP5plk6wAvSwD0YEirm', 1);
INSERT INTO public.iuser (user_id, email, username, password_hash, avatar_id)
VALUES (3, 'none@proton.me', 'none', '$2y$12$d/MJQW2V0jQkYbLLJqZRpOHyH8Fcs4Bqh78i17oqhAASJjPEInV5K', 8);
--------------------------------------------------------------------------------------------------Type inserts
INSERT INTO public.type (type_id, type_name)
VALUES (1, 'button');
INSERT INTO public.type (type_id, type_name)
VALUES (2, 'input');
INSERT INTO public.type (type_id, type_name)
VALUES (3, 'checkbox');
INSERT INTO public.type (type_id, type_name)
VALUES (4, 'radio button');
--------------------------------------------------------------------------------------------------Color inserts
INSERT INTO public.color (color_id, hex)
VALUES (1, '00F0FF');
INSERT INTO public.color (color_id, hex)
VALUES (2, '13A823');
INSERT INTO public.color (color_id, hex)
VALUES (3, 'FFFFFF');
INSERT INTO public.color (color_id, hex)
VALUES (4, 'FF5733');
INSERT INTO public.color (color_id, hex)
VALUES (5, 'C70039');
INSERT INTO public.color (color_id, hex)
VALUES (6, '900C3F');
INSERT INTO public.color (color_id, hex)
VALUES (7, '581845');
INSERT INTO public.color (color_id, hex)
VALUES (8, 'DAF7A6');
INSERT INTO public.color (color_id, hex)
VALUES (9, '9f29e5');
INSERT INTO public.color (color_id, hex)
VALUES (10, 'd43ad4');
INSERT INTO public.color (color_id, hex)
VALUES (11, '6a5ed3');
INSERT INTO public.color (color_id, hex)
VALUES (12, '5ed398');
--------------------------------------------------------------------------------------------------Set inserts
INSERT INTO public.set (set_id, set_name, user_id)
VALUES (1, 'gradient', 3);
INSERT INTO public.set (set_id, set_name, user_id)
VALUES (2, 'others', 3);
INSERT INTO public.set (set_id, set_name, user_id)
VALUES (3, 'easy', 2);
--------------------------------------------------------------------------------------------------Tag inserts
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (1, 'simple', 1);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (2, 'fancy', 2);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (3, 'colorless', 3);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (4, 'responsive', 4);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (5, 'animated', 5);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (6, 'modest', 6);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (7, 'modern', 8);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (8, 'classic', 8);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (9, 'dark', 7);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (10, 'light', 2);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (11, 'flat', 3);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (12, 'gradient', 4);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (13, 'shadow', 5);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (14, 'bordered', 6);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (15, 'rounded', 7);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (16, 'outlined', 8);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (17, 'transparent', 1);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (18, 'solid', 2);
--------------------------------------------------------------------------------------------------Component inserts
INSERT INTO public.component (component_id, component_name, set_id, type_id, color_id, user_id, created_at, css, html)
VALUES (1, 'gradient text', 1, 2, 9, 1, '2025-03-02 14:00:50.977333',
e'.gradient-border {
    border: 2px solid transparent;
    border-radius: 5px;
    background-image: linear-gradient(white, white), linear-gradient(45deg, rgb(100, 61, 219) 0%, rgb(217, 21, 239) 100%);
    background-origin: border-box;
    background-clip: content-box, border-box;
    padding: 5px;
    font-size: 16px;
    font-weight: bold;
    color: transparent;
    -webkit-background-clip: text;
    background-clip: text;
    background-image: linear-gradient(45deg, rgb(100, 61, 219) 0%, rgb(217, 21, 239) 100%);
}',
'&lt;input type=&quot;text&quot; class=&quot;gradient-border&quot; placeholder=&quot;Enter text here&quot;&gt;');
--------------------------------------------------------------------------------------------------
INSERT INTO public.component (component_id, component_name, set_id, type_id, color_id, user_id, created_at, css, html)
VALUES (2, 'pink submit', 3, 1, 10, 2, '2025-03-12 13:5:00.663748',
e'#submit-button {
    display: inline-flex;
    font-family: &quot;Segoe UI&quot;, Tahoma, Geneva, Verdana, sans-serif;
    width: 7.5em;
    height: 2em;
    font-size: 2rem;
    font-weight: 500;
    padding: 0.8rem 0.8rem;
    justify-content: center;
    align-items: center;
    gap: 0.625rem;
    border-radius: 0.8125rem;
    color: rgb(212, 58, 212);
    border: 3px solid rgb(212, 58, 212);
    background-color: inherit;
}

#submit-button:hover {
    background-color: rgb(212, 58, 212);
    color: #fff;
    cursor: pointer;
    border-color: #ffffff;
}',
'&lt;button id=&quot;submit-button&quot;&gt;Submit&lt;/button&gt;');
--------------------------------------------------------------------------------------------------
INSERT INTO public.component (component_id, component_name, set_id, type_id, color_id, user_id, created_at, css, html)
VALUES (3, 'purple submit', 3, 1, 11, 2, '2025-03-03 12:48:5.729399',
e'#submit-button {
    display: inline-flex;
    font-family: &quot;Segoe UI&quot;, Tahoma, Geneva, Verdana, sans-serif;
    width: 7.5em;
    height: 2em;
    font-size: 2rem;
    font-weight: 500;
    padding: 0.8rem 0.8rem;
    justify-content: center;
    align-items: center;
    gap: 0.625rem;
    border-radius: 0.8125rem;
    color: #6a5ed3;
    border: 3px solid #6a5ed3;
    background-color: inherit;
}

#submit-button:hover {
    background-color: #6a5ed3;
    color: #fff;
    cursor: pointer;
    border-color: #ffffff;
}',
'&lt;button id=&quot;submit-button&quot;&gt;Submit&lt;/button&gt;');
--------------------------------------------------------------------------------------------------
INSERT INTO public.component (component_id, component_name, set_id, type_id, color_id, user_id, created_at, css, html)
VALUES (4, 'green submit', 3, 1, 12, 2, '2025-03-10 12:50:12.928597',
e'#submit-button {
    display: inline-flex;
    font-family: &quot;Segoe UI&quot;, Tahoma, Geneva, Verdana, sans-serif;
    width: 7.5em;
    height: 2em;
    font-size: 2rem;
    font-weight: 500;
    padding: 0.8rem 0.8rem;
    justify-content: center;
    align-items: center;
    gap: 0.625rem;
    border-radius: 0.8125rem;
    color: #5ed398;
    border: 3px solid #5ed398;
    background-color: inherit;
}

#submit-button:hover {
    background-color: #5ed398;
    color: #000000;
    cursor: pointer;
    border-color: #000000;
}',
'&lt;button id=&quot;submit-button&quot;&gt;Submit&lt;/button&gt;');
--------------------------------------------------------------------------------------------------
INSERT INTO public.component (component_id, component_name, set_id, type_id, color_id, user_id, created_at, css, html)
VALUES (5, 'green input', 3, 2, 12, 2, '2025-02-23 12:57:35.674594',
e'#search_input {
    display: inline-flex;
    font-family: &quot;Segoe UI&quot;, Tahoma, Geneva, Verdana, sans-serif;
    width: 7.5em;
    height: 2em;
    font-size: 1rem;
    font-weight: 500;
    padding: 0.1rem 0.8rem;
    justify-content: center;
    align-items: center;
    gap: 0.625rem;
    border-radius: 0.8125rem;
    color: #5ed398;
    border: 3px solid #5ed398;
    background-color: inherit;
}

#search_input:focus {
    outline: none;
    border: 3px solid #5ed398;
}',
'&lt;input component_name=&quot;text&quot; id=&quot;search_input&quot; type=&quot;text&quot; placeholder=&quot;Search&quot; /&gt;');
--------------------------------------------------------------------------------------------------Likes inserts
INSERT INTO public.liked (user_id, component_id, liked_at)
VALUES (2, 5, '2025-03-07 15:48:30.070802');
INSERT INTO public.liked (user_id, component_id, liked_at)
VALUES (1, 1, '2025-03-07 16:32:57.245697');
INSERT INTO public.liked (user_id, component_id, liked_at)
VALUES (1, 5, '2025-03-07 16:32:57.806962');
INSERT INTO public.liked (user_id, component_id, liked_at)
VALUES (2, 2, '2025-03-08 16:05:15.519838');
INSERT INTO public.liked (user_id, component_id, liked_at)
VALUES (2, 1, '2025-03-11 18:52:07.011277');
INSERT INTO public.liked (user_id, component_id, liked_at)
VALUES (2, 4, '2025-03-01 18:32:24.644146');
--------------------------------------------------------------------------------------------------Component_Tag inserts
INSERT INTO public.component_tag (component_id, tag_id)
VALUES (1, 12);
INSERT INTO public.component_tag (component_id, tag_id)
VALUES (1, 2);
INSERT INTO public.component_tag (component_id, tag_id)
VALUES (1, 7);
INSERT INTO public.component_tag (component_id, tag_id)
VALUES (2, 6);
INSERT INTO public.component_tag (component_id, tag_id)
VALUES (2, 13);
INSERT INTO public.component_tag (component_id, tag_id)
VALUES (2, 3);
INSERT INTO public.component_tag (component_id, tag_id)
VALUES (3, 9);
INSERT INTO public.component_tag (component_id, tag_id)
VALUES (3, 1);
INSERT INTO public.component_tag (component_id, tag_id)
VALUES (4, 9);
INSERT INTO public.component_tag (component_id, tag_id)
VALUES (4, 1);
INSERT INTO public.component_tag (component_id, tag_id)
VALUES (5, 12);
INSERT INTO public.component_tag (component_id, tag_id)
VALUES (5, 9);
INSERT INTO public.component_tag (component_id, tag_id)
VALUES (5, 1);

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
       (SELECT jsonb_object_agg(tg.tag_name, co.hex)
        FROM component_tag ct
                 JOIN tag tg USING (tag_id)
                 JOIN color co USING (color_id)
        WHERE ct.component_id = c.component_id) AS tags,
       c.css,
       c.html
FROM component c
         LEFT JOIN color co USING (color_id)
         LEFT JOIN set s USING (set_id)
         LEFT JOIN type t USING (type_id)
         LEFT JOIN iuser u ON c.user_id = u.user_id;
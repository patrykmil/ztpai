DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;

CREATE TABLE iuser
(
    user_id       SERIAL PRIMARY KEY,
    email         VARCHAR(255) UNIQUE NOT NULL,
    username      VARCHAR(255)        NOT NULL,
    password_hash VARCHAR(255)        NOT NULL,
    admin         BOOLEAN DEFAULT FALSE
);
--------------------------------------------------------------------------------------------------
CREATE TABLE set
(
    set_id   SERIAL PRIMARY KEY,
    set_name VARCHAR(30) NOT NULL,
    user_id  INT         REFERENCES iuser (user_id) ON DELETE SET NULL
);
--------------------------------------------------------------------------------------------------
CREATE TABLE type
(
    type_id   SERIAL PRIMARY KEY,
    type_name VARCHAR(30) NOT NULL
);
--------------------------------------------------------------------------------------------------
CREATE TABLE color
(
    color_id SERIAL PRIMARY KEY,
    hex      VARCHAR(6) NOT NULL
);
--------------------------------------------------------------------------------------------------
CREATE TABLE component
(
    component_id   SERIAL PRIMARY KEY,
    component_name VARCHAR(30) NOT NULL,
    set_id         INT         REFERENCES set (set_id) ON DELETE SET NULL,
    type_id        INT REFERENCES type (type_id) ON DELETE CASCADE,
    color_id       INT         REFERENCES color (color_id) ON DELETE SET NULL,
    user_id        INT REFERENCES iuser (user_id) ON DELETE CASCADE,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    html           TEXT,
    css            TEXT
);

--------------------------------------------------------------------------------------------------User inserts
INSERT INTO public.iuser (user_id, email, username, password_hash, admin)
VALUES (1, 'iuadmin@iu.iu', 'admin1', '$2y$12$9cqkVFs2HxdhzN0ZPp8/1uudWjvDZT5YJVI4euVIUJjgPazullhtm', true);
INSERT INTO public.iuser (user_id, email, username, password_hash)
VALUES (2, 'patryk@gmail.com', 'patryk', '$2a$12$AO9m5bhM66mJ6VYJmpcnDeyiB14EmM1FyDhP5plk6wAvSwD0YEirm');
INSERT INTO public.iuser (user_id, email, username, password_hash)
VALUES (3, 'none@proton.me', 'none', '$2y$12$d/MJQW2V0jQkYbLLJqZRpOHyH8Fcs4Bqh78i17oqhAASJjPEInV5K');
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

--------------------------------------------------------------------------------------------------Component inserts
INSERT INTO public.component (component_id, component_name, set_id, type_id, color_id, user_id, created_at, css, html)
VALUES (1, 'gradient text', 1, 2, 9, 1, '2025-01-12 14:00:50.977333',
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
VALUES (2, 'pink submit', 3, 1, 10, 2, '2025-03-12 13:54:00.663748',
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
VALUES (3, 'purple submit', 3, 1, 11, 2, '2025-01-23 12:48:54.729399',
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
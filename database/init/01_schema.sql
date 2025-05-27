DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;

CREATE TABLE avatar (
    avatar_id   SERIAL PRIMARY KEY,
    avatar_path VARCHAR(255) NOT NULL
);

CREATE TABLE iuser (
    user_id       SERIAL PRIMARY KEY,
    email         VARCHAR(50) UNIQUE NOT NULL,
    username      VARCHAR(30) UNIQUE NOT NULL,
    password_hash VARCHAR(65) NOT NULL,
    avatar_id     INT,
    admin         BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (avatar_id) REFERENCES avatar(avatar_id) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE set (
    set_id   SERIAL PRIMARY KEY,
    set_name VARCHAR(30) NOT NULL,
    user_id  INT,
    FOREIGN KEY (user_id) REFERENCES iuser(user_id) ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT set_user_name_unique UNIQUE (user_id, set_name)
);

CREATE TABLE type (
    type_id   SERIAL PRIMARY KEY,
    type_name VARCHAR(30) NOT NULL
);

CREATE TABLE color (
    color_id SERIAL PRIMARY KEY,
    hex      VARCHAR(6) NOT NULL
);

CREATE TABLE component (
    component_id   SERIAL PRIMARY KEY,
    component_name VARCHAR(30) NOT NULL,
    set_id        INT,
    type_id       INT NOT NULL,
    color_id      INT,
    user_id       INT NOT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    html          TEXT,
    css           TEXT,
    FOREIGN KEY (set_id) REFERENCES set(set_id) ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (type_id) REFERENCES type(type_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (color_id) REFERENCES color(color_id) ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (user_id) REFERENCES iuser(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE tag (
    tag_id   SERIAL PRIMARY KEY,
    tag_name VARCHAR(30) UNIQUE NOT NULL,
    color_id INT,
    FOREIGN KEY (color_id) REFERENCES color(color_id) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE component_tag (
    component_id INT NOT NULL,
    tag_id      INT NOT NULL,
    PRIMARY KEY (component_id, tag_id),
    FOREIGN KEY (component_id) REFERENCES component(component_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag(tag_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE liked (
    user_id      INT NOT NULL,
    component_id INT NOT NULL,
    liked_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, component_id),
    FOREIGN KEY (user_id) REFERENCES iuser(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (component_id) REFERENCES component(component_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE message (
    message_id SERIAL PRIMARY KEY,
    user_id    INT NOT NULL,
    title      VARCHAR(80),
    body       VARCHAR(300),
    link       VARCHAR(40),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES iuser(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);
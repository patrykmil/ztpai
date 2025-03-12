DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;

CREATE TABLE "User"
(
    userID       SERIAL PRIMARY KEY,
    email        VARCHAR(255) UNIQUE NOT NULL,
    nickname     VARCHAR(255)        NOT NULL,
    passwordHash VARCHAR(255)        NOT NULL,
    isAdmin      BOOLEAN DEFAULT FALSE
);

CREATE TABLE "Type"
(
    typeID SERIAL PRIMARY KEY,
    name   VARCHAR(30) NOT NULL
);

CREATE TABLE "Component"
(
    componentID SERIAL PRIMARY KEY,
    name        VARCHAR(30) NOT NULL,
    typeID      INT REFERENCES "Type" (typeID) ON DELETE CASCADE,
    css         TEXT,
    html        TEXT,
    authorID    INT REFERENCES "User" (userID) ON DELETE CASCADE,
    createdAt   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


INSERT INTO public."User" (userid, email, nickname, passwordhash, isadmin)
VALUES (1, 'iuadmin@iu.iu', 'admin1', '$2y$12$9cqkVFs2HxdhzN0ZPp8/1uudWjvDZT5YJVI4euVIUJjgPazullhtm', true);
INSERT INTO public."User" (userid, email, nickname, passwordhash)
VALUES (2, 'patryk@gmail.com', 'patryk', '$2a$12$AO9m5bhM66mJ6VYJmpcnDeyiB14EmM1FyDhP5plk6wAvSwD0YEirm');
INSERT INTO public."User" (userid, email, nickname, passwordhash)
VALUES (4, 'none@proton.me', 'none', '$2y$12$d/MJQW2V0jQkYbLLJqZRpOHyH8Fcs4Bqh78i17oqhAASJjPEInV5K');


INSERT INTO public."Type" (typeid, name)
VALUES (1, 'button');
INSERT INTO public."Type" (typeid, name)
VALUES (2, 'input');
INSERT INTO public."Type" (typeid, name)
VALUES (3, 'checkbox');
INSERT INTO public."Type" (typeid, name)
VALUES (4, 'radio button');

INSERT INTO public."Component" (componentid, name, typeid, css, html, authorid, createdat)
VALUES (1, 'purple check', 3, e'.container input {
  position: absolute;
  opacity: 0;
  cursor: pointer;
  height: 0;
  width: 0;
  border-radius: 5px;
}

.container {
  display: block;
  position: relative;
  cursor: pointer;
  font-size: 20px;
  user-select: none;
  border-radius: 5px;
  box-shadow: 2px 2px 0px rgb(183, 183, 183);
}

.checkmark {
  position: relative;
  top: 0;
  left: 0;
  height: 1.3em;
  width: 1.3em;
  background-color: #ccc;
  border-radius: 5px;
}

.container input:checked ~ .checkmark {
  box-shadow: 3px 3px 0px rgb(183, 183, 183);
  transition: all 0.2s;
  opacity: 1;
  background-image: linear-gradient(45deg, rgb(100, 61, 219) 0%, rgb(217, 21, 239) 100%);
}

.container input ~ .checkmark {
  transition: all 0.2s;
  opacity: 1;
  box-shadow: 1px 1px 0px rgb(183, 183, 183);
}

.checkmark:after {
  content: &quot;&quot;;
  position: absolute;
  opacity: 0;
  transition: all 0.2s;
}

.container input:checked ~ .checkmark:after {
  opacity: 1;
  transition: all 0.2s;
}

.container .checkmark:after {
  left: 0.45em;
  top: 0.25em;
  width: 0.25em;
  height: 0.5em;
  border: solid white;
  border-width: 0 0.15em 0.15em 0;
  transform: rotate(45deg);
}', e'&lt;label class=&quot;container&quot;&gt;
      &lt;input type=&quot;checkbox&quot; checked=&quot;checked&quot; /&gt;
      &lt;div class=&quot;checkmark&quot;&gt;&lt;/div&gt;
    &lt;/label&gt;', 4, '2025-03-12 13:47:04.369663');

INSERT INTO public."Component" (componentid, name, typeid, css, html, authorid, createdat)
VALUES (2, 'keycap radio button', 4,e'.btn {
  font: inherit;
  background-color: #f0f0f0;
  border: 0;
  color: #242424;
  font-size: 1.15rem;
  padding: 0.375em 1em;
  text-shadow: 0 0.0625em 0 #fff;
  box-shadow: inset 0 0.0625em 0 0 #f4f4f4, 0 0.0625em 0 0 #efefef, 0 0.125em 0 0 #ececec, 0 0.25em 0 0 #e0e0e0, 0 0.3125em 0 0 #dedede, 0 0.375em 0 0 #dcdcdc,
    0 0.425em 0 0 #cacaca, 0 0.425em 0.5em 0 #cecece;
  transition: 0.23s ease;
  cursor: pointer;
  font-weight: bold;
  margin: -1px;
}
.middle {
  border-radius: 0px;
}
.right {
  border-top-right-radius: 0.5em;
  border-bottom-right-radius: 0.5em;
}
.left {
  border-top-left-radius: 0.5em;
  border-bottom-left-radius: 0.5em;
}
.btn:active {
  translate: 0 0.225em;
  box-shadow: inset 0 0.03em 0 0 #f4f4f4, 0 0.03em 0 0 #efefef, 0 0.0625em 0 0 #ececec, 0 0.125em 0 0 #e0e0e0, 0 0.125em 0 0 #dedede, 0 0.2em 0 0 #dcdcdc, 0 0.225em 0 0 #cacaca,
    0 0.225em 0.375em 0 #cecece;
  letter-spacing: 0.1em;
  color: skyblue;
}
.btn:focus {
  color: skyblue;
}', e'&lt;div class=&quot;btn-group&quot;&gt;
      &lt;button class=&quot;btn left&quot; type=&quot;button&quot;&gt;Left&lt;/button&gt;
      &lt;button class=&quot;btn middle&quot; type=&quot;button&quot;&gt;Middle&lt;/button&gt;
      &lt;button class=&quot;btn right&quot; type=&quot;button&quot;&gt;Right&lt;/button&gt;
    &lt;/div&gt;', 4, '2025-01-12 13:49:21.140628');

INSERT INTO public."Component" (componentid, name, typeid, css, html, authorid, createdat)
VALUES (5, 'gradient text',  2,  e'.gradient-border {
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
        '&lt;input type=&quot;text&quot; class=&quot;gradient-border&quot; placeholder=&quot;Enter text here&quot;&gt;',
        4, '2025-01-12 14:00:50.977333');

INSERT INTO public."Component" (componentid, name, typeid, css, html, authorid, createdat)
VALUES (47, 'pink submit', 1, e'#submit-button {
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
}', '&lt;button id=&quot;submit-button&quot;&gt;Submit&lt;/button&gt;', 2, '2025-01-12 13:54:00.663748');
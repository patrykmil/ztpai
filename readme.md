<div style="display: flex; align-items: center;">
  <img src="react_app/public/images/logo.svg" alt="IU Components Library Logo" width="80" height="80" style="margin-right: 10px;">
  <h1>IU components library</h1>
</div>

## Table of Contents

- [Introduction](#introduction)
- [Technologies](#technologies)
- [Setup](#setup)
- [Users](#users)

## Introduction

IU - library of html/css components

## Technologies

| Function        | Used                                                                                                                                                             |
| --------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Backend         | ![Java Icon](icons/java.svg){width=45 height=45} ![Springboot Icon](icons/spring.svg){width=45 height=45} ![Rabbit Icon](icons/rabbitmq.svg){width=45 height=45} |
| Database        | ![PostgreSQL Icon](icons/postgresql.svg){width=45 height=45}                                                                                                     |
| Frontend        | ![Bun Icon](icons/bun.png){width=45 height=45} ![JavaScript Icon](icons/javascript.svg){width=45 height=45} ![React Icon](icons/react.svg){width=45 height=45}   |
| Site structure  | ![HTML Icon](icons/html.svg){width=45 height=45} ![CSS Icon](icons/css.svg){width=45 height=45}                                                                  |
| Version Control | ![Git Icon](icons/git.svg){width=45 height=45} ![GitHub Icon](icons/github.svg){width=45 height=45}                                                              |
| Contenerization | ![Docker Icon](icons/docker.svg){width=45 height=45}                                                                                                             |

## Setup

Clone repository:

```sh
  git clone https://github.com/patrykmil/ztpai.git
```

Rename .env.template and fill with your own values

Inside the project directory, run:

```sh
  docker compose up -d --build
```

| Service                   | Port  |
| ------------------------- | ----- |
| API                       | 8080  |
| Website                   | 5173  |
| Rabbitmq management panel | 15672 |
| Database                  | 5432  |

## Users

Predefined users:

- Admin 1:
  - email: iuadmin@iu.iu
  - password: adminadmin
- User 2:
  - email: patryk@gmail.com
  - password: iU4qQKZugAR6Tb
- User 3:
  - email: none@proton.me
  - password: py4369t!nM4

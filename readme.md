<div style="display: flex; align-items: center;">
  <img src="react_app/public/images/logo.svg" alt="IU Components Library Logo" width="80" height="80" style="margin-right: 10px;">
  <h1>IU components library</h1>
</div>

## Table of Contents

- [Introduction](#introduction)
- [Technologies](#technologies)
- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [Structure](#structure)
- [Users](#users)

## Introduction

IU - library of html/css components

## Technologies

| Function        | Used                                                                                                                                                                                                  |
|-----------------| ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Backend         | <img src="readme_assets/icons/java.svg" width="45" height="45"> <img src="readme_assets/icons/spring.svg" width="45" height="45"> <img src="readme_assets/icons/rabbitmq.svg" width="45" height="45"> |
| Database        | <img src="readme_assets/icons/postgresql.svg" width="45" height="45">                                                                                                                                 |
| Frontend        | <img src="readme_assets/icons/bun.png" width="45" height="45"> <img src="readme_assets/icons/javascript.svg" width="45" height="45"> <img src="readme_assets/icons/react.svg" width="45" height="45"> |
| Site Structure  | <img src="readme_assets/icons/html.svg" width="45" height="45"> <img src="readme_assets/icons/css.svg" width="45" height="45">                                                                        |
| Version Control | <img src="readme_assets/icons/git.svg" width="45" height="45"> <img src="readme_assets/icons/github.svg" width="45" height="45">                                                                      |
| Contenerization | <img src="readme_assets/icons/docker.svg" width="45" height="45">                                                                                                                                     |

## Prerequisites

* Git
* Docker
* Docker compose

## Setup

Clone repository:

```sh
  git clone https://github.com/patrykmil/ztpai.git
```

Rename .env.template to .env and fill with your own values

Inside the project directory, run:

```sh
  docker compose up -d --build
```

## Structure

| Service                   | Port  |
| ------------------------- | ----- |
| API                       | 8080  |
| Website                   | 5173  |
| Rabbitmq management panel | 15672 |
| Database                  | 5432  |


### Architecture

<img src="/readme_assets/iu.excalidraw.png" width="50%">

### Entity Relationship Diagram

<img src="/readme_assets/erd.png" width="50%">

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

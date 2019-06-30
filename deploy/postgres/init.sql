CREATE USER ds;
CREATE DATABASE research;
GRANT ALL PRIVILEGES ON DATABASE research TO ds;

create table if not exists claims
(
    id integer unique primary key
);

alter table claims owner to ds;

create table if not exists claim_images
(
    id integer unique primary key
);

alter table claim_images owner to ds;

create table if not exists claim_tags
(
    id integer unique primary key
);

alter table claim_tags owner to ds;

create table if not exists tags
(
    id integer unique primary key
);

alter table tags owner to ds;

create table if not exists users
(
    id integer unique primary key
);

alter table users owner to ds;

create table if not exists claims_pool
(
    id integer unique primary key
);

alter table claims_pool owner to ds;

create table if not exists image_metadata
(
    id integer unique primary key
);

alter table image_metadata owner to ds;


# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table user (
  email                     varchar(255) not null,
  id                        integer,
  name                      varchar(255),
  country                   varchar(255),
  city                      varchar(255),
  birth_date                varchar(255),
  user_type                 integer,
  password                  varchar(255),
  last_visit                timestamp,
  constraint pk_user primary key (email))
;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists user_seq;


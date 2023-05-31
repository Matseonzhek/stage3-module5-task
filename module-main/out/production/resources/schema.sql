-- drop table if exists authors_news;
drop table if exists news_tag;
drop table if exists news_comment;
drop table if exists comments;
drop table if exists newsmodel;
drop table if exists news;
drop table if exists authormodel;
drop table if exists authors;
drop table if exists tag;
drop table if exists tagmodel;
drop sequence if exists tagsequence;

CREATE TABLE IF NOT EXISTS authors
(
    id SERIAL  NOT NULL ,
    createddate timestamp without time zone NOT NULL,
    name character varying(255)  ,
    updateddate timestamp without time zone NOT NULL,
    CONSTRAINT authors_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS news
(
    id SERIAL  NOT NULL ,
    content character varying(255)  ,
    createddate timestamp without time zone NOT NULL,
    title character varying(255)  ,
    updateddate timestamp without time zone NOT NULL,
    authormodel_id bigint,
    CONSTRAINT news_pkey PRIMARY KEY (id),
    CONSTRAINT fk9y3kpi7r1ltiqfqeygicqlvps FOREIGN KEY (authormodel_id)
        REFERENCES authors (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS comments
(
    id SERIAL  NOT NULL ,
    content character varying(255)  ,
    createddate timestamp without time zone NOT NULL,
    updateddate timestamp without time zone,
    newsmodel_id bigint,
    CONSTRAINT comments_pkey PRIMARY KEY (id),
    CONSTRAINT fkscd6gmh52ntiyink7rgy3tukf FOREIGN KEY (newsmodel_id)
        REFERENCES news (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE TABLE IF NOT EXISTS tag
(
    id SERIAL  NOT NULL ,
    name character varying(255)  ,
    CONSTRAINT tag_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS news_tag
(
    news_id bigint NOT NULL,
    tag_id bigint NOT NULL,
    CONSTRAINT fk6t4e6895p5bpxftb9cpa7d9rq FOREIGN KEY (news_id)
        REFERENCES news (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkc8mlwm67bxmmskec8wg4sd09g FOREIGN KEY (tag_id)
        REFERENCES tag (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


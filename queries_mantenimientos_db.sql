-- DROP SCHEMA dbo;

CREATE DATABASE queries_mantenimientos_db;
USE queries_mantenimientos_db;
-- queries_mantenimientos_db.dbo.t_actions definition

-- Drop table

-- DROP TABLE ocb_vaucher_mantenimientos.dbo.t_actions;

CREATE TABLE t_actions (
                           id int IDENTITY(1,1) NOT NULL,
                           name varchar(255) COLLATE Modern_Spanish_CI_AS NULL,
                           CONSTRAINT PK__t_action__3213E83F473D8BA8 PRIMARY KEY (id)
);


-- ocb_vaucher_mantenimientos.dbo.t_audit_log definition

-- Drop table

-- DROP TABLE ocb_vaucher_mantenimientos.dbo.t_audit_log;

CREATE TABLE t_audit_log (
                             id bigint IDENTITY(1,1) NOT NULL,
    [action] varchar(255) COLLATE Modern_Spanish_CI_AS NULL,
    data_json varchar(255) COLLATE Modern_Spanish_CI_AS NULL,
    [date] datetime2 NULL,
    user_id int NOT NULL,
    CONSTRAINT PK__t_audit___3213E83FFF5707AC PRIMARY KEY (id)
    );


-- ocb_vaucher_mantenimientos.dbo.t_tables definition

-- Drop table

-- DROP TABLE ocb_vaucher_mantenimientos.dbo.t_tables;

CREATE TABLE t_tables (
                          id bigint IDENTITY(1,1) NOT NULL,
                          app_id int NULL,
                          name varchar(255) COLLATE Modern_Spanish_CI_AS NULL,
                          CONSTRAINT PK__t_tables__3213E83FE52784BA PRIMARY KEY (id)
);


-- ocb_vaucher_mantenimientos.dbo.t_fields definition

-- Drop table

-- DROP TABLE ocb_vaucher_mantenimientos.dbo.t_fields;

CREATE TABLE t_fields (
                          id bigint IDENTITY(1,1) NOT NULL,
                          name varchar(255) COLLATE Modern_Spanish_CI_AS NULL,
                          table_id bigint NULL,
    [type] varchar(255) COLLATE Modern_Spanish_CI_AS NULL,
    CONSTRAINT PK__t_fields__3213E83FD24CE596 PRIMARY KEY (id),
    CONSTRAINT FKqmej2vnju91cx1291d14bvss3 FOREIGN KEY (table_id) REFERENCES t_tables(id)
    );


-- ocb_vaucher_mantenimientos.dbo.t_queries definition

-- Drop table

-- DROP TABLE ocb_vaucher_mantenimientos.dbo.t_queries;

CREATE TABLE t_queries (
                           id bigint IDENTITY(1,1) NOT NULL,
                           authorized_at datetime2 NULL,
                           authorized_by int NULL,
                           parameters varchar(255) COLLATE Modern_Spanish_CI_AS NULL,
                           requested_at datetime2 NULL,
                           requested_by int NOT NULL,
                           response varchar(255) COLLATE Modern_Spanish_CI_AS NULL,
                           status varchar(255) COLLATE Modern_Spanish_CI_AS NULL,
                           where_condition varchar(255) COLLATE Modern_Spanish_CI_AS NULL,
                           action_id int NULL,
                           table_id bigint NULL,
                           CONSTRAINT PK__t_querie__3213E83FE81723CE PRIMARY KEY (id),
                           CONSTRAINT FK49qvq5boaa39f8y72h9jeuvc9 FOREIGN KEY (action_id) REFERENCES t_actions(id),
                           CONSTRAINT FKmg6wl6yht5y2v0ba42xykxf13 FOREIGN KEY (table_id) REFERENCES t_tables(id)
);


-- ocb_vaucher_mantenimientos.dbo.t_table_actions definition

-- Drop table

-- DROP TABLE ocb_vaucher_mantenimientos.dbo.t_table_actions;

CREATE TABLE t_table_actions (
                                 table_id bigint NOT NULL,
                                 action_id int NOT NULL,
                                 CONSTRAINT FK6jetmvdt27n37kexas0390u6i FOREIGN KEY (action_id) REFERENCES t_actions(id),
                                 CONSTRAINT FKtipac8gkcwdqfhaghie2qbh2a FOREIGN KEY (table_id) REFERENCES t_tables(id)
);

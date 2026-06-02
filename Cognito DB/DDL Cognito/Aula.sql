CREATE TABLE aula (
PK_id_aula SERIAL PRIMARY KEY,
aula_titulo VARCHAR(100) NOT NULL,
aula_descricao VARCHAR(300) NOT NULL,
aula_nivel VARCHAR(10) NOT NULL
);
CREATE TABLE atividade (
PK_id_atividade SERIAL PRIMARY KEY,
atividade_pergunta VARCHAR(300) NOT NULL,
atividade_resposta VARCHAR(200) NOT NULL,
atividade_tipo VARCHAR(50) NOT NULL,
FK_id_aula INT REFERENCES aula(PK_id_aula)
);
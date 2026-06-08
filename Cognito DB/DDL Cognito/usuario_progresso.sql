-- Tabela que serve para atualizar o progresso de cada usuário.
-- Foi feita após a necessidade durante o desnevolvimento do Front-End

CREATE TABLE usuario_progresso (
    id_progresso SERIAL PRIMARY KEY,
    id_usuario BIGINT,
    id_aula INT,
    tipo_conclusao VARCHAR(50), -- VIDIO ASSISTIDO ou ATIVIDADE_CONCLUIDA
    data_conclusao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE usuario_progresso ADD COLUMN id_atividade INT;


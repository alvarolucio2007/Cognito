CREATE TABLE usuario (
PK_id_usuario SERIAL PRIMARY KEY, -- Corrigido de PK_id_usaurio para PK_id_usuario
usuario_nome VARCHAR(150) NOT NULL,
usuario_email VARCHAR(100) NOT NULL,
usuario_senha VARCHAR(16) NOT NULL,
usuario_data_nascimento TIMESTAMP NOT NULL
);
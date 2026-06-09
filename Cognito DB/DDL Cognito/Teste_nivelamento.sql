CREATE TABLE teste_nivelamento(
PK_id_teste_nivelamento SERIAL PRIMARY KEY,
teste_nivelamento_nivel_detectado VARCHAR(20),
teste_nivelamento_data_realizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
FK_id_usuario INT REFERENCES usuario(PK_id_usuario) -- Referência Corrigida aqui também
);
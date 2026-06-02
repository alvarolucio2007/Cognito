CREATE TABLE configuracao(
PK_id_configuracao SERIAL PRIMARY KEY,
configuracao_alto_contraste BOOLEAN DEFAULT 'FALSE',
configuracao_texto_ampliado BOOLEAN DEFAULT 'FALSE',
configuracao_sensibilidade_toque FLOAT DEFAULT 1.0,
configuracao_modo_voz BOOLEAN DEFAULT 'FALSE',
FK_id_usuario INT REFERENCES usuario(PK_id_usaurio)
);
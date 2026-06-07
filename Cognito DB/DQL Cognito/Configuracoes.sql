SELECT * FROM configuracao;

SELECT *
FROM configuracao
WHERE configuracao_alto_contraste = TRUE;

SELECT *
FROM configuracao
WHERE configuracao_modo_voz = TRUE;

SELECT
    u.usuario_nome,
    c.configuracao_alto_contraste,
    c.configuracao_texto_ampliado,
    c.configuracao_modo_voz
FROM usuario u
INNER JOIN configuracao c
ON u.PK_id_usaurio = c.FK_id_usuario;
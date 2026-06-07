SELECT * FROM teste_nivelamento;

SELECT *
FROM teste_nivelamento
WHERE teste_nivelamento_nivel_detectado = 'Iniciante';

SELECT
    teste_nivelamento_nivel_detectado,
    COUNT(*) AS quantidade
FROM teste_nivelamento
GROUP BY teste_nivelamento_nivel_detectado;

SELECT
    u.usuario_nome,
    t.teste_nivelamento_nivel_detectado,
    t.teste_nivelamento_data_realizacao
FROM usuario u
INNER JOIN teste_nivelamento t
ON u.PK_id_usaurio = t.FK_id_usuario;

SELECT *
FROM teste_nivelamento
ORDER BY teste_nivelamento_data_realizacao;
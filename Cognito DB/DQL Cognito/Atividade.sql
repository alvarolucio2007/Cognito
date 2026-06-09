SELECT * FROM atividade;

SELECT *
FROM atividade
WHERE atividade_tipo = 'Múltipla Escolha';

SELECT *
FROM atividade
WHERE atividade_tipo = 'Dissertativa';

SELECT atividade_tipo,
       COUNT(*) AS quantidade
FROM atividade
GROUP BY atividade_tipo;

SELECT
    a.atividade_pergunta,
    au.aula_titulo
FROM atividade a
INNER JOIN aula au
ON a.FK_id_aula = au.PK_id_aula;
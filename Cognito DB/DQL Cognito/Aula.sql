SELECT * FROM aula;

SELECT *
FROM aula
WHERE aula_nivel = 'Iniciante';

SELECT *
FROM aula
WHERE aula_nivel = 'Intermediário';

SELECT aula_nivel,
       COUNT(*) AS quantidade
FROM aula
GROUP BY aula_nivel;

SELECT *
FROM aula
ORDER BY aula_titulo;
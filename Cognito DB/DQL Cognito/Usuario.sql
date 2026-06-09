SELECT * FROM usuario;

SELECT usuario_nome,
       usuario_email
FROM usuario;

SELECT *
FROM usuario
ORDER BY usuario_nome;

SELECT COUNT(*) AS total_usuarios
FROM usuario;

SELECT usuario_nome,
       usuario_email
FROM usuario
WHERE usuario_nome LIKE 'J%';
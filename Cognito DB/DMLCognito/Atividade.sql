INSERT INTO atividade (atividade_pergunta ,atividade_resposta, atividade_tipo,FK_id_aula) VALUES
('Qual é a sintaxe básica do comando SELECT?', 'SELECT coluna FROM tabela WHERE condição', 'Múltipla Escolha', 1),
('Qual operador é usado para combinar tabelas?', 'JOIN', 'Verdadeiro/Falso', 2),
('Como inserir dados em uma tabela?', 'INSERT INTO tabela (colunas) VALUES (valores)', 'Completar a Frase', 3),
('O que é uma chave primária?', 'Um identificador único para cada registro', 'Dissertativa', 3),
('Qual a diferença entre INNER JOIN e LEFT JOIN?', 'INNER JOIN retorna apenas registros que existem em ambas as tabelas, LEFT JOIN retorna todos do lado esquerdo', 'Dissertativa', 2),
('Escreva uma query para atualizar um registro', 'UPDATE tabela SET coluna = valor WHERE condição', 'Prática', 3),
('Como deletar registros de uma tabela?', 'DELETE FROM tabela WHERE condição', 'Múltipla Escolha', 3),
('O que é uma VIEW?', 'Uma tabela virtual criada a partir de uma query', 'Verdadeiro/Falso', 10),
('Qual é a função do GROUP BY?', 'Agrupar registros por um ou mais campos', 'Completar a Frase', 1),
('Como criar um índice?', 'CREATE INDEX nome_indice ON tabela (coluna)', 'Prática', 4);
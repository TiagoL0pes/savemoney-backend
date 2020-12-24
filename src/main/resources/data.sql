INSERT INTO bancos(id_banco, codigo, nome)
    SELECT * FROM (SELECT 1, '001', 'Banco do Brasil') AS registro
    WHERE NOT EXISTS (
        SELECT codigo FROM bancos WHERE codigo = '246'
    ) LIMIT 1;

INSERT INTO bancos(id_banco, codigo, nome)
    SELECT * FROM (SELECT 2, '033', 'Santander') AS registro
    WHERE NOT EXISTS (
        SELECT codigo FROM bancos WHERE codigo = '246'
    ) LIMIT 1;

INSERT INTO bancos(id_banco, codigo, nome)
    SELECT * FROM (SELECT 3, '104', 'Caixa Econômica') AS registro
    WHERE NOT EXISTS (
        SELECT codigo FROM bancos WHERE codigo = '246'
    ) LIMIT 1;

INSERT INTO bancos(id_banco, codigo, nome)
    SELECT * FROM (SELECT 4, '237', 'Bradesco') AS registro
    WHERE NOT EXISTS (
        SELECT codigo FROM bancos WHERE codigo = '246'
    ) LIMIT 1;

INSERT INTO bancos(id_banco, codigo, nome)
    SELECT * FROM (SELECT 5, '341', 'Banco Itaú-Unibanco') AS registro
    WHERE NOT EXISTS (
        SELECT codigo FROM bancos WHERE codigo = '246'
    ) LIMIT 1;

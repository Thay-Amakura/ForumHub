CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE curso (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    categoria VARCHAR(50)
);

CREATE TYPE estado_topico AS ENUM ('ABERTO', 'FECHADO', 'ARQUIVADO');

CREATE TABLE topico (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    mensagem TEXT NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado estado_topico DEFAULT 'ABERTO',
    autor_id INT REFERENCES usuario(id),
    curso_id INT REFERENCES curso(id)
);
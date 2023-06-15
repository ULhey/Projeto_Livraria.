CREATE DATABASE livraria

USE livraria

CREATE TABLE editora (
	cod INT NOT NULL,
	nome VARCHAR(30) NOT NULL,
	site VARCHAR(40) NULL,
	PRIMARY KEY (cod)
)

CREATE TABLE autor (
	cod INT NOT NULL,
	nome VARCHAR(30) NOT NULL,
	PRIMARY KEY (cod)
)

CREATE TABLE genero(
	cod INT NOT NULL,
	descricao VARCHAR(50) NOT NULL,
	PRIMARY KEY (cod)
)

CREATE TABLE livro (
	cod  INT NOT NULL,
	titulo VARCHAR(100) NOT NULL,
	codGenero INT NOT NULL,
	codAutor INT NOT NULL,
	PRIMARY KEY (cod),
	FOREIGN KEY (codGenero) REFERENCES genero (cod),
	FOREIGN KEY (codAutor) REFERENCES autor (cod)
)

CREATE TABLE  edicao(
	cod INT NOT NULL,
	tipoed VARCHAR(50) NOT NULL,
	valor DECIMAL(7,2) NOT NULL,
	numpag INT  NOT NULL,
	codEditora INT NOT NULL,
	codLivro  INT NOT NULL,
	PRIMARY KEY (cod),
	FOREIGN KEY (codEditora) REFERENCES editora (cod),
	FOREIGN KEY (codLivro) REFERENCES livro (cod)
)

CREATE TABLE cliente(
	CPF VARCHAR(11) NOT NULL,
	nome VARCHAR(50) NOT NULL,
	PRIMARY KEY (CPF)
)

CREATE TABLE compra (
	cod INT NOT NULL,
	codEdicao INT NOT NULL,
	qtdComprada INT NOT NULL,
	CPFcli VARCHAR(11) NOT NULL,
	PRIMARY KEY (cod),
	FOREIGN KEY (codEdicao) REFERENCES edicao (cod),
	FOREIGN KEY (CPFcli) REFERENCES cliente(CPF)
)

CREATE TABLE estoque(
	cod INT NOT NULL,
	codEdicao INT NOT NULL,
	quantidade INT NOT NULL,
	PRIMARY KEY (cod),
	FOREIGN KEY (codEdicao) REFERENCES edicao (cod)
)

INSERT INTO autor VALUES 
(4, 'Lucas' ),
(3, 'Wellen'),
(2, 'William'),
(1, 'Felippe')

INSERT INTO editora VALUES
(3, 'Naruaito', 'Sasuke.com'),
(1, 'felippe', 'ofilhofeionaotempai.com'),
(2, 'Scrolar', 'Scrola.com')

INSERT INTO genero VALUES
(1, 'Terror do JavaFX'),
(2, 'Depressão do Java')

INSERT INTO livro VALUES
(1,'Amor e amor',1,1),
(2,'Scrolando com sucesso',2,1),
(3,'Naruitando com sucesso',1,4),
(4,'Sucesso com sucesso',1,3)

INSERT INTO edicao VALUES
(1, 'Capa Dura', 100, 20.00, 1 , 1),
(2, 'Capa Simples', 350, 15.00,1, 1)

INSERT INTO estoque VALUES
(2,2,33)
(1,1,25)

INSERT INTO cliente VALUES
('12345678912', 'Felippe'),
('12345678913', 'Lucas'),
('12345678914', 'William'),
('12345678915', 'Alguem')

INSERT INTO compra VALUES
(1, 1, 3, '12345678912'),
(4, 2, 6, '12345678913'),
(2, 2, 7, '12345678914'),
(3, 2, 5, '12345678915')

select * from edicao
select * from autor
select * from editora 
select * from livro
select * from estoque
select * from compra
select * from cliente
select * from genero
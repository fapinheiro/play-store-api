insert into categoria (nome) values ('Informatica');
insert into categoria (nome) values ('Escritorio');
insert into categoria (nome) values ('Perfumaria');
insert into categoria (nome) values ('Higiene');
insert into categoria (nome) values ('Casa & Conforto');

insert into produto (nome, preco) values ('Computador', 2000);
insert into produto (nome, preco) values ('Impressora', 800);
insert into produto (nome, preco) values ('Mesa escritorio', 300);
insert into produto (nome, preco) values ('Toalha', 20);
insert into produto (nome, preco) values ('Colcha', 30);
insert into produto (nome, preco) values ('TV true color', 1200);
insert into produto (nome, preco) values ('Shampoo', 7.5);

insert into produto_categoria (id_produto, id_categoria) values (1,1);
insert into produto_categoria (id_produto, id_categoria) values (2,1);
insert into produto_categoria (id_produto, id_categoria) values (2,1);
insert into produto_categoria (id_produto, id_categoria) values (3,2);

insert into estado(nome) values ('Minas Gerais');
insert into estado(nome) values ('Sao Paulo');

insert into cidade(nome, id_estado) values ('Belo Horizonte', 1);
insert into cidade(nome, id_estado) values ('Cotia', 2);
insert into cidade(nome, id_estado) values ('Campinas', 2);

insert into cliente(nome, email, senha, cpf_ou_cnpj, tipo) 
values ('barbara consan', 'barbara@gmail.com', '$2a$10$wB0XIhjN9IvPh9cNWNHSHeGv67rbLizId7Lz4lcSjzIJoOQgjnC4a', '09139875894' , 1);
insert into cliente(nome, email, senha, cpf_ou_cnpj, tipo) 
values ('filipe pinheiro', 'filipe@gmail.com', '$2a$10$wB0XIhjN9IvPh9cNWNHSHeGv67rbLizId7Lz4lcSjzIJoOQgjnC4a', '32610948859' , 1);

insert into perfis (id_cliente, id_perfil) values (1,1);
insert into perfis (id_cliente, id_perfil) values (2,2);

insert into endereco(logradouro, numero, complemento, bairro, 
cep, id_cliente, id_cidade) 
values ('R. Capitanias Hereditarias','1401', 'ap22', 'Jd Novo Sto. Amaro', 
'05820120', 1, 2);

insert into telefone(id_cliente, telefone_completo) 
values (1, '+5511985408857');
insert into telefone(id_cliente, telefone_completo) 
values (1, '+351985408857');

insert into pedido(instante, id_cliente, id_endereco) 
values (current_date, 1, 1);
insert into pedido(instante, id_cliente, id_endereco) 
values (current_date, 1, 1);

insert into item_pedido(desconto, preco, quantidade, id_pedido, id_produto)
values (0, 2000, 1, 1, 1);
insert into item_pedido(desconto, preco, quantidade, id_pedido, id_produto)
values (0, 800, 1, 1, 2);
insert into item_pedido(desconto, preco, quantidade, id_pedido, id_produto)
values (10, 80, 1, 2, 3);

insert into pagamento(estado, id_pedido) values (1, 1);
insert into pagamento(estado, id_pedido) values (2, 2);

insert into pagamento_boleto(id_pedido, data_vencimento, data_pagamento) 
values (1, current_date + 30, null);
insert into pagamento_cartao(id_pedido, numero_de_parcelas) 
values (2, 3);

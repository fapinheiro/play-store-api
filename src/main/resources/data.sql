insert into categoria (nome) values ('informatica');
insert into categoria (nome) values ('escritorio');

insert into produto (nome, preco) values ('computador', 2000);
insert into produto (nome, preco) values ('impressora', 800);
insert into produto (nome, preco) values ('mouse', 80);

insert into produto_categoria (id_produto, id_categoria) values (1,1);
insert into produto_categoria (id_produto, id_categoria) values (2,1);
insert into produto_categoria (id_produto, id_categoria) values (2,1);
insert into produto_categoria (id_produto, id_categoria) values (3,2);

insert into estado(nome) values ('Minas Gerais');
insert into estado(nome) values ('Sao Paulo');

insert into cidade(nome, id_estado) values ('Belo Horizonte', 1);
insert into cidade(nome, id_estado) values ('Cotia', 2);
insert into cidade(nome, id_estado) values ('Campinas', 2);

insert into cliente(nome, email, cpf_ou_cnpj, tipo) values ('32610948859', 'filipe@gmail.com', 'filipe pinheiro', 1);

insert into endereco(logradouro, numero, complemento, bairro, cep, id_cliente, id_cidade) values ('R. Capitanias Hereditarias','1401', 'ap22', 'Jd Novo Sto. Amaro', '05820120', 1, 2);

insert into telefone(id_cliente, telefone_completo) values (1, '+5511985408857');
insert into telefone(id_cliente, telefone_completo) values (1, '+351985408857');

insert into pedido(instante, id_cliente, id_endereco) values (current_date, 1, 1);
insert into pedido(instante, id_cliente, id_endereco) values (current_date, 1, 1);

insert into pagamento(estado, id_pedido) values (0, 1);
insert into pagamento(estado, id_pedido) values (1, 2);

insert into pagamento_boleto(id_pedido, data_vencimento, data_pagamento) values (1, current_date + 30, null);
insert into pagamento_cartao(id_pedido, numero_de_parcelas) values (2, 3);

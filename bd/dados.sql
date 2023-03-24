INSERT INTO CARGO (id_cargo, nome)
     VALUES (seq_cargo.nextval, 'administração');

INSERT INTO CARGO (id_cargo, nome)
     VALUES (seq_cargo.nextval, 'medico');

INSERT INTO CARGO (id_cargo, nome)
     VALUES (seq_cargo.nextval, 'cliente');
    
INSERT INTO USUARIO (id_usuario, id_cargo, cpf, email, nome, senha, contatos, cep, numero, ativo) 
	 VALUES (seq_usuario.nextval, 1, '74812465977', 'carlos_alberto@gmail.com', 'Carlos Paulo Alberto', 'carlospalb417', '(34) 99974-8841', '41567041', '72', 1);

INSERT INTO USUARIO (id_usuario, id_cargo, cpf, email, nome, senha, contatos, cep, numero, ativo) 
	 VALUES (seq_usuario.nextval, 3, '01572557788', 'yago_miguel_farias@gsw.com.br', 'Yago Miguel João Farias', 'yagomiguel7788', '(33) 99943-5687', '64004805', '15', 1);

INSERT INTO CONVENIO (id_convenio, cadastro_orgao_regulador, taxa_abatimento) 
 	 VALUES (seq_convenio.nextval, '357391', 5.2);

INSERT INTO CLIENTE (id_cliente, id_convenio, id_usuario) 
	 VALUES (seq_cliente.nextval, 1, 1);

INSERT INTO USUARIO (id_usuario, id_cargo, cpf, email, nome, senha, contatos, cep, numero, ativo) 
	 VALUES (seq_usuario.nextval, 2, '56162690776', 'carolinepietrarocha@ferplast.com.br', 'Caroline Pietra Melissa Rocha', 'carolinepietrarocha0776', '(27) 98815-5061', '75901133', '280', 1);

INSERT INTO ESPECIALIDADE (id_especialidade, nome, valor) 
	 VALUES (seq_especialidade.nextval, 'Clínico geral', 500.00);

INSERT INTO MEDICO (id_medico, id_usuario, id_especialidade, crm) 
	 VALUES (seq_medico.nextval, 2, 1, 'CRM-ES123456');

INSERT INTO AGENDAMENTO (id_agendamento, id_medico, id_cliente, data_horario) 
	 VALUES (seq_agendamento.nextval, 1, 1, TO_DATE('2023-02-28 15:00','yyyy/mm/dd hh24:mi'));

-- Supondo que foi consultado:
UPDATE AGENDAMENTO 
	SET exame = 'Sangue', tratamento = 'Dipirona' 
	WHERE id_agendamento = 1;

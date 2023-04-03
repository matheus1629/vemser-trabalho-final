# Safety Soft
<h3> <i> 'Segurança dos dados e qualidade de vida' </i> </h3>
<h2 style="text-align: center" >Projeto Java para Agendamento e Gestão de Consultas Médicas</h2>

<p>
    Este projeto tem como ponto principal o desenvolvimento de uma API RESTful do sistema back-end para a gerência e agendamento de consultas médicas.
</p>
<hr>
<p>
    <strong> ⛺ Estrutura principal:</strong>
    <ul>
        <li><u>Usuário Administrativo</u> - <i>tem acesso as de controle nos registros do sistema.</i></li>
        <li><u>Cliente</u> - <i>possui informações pessoais básicas e de login, esse pode ter algum Convênio. </i></li>
        <li><u>Convênio</u> - <i>para o cadastro no Cliente, podendo ter algum abatimento no valor da consulta.</i></li>
        <li><u>Médico</u> - <i>possui informações pessoais básicas e de login, deve ter necessariamente alguma Especialidade.</i></li>
        <li><u>Especialidade</u> - <i>para registrar a atuação e variante do valor de consulta dos médicos.</i></li>
        <li><u>Agendamento</u> - <i>é usado para registrar uma consulta de um paciente (Cliente) com um médico.</i></li>
    </ul>
    Diferenças de permissões:
    <ul>
        <li>Qualquer pessoa consegue: se cadastrar como médico ou cliente, se autenticar, trocar sua senha (estando logado), redefinir sua senha (sem estar logado).</li>
        <li>Um Administrador faz operações de CRUD (create, read, update e delete) em qualquer classe, especialmente: Convênio, Especialidade e Agendamento. Contudo, para que ele crie um agendamento é necessário indicar a solicitação de atendimento (feita pelo cliente). Ele também consegue consultar as solicitações de diversas formas.</li>
        <li>Um Cliente e um Médico podem: atualizar suas informações de cadastro, verificar suas informações e seus agendamentos.</li>
        <li>Um Cliente consegue solicitar um agendamento. Esse agendamento estartá submetido à aceitação de um administrador.</li>
        <li>Um Médico consegue editar um agendamento, mas somente para incluir nele o tratamento e os exames após a consulta ter ocorrido.</li>
    </ul>
    Destaques desta versão:
    <ul>
        <li>Sistema de solicitação de atendimento para os clientes. Agora, para que um agendamento seja criado é necessário que um cliente solicite um agendamento. Um administrador deverá analisar e escolher aceitar ou recusar.</li>
        <li>Registro de logs em alterações de solicitações e agendamentos feitas por algum administrador. Esses logs podem ser consultados pelo administrador.</li>
        <li>Código fonte com testes unitários aplicados.</li>
    </ul>
</p>
<hr>

### 🛠 Tecnologias e padrões de projeto
<ul>
    <li>Java 17</li>
    <li>Spring Boot</li>
    <li>Spring Security</li>
    <li>Spring Data</li>
    <li>Banco de dados Oracle</li>
    <li>MongoDB</li>
    <li>Swagger</li>
    <li>Mockito</li>
    <li>JavaMail com templates FreeMarker</li>
    <li>Feign Client</li>
    <li>Jenkins</li>
    <li>Abordagem MVC</li>
    <li>Padrão de projeto DTO</li>
</ul>
<h6><i> Obs.: deploy disponvível somente internamente</i></h6>
<hr>

### Diagrama de Entidade e Relacionamento (Banco de Dados)
<img src="docs/ER.png">

<hr>

### Diagrama de classes (Estrutura do código)

<a href="docs/Diagrama_de_Classes.jpg">Clique aqui para visualizar.</a>

<hr>

#### Vem Ser - Trabalho final do Módulo 3.3
## Trello da divisão de tarefas:
<p>https://trello.com/b/qHePfMlv/trabalho-final-modulo-4</p>

<hr>
<h2>Autores</h2> 
<table>
  <tr>
    <td align="center"><a href="https://github.com/matheus1629"><img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/89110918?v=4" width="100px;" alt=""/><br /><sub><b>Matheus Palermo</b></sub></a><br /></td>
    <td align="center"><a href="https://github.com/pedro-s-20"><img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/63027972?v=4" width="100px;" alt=""/><br /><sub><b>Pedro Sousa</b></sub></a><br /></td>
    <td align="center"><a href="https://github.com/Thassio141"><img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/73563601?v=4" width="100px;" alt=""/><br /><sub><b>Thassio Vagner</b></sub></a><br /></td>
  </tr>
</table>

<hr>
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
        <li><u>Usuário Administrativo</u> - <i>tem acesso a todas funcionalidades do sistema.</i></li>
        <li><u>Cliente</u> - <i>possui informações pessoais básicas e de login, esse pode ter algum Convênio. </i></li>
        <li><u>Convênio</u> - <i>para o cadastro no Cliente, podendo ter algum abatimento no valor da consulta.</i></li>
        <li><u>Médico</u> - <i>possui informações pessoais básicas e de login, deve ter necessariamente alguma Especialidade.</i></li>
        <li><u>Especialidade</u> - <i>para registrar a atuação e variante do valor de consulta dos médicos.</i></li>
        <li><u>Agendamento</u> - <i>é usado para registrar uma consulta de um paciente (Cliente) com um médico.</i></li>
    </ul>
    Diferenças de permissões:
    <ul>
        <li>Qualquer pessoa consegue: criar um login, se autenticar, trocar sua senha (estando logado), redefinir sua senha (sem estar logado), se cadastrar como médico ou cliente.</li>
        <li>Um Administrador faz operações de CRUD (create, read, update e delete) em qualquer classe, especialmente: Convênio, Especialidade e Agendamento. Ou seja, somente ele faz agendamentos.</li>
        <li>Um Cliente e um Médico podem somente: atualizar suas informações de cadastro, verificar suas informações e verificar seus agendamentos.</li>
    </ul>
    Destaques desta versão:
    <ul>
        <li>Sistema de autenticação e segurança, bem como diferenciação de acessos e sistema para login (com criptografia de senhas e recursos para recuperação de acesso).</li>
        <li>Valor da consulta é calculado e incluído no agendamento, com base na especialidade do médico e desconto de convênio do cliente.</li>
        <li>Envio de e-mails nos casos em que: se cria um usuário, se inlui, altera ou exclui um Agendamento (enviado tanto ao médico quanto ao Cliente), se solicita um código para redefinir senha e ao ter alterado a senha.</li>
        <li>Retorno de informações adicionais do endereço coletadas através da pesquisa de CEP por uma comunicação com a API pública do site <i>viacep.com.br</i>.</li>
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
    <li>Swagger</li>
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
<p>https://trello.com/b/pY4BMcN8/trabalho-final-spring-data</p>

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
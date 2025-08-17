## 💬 FórumHub

<p>API REST de um fórum online para discussão de tópicos, respostas e usuários, com autenticação via JWT e documentação Swagger.</P>

---

<h3>🛠️ Funcionalidades</h3>

 - CRUD completo de Tópicos (/topicos):

 - Criar, listar, detalhar, atualizar e excluir tópicos.

 - Paginação e ordenação por data.

 - Filtro por curso e ano.

 - Validação de dados e prevenção de duplicidades.

 - CRUD completo de Usuários (/usuario):

 - Criar, listar, detalhar, atualizar e excluir usuários.

 - CRUD completo de Respostas (/respostas):

 - Criar, listar, detalhar, atualizar e excluir respostas vinculadas a tópicos.

 - Autenticação de usuários via JWT.

 - Documentação interativa da API via Swagger.

---

<h3>🖥️ Tecnologias Utilizadas</h3>

<img align="left" alt="VsCode" tittle="VsCode" width="30px" style="padding-right:10px;" src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/vscode/vscode-original.svg"/>

<img align="left" alt="Java" tittle="Java" width="30px" style="padding-right:10px;" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg"/>

<img align="left" alt="Spring" tittle="1spring" width="30px" style="padding-right:10px;" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" />

<img align="left" alt="Git" tittle="Git" width="30px" style="padding-right:10px;" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/git/git-original.svg" />

<img align="left" alt="PostgreSQL" tittle="PostgreSQL" width="30px" style="padding-right:10px;" src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/postgresql/postgresql-original.svg" />

<img align="left" alt="Insomnia" tittle="Insomnia" width="30px" style="padding-right:10px;" src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/insomnia/insomnia-original-wordmark.svg" /><br>

---

<h3>📝 Requisitos</h3>

 - Java 17 ou superior

 - PostgreSQL 14+

 - Maven 3.8+

---

<h3>🎲 Configuração do Banco de Dados<h3>

<p>No arquivo src/main/resources/application.properties:</p>

<p>spring.application.name=forum_hub<br>
spring.datasource.url=jdbc:postgresql://localhost/forum_hub<br>
spring.datasource.username=${USER_NAMESQL}<br>
spring.datasource.password=${PASSWORD}<br>
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect</p>

Spring Datasource<br>
spring.jpa.hibernate.ddl-auto=update<br>

spring.jpa.show-sql=true<br>
spring.flyway.enabled=true<br>

Configurações JWT<br>
jwt.secret=MinhaChaveSuperSecretaComMaisDe32Caracteres<br>
jwt.expiration=86400000<br>


⚠️ Lembre-se de substituir seu_usuario e sua_senha pelos dados do seu PostgreSQL.

---

<h3>Executando a Aplicação</h3>

Clone o repositório:

git clone https://github.com/seu-usuario/forumhub.git


Entre na pasta do projeto:

cd forumhub


Compile a aplicação:

mvn clean install


Execute a aplicação:

mvn spring-boot:run


Acesse a documentação Swagger:

http://localhost:8080/swagger-ui/index.html

<h3>Testando a API</h3>

1. Autenticação

URL: POST /login

Corpo da requisição (JSON):

{
  "email": "usuario@email.com",
  "senha": "123456"
}


O retorno será um token JWT, que deve ser enviado nos headers das próximas requisições:

Authorization: Bearer <token>

2. Endpoints principais
Endpoint	Método	Descrição
 - /topicos	GET	Listar todos os tópicos
 - /topicos/{id}	GET	Detalhar tópico específico
 - /topicos	POST	Criar novo tópico
 - /topicos/{id}	PUT	Atualizar tópico existente
 - /topicos/{id}	DELETE	Excluir tópico
 - /usuario	GET	Listar usuários
 - /usuario/{id}	GET	Detalhar usuário
 - /usuario	POST	Criar usuário
 - /usuario/{id}	PUT	Atualizar usuário
 - /usuario/{id}	DELETE	Excluir usuário
 - /respostas	GET	Listar respostas
 - /respostas/{id}	GET	Detalhar resposta
 - /respostas	POST	Criar resposta vinculada a um tópico
 - /respostas/{id}	PUT	Atualizar resposta
 - /respostas/{id}	DELETE	Excluir resposta

---

<h3>Observações</h3>

 - Todos os campos obrigatórios são validados pelo Spring (@Valid).

 - Não é permitido criar tópicos com mesmo título e mensagem.

 - A documentação Swagger fornece exemplos de requisições e respostas para facilitar o teste da API.

 - JWT garante que apenas usuários autenticados possam criar, atualizar ou deletar recursos.

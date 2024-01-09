# Controle financeiro
Sistema para auxiliar no controle financeiro de gastos com cartões de crédito e despesas em geral, projetado no formato de API com arquitetura REST.

## Tecnologias utilizadas:
* Java 8
* Junit 5
* Lombok
* Spring Framework (MVC, Boot, Data JPA, Security)
* JWT
* Maven
* Jacoco
* Swagger

## Testes Unitários
A aplicação possui análise de cobertura de código realizada pelo Jacoco e uma cobertura geral de testes unitários de 98% utilizando Junit 5, o relatório pode ser obtido após a construção do projeto no arquivo target/site/jacoco/index.html.

## Documentação da API
O projeto disponibiliza a documentação das APIs através do Swagger, após iniciar a aplicação, a documentação pode ser verificada no endereço http://localhost:8080/swagger-ui.html.

## Instalação
O projeto foi criado com Maven, basta importá-lo em sua IDE de preferência e realizar o build para resolver as dependências da aplicação.

## Configuração do banco de dados
O projeto está configurado com dois profiles, sendo eles TEST e DEV.
O profile TEST foi utilizado durante o desenvolvimento e está configurado com o banco de dados em memória H2.
O profile DEV está configurado com o banco de dados MySQL, sua configuração pode ser alterada no arquivo resources/application-dev.properties. Para iniciar a aplicação em DEV será necessário rodar o comando docker-compose no diretório docker da raiz da aplicação, caso não possua o docker instalado, é possível também utilizar uma instância do MySQL e criar uma base de dados com o nome savemoney e alterar as credenciais de acesso conforme o exemplo abaixo:
```
spring.datasource.url=jdbc:mysql://localhost:3306/savemoney?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=<usuario>
spring.datasource.password=<senha>
```

## Como usar a API
Existem recursos públicos que podem ser acessados, sendo eles autenticação ('/auth'), registro de novos usuários ('/usuarios') e consulta de bancos cadastrados ('/bancos'). Para o restante das requisições é necessário informar um Bearer token válido através de uma requisição à '/auth' e inseré-lo no header Authorization das próximas requisições.

Os testes na API podem ser realizados através de um cliente REST de sua preferência.

## Como iniciar a aplicação
Para iniciar a aplicação em TEST, basta definir a propriedade de sistema -Dspring.profiles.active=test. Após a inicialização o banco em memória será atualizado com registros para realização de testes.
Para iniciar a aplicação em DEV, será necessário rodar o comando docker-compose up no diretório /docker, após isso, basta definir a propriedade de sistema -Dspring.profiles.active=dev.

## Extras
- Os usuários da IDE Intellij 2020.3+ precisarão adicionar o comando no *build process option* `-Djps.track.ap.dependencies=false` para que seja possível realizar a compilação dos Java Annotation Processors (Lombok e Mapstruct).
A thread detalhada pode ser acomapnhada aqui [[BUG] Lombok Does not work with IntelliJ EAP 2020.3 Build 203.4203.26](https://github.com/projectlombok/lombok/issues/2592#issuecomment-705449860)
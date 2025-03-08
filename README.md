## Informações das Ferramentas

### Versões Utilizadas

- **Spring Boot:** 3.4.3
- **Java:** 23
- **MySQL Connector:** [dependência no `pom.xml`](https://mvnrepository.com/artifact/mysql/mysql-connector-java)
- **Junit Jupiter:** 5.10.0
- **Mockito:** 5.5.0


### Instruções para iniciar o projeto.

1. Utilize esse comando no caminho raiz da pasta do projeto:
    ```bash
    ./mvnw spring-boot:run


Instruções para configurar e iniciar o banco de dados.

## Configuração do Banco de Dados

### Credenciais do Banco de Dados

As credenciais do banco de dados estão configuradas no arquivo `application.properties`.

### Iniciar o Banco de Dados com Docker Compose

Para iniciar o banco de dados usando Docker Compose, siga os passos abaixo:

1. Puxe a última versão do MySQL:
    ```bash
    docker pull mysql:latest
    ```
2. Execute o container do MySQL:
    ```bash
    docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=123 -p 3306:3306 -d mysql:latest
    ```

Certifique-se de ajustar `MYSQL_ROOT_PASSWORD=123` para a sua senha.


### Observação: 

Na hora de escolher a versão do Java fiz com a última, 23, e por conta disso algumas dependências ainda não são suportadas
Por conta disso tive dificuldades em relação a esses testes. Sendo que para isso eles estarão prontos no momento que atualizarem
as dependências. 

Descuido meu nessa parte, mas já alertando aqueles que forem mexer no projeto. 
# Produto API

Este é um projeto de uma API REST para gerenciamento de produtos. Ele foi desenvolvido utilizando **Spring Boot**, **PostgreSQL**, **Flyway** e **Maven**.

## 📋 Pré-requisitos

Antes de começar, você precisará ter os seguintes softwares instalados em sua máquina:

- **Java** (versão 17 ou superior)
- **PostgreSQL** (versão 10 ou superior)
- **Maven** (versão 3.6 ou superior)
- **IntelliJ IDEA** (ou outra IDE de sua preferência)
- **cURL** ou **Postman** (para testar a API)

---

## 🚀 Etapas do Projeto

### 1. Criando o Projeto no IntelliJ IDEA
1. Abra o IntelliJ IDEA.
2. Vá em **File > New > Project**.
3. Escolha **Maven** como tipo de projeto.
4. Preencha os campos:
    - **GroupId**: `com.example`
    - **ArtifactId**: `produto-api`
    - **Version**: `1.0-SNAPSHOT`
5. Clique em **Finish** para criar o projeto.

---

### 2. Configurando o `pom.xml`
O arquivo `pom.xml` é o arquivo de configuração do Maven. Ele contém informações sobre o projeto e suas dependências.

Adicione as dependências no arquivo `pom.xml`:
_As versões devem ser atualizadas para manter a compatibilidade._

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>produto-api</artifactId>
    <version>1.0-SNAPSHOT</version>
    <properties>
        <java.version>17</java.version>
        <spring-boot.version>3.1.6</spring-boot.version>
    </properties>
    <dependencies>
        <!-- Spring Boot Web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>${spring-boot.version}</version>
        </dependency>
        <!-- Spring Boot JPA -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
            <version>${spring-boot.version}</version>
        </dependency>
        <!-- PostgreSQL Driver -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.6.0</version>
        </dependency>
        <!-- Flyway -->
        <dependency>
            <groupId>org.flywaydb</groupId>
            <artifactId>flyway-core</artifactId>
            <version>9.22.1</version>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>${spring-boot.version}</version>
            </plugin>
        </plugins>
    </build>
</project>
```

### 3. Configurando o Banco de Dados
O banco de dados utilizado neste projeto é o PostgreSQL.
A manutenção dos scripts SQL é feita com o Flyway.
O Flyway é uma ferramenta de migração de banco de dados que permite versionar e executar scripts SQL automaticamente.
A execução dos scripts é feita automaticamente ao iniciar a aplicação. Caso haja atualizações, o Flyway executa os scripts necessários.

Crie um banco de dados no PostgreSQL chamado produtos_db.

   ```sql
       CREATE DATABASE produtos_db;
   ```

Configure o arquivo application.properties em src/main/resources:
_ As informações de usuário e senha devem ser atualizadas com as suas credenciais. O uso de um arquivo isolado para as configurações do banco de dados é uma prática recomendada._

```text
      spring.datasource.url=jdbc:postgresql://localhost:5432/produtos_db
      spring.datasource.username=<seu_usuario>
      spring.datasource.password=<sua_senha></sua_senha>
      spring.datasource.driver-class-name=org.postgresql.Driver
      
      spring.jpa.hibernate.ddl-auto=none
      spring.jpa.show-sql=true
      
      spring.flyway.baseline-on-migrate=true
      spring.flyway.locations=classpath:db/migration
   ``` 

### 4. Criando a Estrutura do Projeto
O projeto adota uma estrutura de pacotes com base na arquitetura MVC (Model-View-Controller).
+ A pasta src/main/java contêm os pacotes do projeto.
  + A pasta src/main/java/com/example/produtoapi/model contêm as entidades.
  + A pasta src/main/java/com/example/produtoapi/repository contêm os repositórios.
  + A pasta src/main/java/com/example/produtoapi/service contêm os serviços.
  + A pasta src/main/java/com/example/produtoapi/controller contêm os controladores.
+ A pasta src/main/resources contêm os arquivos de configuração e os scripts SQL.
+ A pasta src/test/java contêm os testes unitários.

#### 4.1 Criar Entidade Produto

Arquivo: src/main/java/com/example/produtoapi/model/Produto.java
   ```java
      package com.example.produtoapi.model;
       
       import jakarta.persistence.Entity;
       import jakarta.persistence.Id;
       
       @Entity
       public class Produto {
       @Id
       private Long id;
       private String nome;
       private String marca;
       
           // Getters e Setters
       }
   ```
#### 4.2 Criar o Repositório

Arquivo: src/main/java/com/example/produtoapi/repository/ProdutoRepository.java

 ```java
     package com.example.produtoapi.repository;
     import com.example.produtoapi.model.Produto;
     import org.springframework.data.jpa.repository.JpaRepository;
     public interface ProdutoRepository extends JpaRepository<Produto, Long> {
     }
 ```

#### 4.3 Criar o Serviço

Arquivo: src/main/java/com/example/produtoapi/service/ProdutoService.java
```java
        package com.example.produtoapi.service;
        
        import com.example.produtoapi.model.Produto;
        import com.example.produtoapi.repository.ProdutoRepository;
        import org.springframework.stereotype.Service;
        
        import java.util.List;
        import java.util.Optional;
        
        @Service
        public class ProdutoService {
        private final ProdutoRepository produtoRepository;
        
            public ProdutoService(ProdutoRepository produtoRepository) {
                this.produtoRepository = produtoRepository;
            }
        
            public List<Produto> findAll() {
                return produtoRepository.findAll();
            }
        
            public Optional<Produto> findById(Long id) {
                return produtoRepository.findById(id);
            }
        
            public Produto save(Produto produto) {
                return produtoRepository.save(produto);
            }
        
            public void deleteById(Long id) {
                produtoRepository.deleteById(id);
            }
        }
   ```

#### 4.4 Criar o Controlador

Arquivo: src/main/java/com/example/produtoapi/controller/ProdutoController.java

```java

        package com.example.produtoapi.controller;
        
        import com.example.produtoapi.model.Produto;
        import com.example.produtoapi.service.ProdutoService;
        import org.springframework.web.bind.annotation.*;
        
        import java.util.List;
        
        @RestController
        @RequestMapping("/api/produtos")
        public class ProdutoController {
        private final ProdutoService produtoService;
        
            public ProdutoController(ProdutoService produtoService) {
                this.produtoService = produtoService;
            }
        
            @GetMapping
            public List<Produto> getAll() {
                return produtoService.findAll();
            }
        
            @GetMapping("/{id}")
            public Produto getById(@PathVariable Long id) {
                return produtoService.findById(id).orElseThrow();
            }
        
            @PostMapping
            public Produto create(@RequestBody Produto produto) {
                return produtoService.save(produto);
            }
        
            @DeleteMapping("/{id}")
            public void delete(@PathVariable Long id) {
                produtoService.deleteById(id);
            }
        }
   ```

### 5. Criar Migração SQL
A migração SQL é um arquivo que contêm as instruções SQL para criar ou alterar a estrutura do banco de dados.
O Flyway fica responsável por executar esses scripts automaticamente. O controle de versão é feito pelo nome do arquivo.
O nome do arquivo segue o padrão V{versão}__{descrição}.sql.

Arquivo: src/main/resources/db/migration/V1__create_table_produtos.sql

```sql
   CREATE TABLE produto (
   id VARCHAR(10) PRIMARY KEY,
   nome VARCHAR(100) NOT NULL,
   marca VARCHAR(50) NOT NULL
   );
```

### 6. Testando a API com cURL
Após a implementação da API, você pode testá-la com cURL. O cURL é uma ferramenta de linha de comando para transferir dados com URLs.
Você também pode usar o Postman ou o Insomnia para testar a API.

#### 6.0 Iniciar a aplicação
   ```bash
      mvn spring-boot:run
   ```

#### 6.1 Criar um produto
   ```bash
       curl -X POST http://localhost:8080/api/produtos \
       -H "Content-Type: application/json" \
       -d '{
       "id": "141",
       "nome": "Fone de Ouvido",
       "descricao": "Fone de Ouvido + Manual + estojo para carregamento + 3 pares de almofadas auriculares + cabo de carregamento. Tipo: In Ear Bluetooth: Sim Alcance do Bluetooth: 10 Metros À Prova d'água: Sim, IPX4 Controle de Play e Pause: Sim Microfones: Sim. Dois em cada fone Isolamento de Ruído: Sim. Isolamento ativo"
       }'
   ```

#### 6.2 Listar produtos
   ```bash
      curl -X GET http://localhost:8080/api/produtos
   ```
   
#### 6.3 Buscar por id
   ```bash
      curl -X GET http://localhost:8080/api/produtos/141
   ```

#### 6.4 Deletar por id
   ```bash
      curl -X DELETE http://localhost:8080/api/produtos/141
   ```
#### 7. Conclusão
   Este projeto cobre todas as etapas, desde a criação no IntelliJ até os testes com cURL. Você pode expandi-lo adicionando autenticação, validações ou novos recursos.

🚀 Divirta-se! 🚀 


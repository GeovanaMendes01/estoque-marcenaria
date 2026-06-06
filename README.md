# Estoque Marcenaria

Aplicação web de gerenciamento de estoque para uma marcenaria, desenvolvida com Spring Boot seguindo o padrão MVC.

## Tecnologias

- Java 17
- Spring Boot
- Spring Data JPA
- Thymeleaf
- Banco de dados H2 (em memória)
- HTML5 e CSS3

## Funcionalidades

- CRUD completo de **Produtos** (nome, tipo de madeira, quantidade, preço)
- CRUD completo de **Fornecedores** (nome, CNPJ, telefone, e-mail)

## Como rodar

**Pré-requisito:** ter o Java 17 instalado.

1. Clone o repositório:
   ```bash
   git clone https://github.com/GeovanaMendes01/estoque-marcenaria.git
   ```

2. Entre na pasta do projeto:
   ```bash
   cd estoque-marcenaria/progWeb
   ```

3. Execute a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```
   No Windows:
   ```bash
   mvnw.cmd spring-boot:run
   ```

4. Acesse no navegador: [http://localhost:8080](http://localhost:8080)

## Páginas disponíveis

| Rota | Descrição |
|------|-----------|
| `/` | Página inicial |
| `/produtos` | Gerenciar produtos |
| `/fornecedores` | Gerenciar fornecedores |
| `/h2-console` | Console do banco de dados (dev) |

> No console H2, use a JDBC URL: `jdbc:h2:mem:marcenaria`, usuário `sa`, senha em branco.

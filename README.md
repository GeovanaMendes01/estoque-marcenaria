# Estoque Marcenaria

Aplicação web de gerenciamento de estoque para uma marcenaria, desenvolvida com Spring Boot seguindo o padrão MVC.

## Tecnologias

- Java 17
- Spring Boot 4.0.6
- Spring Data JPA
- Thymeleaf
- PostgreSQL
- Apache POI 5.3.0 (exportação de planilhas .xlsx)
- Lombok
- HTML5, CSS3 e Font Awesome 5

## Funcionalidades

- **Dashboard** com contagem de produtos, fornecedores e itens em alerta de estoque
- **CRUD completo de Produtos** — nome, descrição, quantidade, preço e fornecedor vinculado
- **CRUD completo de Fornecedores** — nome, CNPJ, telefone e e-mail (validação de CNPJ duplicado)
- **Registro de Baixas** — desconta quantidade do produto no estoque com validação de estoque suficiente
- **Exportação para Excel (.xlsx)** de produtos e de baixas (com filtro por período)

## Pré-requisitos

- Java 17 ou superior
- Maven
- Docker (para o banco de dados PostgreSQL)

## Como rodar

1. Clone o repositório:
   ```bash
   git clone https://github.com/GeovanaMendes01/estoque-marcenaria.git
   cd estoque-marcenaria/progWeb
   ```

2. Suba o container do PostgreSQL:
   ```bash
   docker run --name estoque-pg \
     -e POSTGRES_USER=admin \
     -e POSTGRES_PASSWORD=admin \
     -e POSTGRES_DB=estoque \
     -p 5434:5432 \
     -d postgres
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

## Rotas disponíveis

| Rota | Método | Descrição |
|------|--------|-----------|
| `/` | GET | Dashboard |
| `/produtos` | GET | Listar produtos |
| `/produtos/cadastro` | GET | Formulário de novo produto |
| `/produtos/salvar` | POST | Salvar novo produto |
| `/produtos/editar/{id}` | GET | Formulário de edição |
| `/produtos/atualizar/{id}` | POST | Atualizar produto |
| `/produtos/excluir/{id}` | GET | Excluir produto |
| `/produtos/exportar` | GET | Exportar lista em .xlsx |
| `/fornecedores` | GET | Listar fornecedores |
| `/fornecedores/cadastro` | GET | Formulário de novo fornecedor |
| `/fornecedores/salvar` | POST | Salvar novo fornecedor |
| `/fornecedores/editar/{id}` | GET | Formulário de edição |
| `/fornecedores/atualizar/{id}` | POST | Atualizar fornecedor |
| `/fornecedores/excluir/{id}` | GET | Excluir fornecedor |
| `/baixas` | GET | Listar baixas |
| `/baixas/cadastro` | GET | Formulário de nova baixa |
| `/baixas/salvar` | POST | Registrar baixa |
| `/baixas/excluir/{id}` | GET | Excluir baixa |
| `/baixas/exportar` | GET | Exportar baixas em .xlsx (parâmetros opcionais: `dataInicio` e `dataFim`) |

## Estrutura do projeto

```
src/
└── main/
    ├── java/br/ufms/facom/progWeb/
    │   ├── controllers/
    │   │   ├── HomeController.java
    │   │   ├── ProdutoController.java
    │   │   ├── FornecedorController.java
    │   │   └── BaixaController.java
    │   ├── models/
    │   │   ├── Produto.java
    │   │   ├── Fornecedor.java
    │   │   └── Baixa.java
    │   ├── repositories/
    │   │   ├── ProdutoRepository.java
    │   │   ├── FornecedorRepository.java
    │   │   └── BaixaRepository.java
    │   └── service/
    │       ├── ProdutoService.java
    │       ├── FornecedorService.java
    │       └── BaixaService.java
    └── resources/
        ├── templates/
        │   ├── index.html
        │   ├── produtos.html
        │   ├── cadastro.html
        │   ├── editar.html
        │   ├── fornecedores/
        │   │   ├── index.html
        │   │   └── form.html
        │   └── baixas/
        │       ├── index.html
        │       └── cadastro.html
        ├── static/css/
        │   └── style.css
        └── application.properties
```
# Demo DAO JDBC

Este projeto é uma implementação do padrão de projeto **DAO (Data Access Object)** utilizando **Java** e **JDBC**. O objetivo principal é demonstrar como realizar a persistência de dados em um banco de dados **MySQL** seguindo as melhores práticas de desacoplamento e organização em camadas.

## 📋 Sobre o Projeto

O sistema gerencia registros de **Vendedores (Sellers)** e **Departamentos (Departments)**. A arquitetura foi projetada para separar completamente a lógica de acesso a dados (SQL) da lógica de negócio, facilitando a manutenção e futuras trocas de tecnologia de persistência.

### Funcionalidades Principais:
* **Gestão de Departamentos:** CRUD completo (Inserção, Atualização, Deleção e Consultas) para a entidade `Department`.
* **Gestão de Vendedores:** CRUD completo para a entidade `Seller`.
* **Filtro por Departamento:** Listagem de todos os vendedores vinculados a um setor específico.
* **Tratamento de Erros:** Exceções customizadas (`DbException` e `DbIntegrityException`) para lidar com falhas de conexão e integridade referencial.
* **Instanciação Desacoplada:** Uso do padrão **Factory** para criar instâncias de DAO sem expor a implementação JDBC ao programa principal.

## 🛠️ Tecnologias e Padrões

* **Linguagem:** Java (JDK 13+)
* **Persistência:** JDBC (Java Database Connectivity)
* **Banco de Dados:** MySQL
* **Padrões de Projeto:** DAO (Data Access Object), Factory Method e Composição.

## 📂 Estrutura do Projeto

* `application`: Contém as classes `Program` e `Program2` com os testes manuais.
* `db`: Lógica de conexão, fechamento de recursos e classes de exceção.
* `model.dao`: Interfaces que definem o contrato de persistência.
* `model.dao.impl`: Implementações em JDBC com SQL nativo.
* `model.entities`: Classes de modelo (POJOs) que representam as tabelas.

## 🚀 Como Configurar

### 1. Requisitos
* Java JDK instalado.
* MySQL Server rodando.
* Driver JDBC do MySQL (`mysql-connector-java`).

### 2. Configuração do Banco
Crie o arquivo `db.properties` na raiz do projeto com as suas credenciais:

```properties
user=root
password=123456789
dburl=jdbc:mysql://localhost:3306/coursejdbc
useSSL=false
```

### 3. Execução
Basta executar a classe `Program.java` para testar os vendedores ou a `Program2.java` para os departamentos.

## 📝 Exemplo de Consulta (Inner Join)

Para otimizar o desempenho, a busca por ID de vendedor recupera os dados do departamento em uma única operação:

```sql
SELECT seller.*, department.Name as DepName 
FROM seller INNER JOIN department 
ON seller.DepartmentId = department.Id 
WHERE seller.Id = ?
```

## 📚 Aprendizados

- Implementação do padrão DAO na prática
- Uso de JDBC com boas práticas
- Separação de responsabilidades em camadas

---
Projeto desenvolvido com fins educacionais para praticar acesso a dados com JDBC e aplicação do padrão DAO, com foco em boas práticas de organização e separação de responsabilidades.
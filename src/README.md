# Property Manager

## Descrição

O **Property Manager** é um sistema de gerenciamento de propriedades para imobiliárias independentes. O projeto oferece funcionalidades completas de CRUD (Create, Read, Update, Delete) para entidades como Usuários (Admins, Inquilinos, Proprietários), Propriedades, Contratos, entre outras. Além disso, o sistema inclui regras de negócio robustas e validações para garantir a integridade dos dados e operações.

## Funcionalidades

### CRUD

O sistema oferece operações completas de CRUD para as seguintes entidades:

- **Usuários**: Gerenciamento de usuários do sistema, incluindo admins, inquilinos e proprietários.
- **Propriedades**: Cadastro e gerenciamento de propriedades, incluindo atributos como endereço, tipo de propriedade, valor de aluguel, descrição, entre outros.
- **Contratos**: Gerenciamento de contratos de locação, incluindo criação, leitura, atualização e exclusão de contratos.

### Regras de Negócio Implementadas

O sistema incorpora várias regras de negócio para garantir o funcionamento correto das operações. Algumas das principais regras incluem:

1. **Contrato**:
    - Um contrato pode ser criado apenas se não houver um contrato existente para o mesmo inquilino ou propriedade.
    - Antes de criar um contrato, o sistema verifica se o contrato anterior para o mesmo inquilino ou propriedade foi encerrado com base na data.

2. **Propriedade**:
    - Nenhuma propriedade pode ter mais de um contrato ativo ao mesmo tempo.
    - A verificação se a propriedade está disponível para um novo contrato é realizada antes da criação de um contrato.

3. **Inquilino**:
    - Um inquilino não pode ter mais de um contrato ativo ao mesmo tempo.
    - A verificação se o inquilino está disponível para um novo contrato é realizada antes da criação de um contrato.

### Validações

O sistema utiliza várias validações para garantir a integridade dos dados e evitar operações inválidas:

- **Validações de Entidade**: Validação de campos obrigatórios, formatos de dados, e outras restrições aplicáveis a cada entidade.
- **Validações de Regras de Negócio**: Validações específicas que garantem o cumprimento das regras de negócio, como a verificação de contratos ativos antes de criar novos contratos.

### Tratamento de Exceções

- O sistema utiliza um `@RestControllerAdvice` para tratamento global de exceções. As exceções tratadas incluem:
    - `EntityNotFoundException`: Lançada quando uma entidade não é encontrada.
    - `MethodArgumentNotValidException`: Lançada em caso de falhas de validação.
    - `ValidateException`: Exceções personalizadas para regras de negócio não atendidas.

## Estrutura do Projeto

O projeto segue uma arquitetura limpa e modular, com as camadas principais organizadas da seguinte forma:

- **Controller**: Lida com as requisições HTTP e delega as operações para os serviços.
- **Service**: Contém a lógica de negócio e interage com os repositórios.
- **Repository**: Interface para comunicação com o banco de dados.
- **DTO**: Objetos de Transferência de Dados (DTOs) utilizados para transferir dados entre as camadas.
- **Mapper**: Utilizado para conversão entre entidades e DTOs.

## Tecnologias Utilizadas

- **Java**: Linguagem de programação principal.
- **Spring Boot**: Framework utilizado para a construção do backend.
- **Spring Data JPA**: Abstração para interações com o banco de dados.
- **Spring Security**: Utilizado para autenticação e autorização.
- **Lombok**: Utilizado para simplificação do código e manutenção de POJOs.
- **JUnit**: Utilizado para testes unitários.

## Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/property-manager.git

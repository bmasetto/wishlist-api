# Wishlist API

Essa aplicação consiste em uma API em Java através da qual é possível gerenciar os clientes de uma companhia, assim 
como os seus produtos desejados.

## :triangular_ruler: Arquitetura
Inspirada no padrão [REST](https://www.ics.uci.edu/~fielding/pubs/dissertation/fielding_dissertation.pdf) e no 
[Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html), a arquitetura 
modular dessa API foi dividida da seguinte maneira, visando seu desacoplamento e o foco no domínio do negócio:

### Core
#### Domain
Mapeia toda e qualquer regra de negócio. 

#### Use case
Orquestra as interações entre as entidades do Domain e define os contratos de comunicações externas (Repository). 

### Adapters
#### Input
Disponibiliza as entradas (endpoints) da API e se comunica com o Use Case.

#### Output
Implementa os contratos de comunicações externas definidos pelo Use case.

### App
Sobe um embedded server e disponibiliza as entradas do Input, injetando as dependências necessárias. 

### Auth
Autentica e autoriza a comunicação com o App.

A seguinte imagem exemplifica, de forma resumida e através do endpoint que acessa uma Wishlist, as interações e as 
dependências entre os módulos descritos acima:

![Exemplo de comunicação entre os módulos](/assets/module_example_flow.png)

Em relação às entradas (endpoints) da API disponibilizadas pelo Input, você poderá visualizá-los detalhadamente no 
SwaggerUI após subir a aplicação no seu ambiente local.

## :computer: Rodando local

### Pré-requisitos
- [JDK 16+](https://docs.oracle.com/en/java/javase/16/install/overview-jdk-installation.html)
- [Docker Compose](https://docs.docker.com/compose/install/)
- Portas disponíveis: 8000, 8001 e 8002

Para rodar a aplicação no seu ambiente local, execute as seguintes etapas na ordem descrita e no diretório raiz do 
repositório. Essas etapas foram pensadas para facilitar a primeira execução, mas podem ser aprimoradas com alguns 
softwares (Postman, IntelliJ, etc).

#### Etapa 1 - Rodando os testes e gerando os artefatos
###### Linux e macOS
``./mvnw install``
###### Windows
``./mvnw.cmd install``

#### Etapa 2 - Subindo a stack de infraestrutura
``docker-compose -f local/docker-compose-stack.yml up -d``

#### Etapa 3 - Subindo a aplicação
``docker-compose -f local/docker-compose-spring-app.yml up``

#### Etapa 4 - Usando o SwaggerUI
Para fazer requisições para a aplicação sem depender de outros softwares, você pode abrir o seguinte link no seu 
navegador e usar a feature de "Try it out" do SwaggerUI (como exemplificado no GIF abaixo);

http://localhost:8000/swagger-ui/index.html?configUrl=/api-docs/swagger-config

![Exemplo de criação de cliente no Swagger](/assets/swagger_create_customer_example.gif)

Para que seja possível usar o SwaggerUI como no GIF acima, é necessário informar as seguintes credenciais (parametrizadas 
no container):
- Username: wishlist_api
- Password: 12345

Você também pode verificar os logs da aplicação (output da Etapa 3) para acompanhar as ações que você executa no 
SwaggerUI.

## :rocket: Deploy

### Pré-requisitos
- [Postgresql 13+](https://www.postgresql.org/)
- [Redis 6+](https://redis.io/)
- Serviço que suporte Docker Containers e que consiga se comunicar com o Postgresql e com o Redis

Execute as seguintes etapas na ordem descrita e no diretório raiz do repositório:

#### Etapa 1 - Rodando os testes e gerando os artefatos
###### Linux e macOS
``./mvnw install``
###### Windows
``./mvnw.cmd install``

#### Etapa 2 - Gerando a imagem
``docker build -t wishlistapi-spring-app .``

#### Etapa 3 - Subindo o container
```
docker run wishlistapi -p 8000:8000 \
      -e AUTH_API_USER='<VALUE>' \
      -e AUTH_API_PASSWORD='<VALUE>' \
      -e POSTGRES_URL='<VALUE>' \
      -e POSTGRES_USER='<VALUE>' \
      -e POSTGRES_PASSWORD='<VALUE>' \
      -e PRODUCT_API_URL='<VALUE>' \
      -e LOGGING_LEVEL='<VALUE>' \
      -e REDIS_HOST='<VALUE>' \
      -e REDIS_PORT='<VALUE>' \
      -e REDIS_DATABASE='<VALUE>' \
      -e REDIS_PASSWORD='<VALUE>'
```

# mentoria

<p align="center">
<img src="http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge"/>
</p>

## Sobre o projeto

Demanda: Desenvolvimento de API de Produtos Para servir de Catálogo para nosso ecommerce.
Descrição da Demanda: Estamos buscando criar uma API robusta de gerenciamento de produtos. 
A API será fundamental para o nosso sistema de comércio eletrônico, permitindo a criação, leitura, 
atualização e exclusão de produtos. Além disso, a API deve ser escalável e segura, garantindo que as 
informações dos produtos sejam acessadas apenas por usuários autorizados.


Pré-requisitos:

* Java 17 ou versões superiores.
    - https://www.youtube.com/watch?v=QekeJBShCy4
* Intellj IDEA Community Edition ou sua IDE favorita.
    - https://www.youtube.com/watch?v=0_e9Egeyk2E&t=382s
* Controle de versão GIT instalado na sua máquina.
* Banco MySQL (recomenda-se usar container caso não queira baixar o MySQL na máquina).

```bash

### docker-compose.yml
Colocar na raiz do projeto e rodar o container

version: '3.1'
services:
  db:
    image: mysql
    container_name: mysql
    environment:
      MYSQL_ROOT_PASSWORD: root
    ports:
      - "3306:3306"
    volumes:
      - mentoria_data:/var/lib/mysql

volumes:
  mentoria_data:

```

```bash

# clonar repositório
git clone https://github.com/wilson-silva/mentoria.git


# --- Rodar aplicação mentoria ---

# entrar na pasta do projeto mentoria
cd mentoria ou abrir o bash dentro da pasta

# executar o projeto
./mvnw spring-boot:run

```
## Permissões de acesso

'USER' só acessa métodos GET.

'ADMIN' acessa todos os métodos.

ADMIN:
````
{
    "usuario":"willsilva",
    "senha":"wilson123"
}
````

USER:
```
{
    "usuario":"katysilva",
    "senha":"katy123"
}
```

### Testar recursos via Postman
Obs: Após autenticar o usuario pegar o token gerado e na aba Authorization->Bearer Token-> Token
adicionar no campo desta forma: "Bearer xxxxx..."

Adicionado coleção do postman na raiz do projeto para importar e fazer os teste
"mentoria.postman_collection.json"

### Swagger documentação

```bash
# para acessar a documentação desta API
localhost:8080/swagger-ui.html
```

### Técnicas e tecnologias utilizadas
- Java
- Intellij
- Spring Boot
- Spring Data JPA
- Spring Security
- JWT
- Maven
- MySQL
- Flyway


### Autor
Wilson Gimenes da Silva


https://github.com/wilson-silva/mentoria
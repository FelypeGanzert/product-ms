# Product MicroService
Essa aplicação foi desenvolvida durante o processo seletivo da Compasso UOL.
O projeto é baseado em CRUD(Create, Read, Update and Delete) com Spring e Java.

Para o desenvolvido do projeto foi utilizado os principios do SOLID, de Clean Code e de boas práticas de desenvolvimento que considero essenciais para desenvolver aplicações eficientes, testáveis e que sejam de fácil leitura para manutenção e evolução futura. Além disso os commits foram feitos com a ideia de pequenas entregas, servindo até mesmo como uma documentação do histórico do código.

Esses dias eu vi um [vídeo](https://youtu.be/WYYFwy2Ob5I) do [Otavio Lemos](https://www.youtube.com/channel/UC9cOiXh-RFR7KI61KcyTb0g) sobre a ideia de "Esqueleto Ambulante", achei o conceito muito interessante e apliquei os princípios nesse projeto. Portanto logo no início já configurei um pipeline integrado com o GitHub Actions que executa todos os testes a cada push para a branch principal. O deploy para o Heroku já foi feito no início também fazendo com que caso o Pipeline de CI passase já houvesse o deploy automático para o Heroku. Tendo assim um ambiente de integração e entrega contínua.

Mesmo sem tantas regras de negócio a aplicação foi desenvolvida tendo a cobertura e o desenvolvimento de testes como princípio. 

## :rocket: Principais Tecnologias
* Java 11
* Maven
* Spring Framework
* Banco em memória H2 (Utilizado no desenvolvimento)
* PostgreSQL (Utilizado no Deploy com Heroku)
* Lombok
* SpringFox para gerar a documentação com Swagger
* Pipeline de CI com GitHub Actions
* Heroku configurado com CD

## :books: Documentação e utilização remota
Foi feito o deploy da aplicação no Heroku, a documentação pode ser acessada em:

https://felype-ganzert-products-ms.herokuapp.com/swagger-ui/#/

## 🧪 Rodar os testes local
(É necessário ter o Maven instalado e configurado localmente)

    mvn test

## :rocket: Rodar a aplicação local
(É necessário ter o Maven instalado e configurado localmente)
Para rodar local basta rodar o comando abaixo na raiz do projeto:

    ./mvnw spring-boot:run

Como atualmente está definido em application.properties:

    spring.profiles.active=test

Será subida a aplicação localmente utilizando o Banco H2.

O projeto ficará então acessível em:

    http://localhost:9999/products

## :memo: License
Esse projeto está sobre a MIT license. Veja em [LICENSE](https://github.com/FelypeGanzert/product-ms/blob/main/LICENSE) para mais informações.

---

<h4 align="center">
    Feito por Felype Ganzert - <a href="https://www.linkedin.com/in/felypeganzert/" target="_blank">Entre em Contato</a>
</h4>

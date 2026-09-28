# Catálogo de Jogos

API simples feita em Java e Spring Boot para cadastrar jogos. Os dados ficam em uma lista na memória, então são apagados quando a aplicação é encerrada.

## Como executar

Abra o projeto no IntelliJ, selecione o JDK 25 e execute a classe `CatalogoJogosApplication`. A API ficará disponível em `http://localhost:8080`.

## Rotas

- `GET /jogos` — lista os jogos
- `GET /jogos/{id}` — busca um jogo pelo ID
- `POST /jogos` — cadastra um jogo
- `PUT /jogos/{id}` — atualiza um jogo
- `DELETE /jogos/{id}` — exclui um jogo

O ID é gerado pela própria aplicação. Para cadastrar ou atualizar, envie um JSON como este:

```json
{
  "nome": "Stardew Valley",
  "genero": "Simulação",
  "plataforma": "PC",
  "concluido": false
}
```

Na listagem, é possível filtrar por `genero`, `plataforma` e `concluido`. Os filtros também podem ser usados juntos. Exemplo:

`GET /jogos?plataforma=PC&concluido=false`

As requisições para testar no Postman estão na pasta `postman`. Importe a coleção e execute os exemplos na ordem.
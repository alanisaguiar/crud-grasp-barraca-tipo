# CRUD com Padrões GRASP: Barraca / TipoBarraca

## Grupo A: Alanis Aguiar Bitencourt

## Descrição do projeto
Este projeto foi desenvolvido para a disciplina de Arquitetura de Sistemas com o objetivo de implementar um CRUD completo no contexto de uma feira livre, utilizando Java puro em modo texto e aplicando os padrões GRASP no desenho das classes e no código.

O sistema permite:
- Cadastrar TipoBarraca
- Listar TipoBarraca
- Cadastrar Barraca associada a um TipoBarraca
- Listar Barracas
- Buscar Barraca por id
- Atualizar Barraca
- Excluir Barraca
- Excluir TipoBarraca com validação de vínculo

Além disso, os dados são persistidos em arquivos locais na pasta `data/`.

## Instruções de Compilação e Execução
### Como executar:
#### Execução via terminal
javac -encoding UTF-8 -d bin src/**/*.java  
java -cp bin feira.graspcrud.Main  

#### Execução via IDE
Abrir o projeto no VS Code e executar a classe `Main.java`.

## Padrões GRASP Aplicados
| Padrão GRASP         | Classe(s) |
|--------------------- |----------|
| Information Expert   | Barraca, TipoBarraca |
| Creator              | BarracaService, TipoBarracaService |
| Controller           | BarracaController |
| Low Coupling         | BarracaService, TipoBarracaService |
| High Cohesion        | Todas as classes |
| Pure Fabrication     | JsonMini, BarracaRepositoryJson, TipoBarracaRepositoryJson |
| Indirection          | BarracaRepository, TipoBarracaRepository |
| Protected Variations | BarracaRepository, TipoBarracaRepository |

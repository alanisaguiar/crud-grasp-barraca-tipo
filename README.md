# CRUD com Padrões GRASP: Barraca / TipoBarraca

## Grupo A: Alanis Aguiar Bitencourt

## Descrição do projeto
Este projeto foi desenvolvido para a disciplina de Arquitetura de Sistemas com o objetivo de implementar um CRUD completo no contexto de uma feira livre, utilizando Java e aplicando os padrões GRASP no desenho das classes e no código.

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
| Padrão GRASP        | Classe(s) | Como foi aplicado |
|--------------------|----------|------------------|
| Information Expert | Barraca, TipoBarraca | As regras de validação da entidade e do classificador foram colocadas nas próprias classes de domínio. |
| Creator            | Main, BarracaService, TipoBarracaService | A Main instancia e conecta as dependências; os serviços criam os objetos de domínio a partir dos dados recebidos. |
| Controller         | BarracaController | O controller recebe as opções do menu e delega as ações aos serviços, sem conter regras de negócio. |
| Low Coupling       | BarracaService, TipoBarracaService | Os serviços dependem das interfaces BarracaRepository e TipoBarracaRepository, não das implementações concretas. |
| High Cohesion      | Todas as classes | Cada classe possui uma responsabilidade única e bem definida. |
| Pure Fabrication   | JsonMini, BarracaRepositoryJson, TipoBarracaRepositoryJson | Essas classes foram criadas para lidar com persistência e manipulação de arquivos, mantendo o domínio desacoplado. |
| Indirection        | BarracaRepository, TipoBarracaRepository | Os serviços acessam a persistência por meio das interfaces, criando um intermediário. |
| Protected Variations | BarracaRepository, TipoBarracaRepository | O sistema permite trocar a forma de persistência sem alterar as regras de negócio. |

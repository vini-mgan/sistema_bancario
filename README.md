# sistema_bancario
Projeto 1 para a cadeira de Programação Orientada à Objetos ministrada pelo professor Lucas Rodolfo

**Aluno:** Vinícius Martins Galindo Andrade

## Requisitos

### Requisitos Mínimos
- Classe Client é utilizada para representar diferentes contas bancárias.
  
- Movimentações da conta, números de CPF e até referências aos objetos tipo Client são armazenados em arrays e manipulados de diversas formas durante a execução.
  
- Qualquer transação entre contas é validada. O valor movimentado não pode ser maior que o do saldo de conta e um usuário não pode transferir para si mesmo, por exemplo.

### Requisitos Adicionais

- O usuário pode criar diversas contas e transicionar entre elas, fazendo login e logout e realizando multiplas transações. Os objetos de cada conta se comunicam para que os valores de saldo de ambas as partes sejam devidamente modificados. Dessa forma, é possível criar um ecossistema real de movimentações entre diferentes clientes.

- O acesso a uma conta exige Login com CPF e Senha que já tenham sido registrados e que estejam salvos na base de dados. Dessa forma, o programa se assemelha mais com um sistema bancário real, fornecendo uma barreira de segurança para cada conta.
  
- Dados de toda transação ficam salvos e o usuário pode gerar um extrato bancário .txt com todas as movimentações efetuadas na conta logada durante a execução. Cada conta pode gerar um extrato independente, apenas com os dados de movimentação bancária relativos àquele cliente. Dessa forma, é possível registrar o histórico detalhado de transações efetuadas durante a execução do código.

---

## Descrição do Sistema

Dados como Nome, CPF, Número de Conta e  Saldo ficam salvos em variáveis de instância na classe _Client_. Referência aos objetos de tipo _Client_ ficam salvos no ArrayList _clients_.

A classe _Client_ possui 4 métodos: 

***• Registro( )*** 

Cria uma nova conta (objeto do tipo _Client_). Dados de Nome, CPF e Senha são requisitados ao usuário e validados.

***• Login( )***

Acessa uma conta específica. Dados de CPF e Senha são requisitados ao usuário para login e, caso coincidam com um usuário registrado, acesso é liberado.

***• Transferência( )***

Transaciona valores entre contas. O valor a ser transacionado precisa ser superior ao do saldo atual do usuário logado. O usuário não pode enviar valores para si mesmo.
Os dados das movimentações ficam salvos em váriaveis de instância na classe Movimento (esta fica aninhada à classe _Client_). Refêrencias à objetos do tipo Movimento ficam salvos no ArrayList extrato.

***• getExtrato( )***

Gera um arquivo .txt com o registro de todas as movimentações feitas na conta que está logada com informações de Data e Hora da transferência/depósito, valor, partes envolvidas e CPF. Para isso foi importada a classe _PrintWriter_. Cada conta possui um extrato independente e ele pode ser atualizado múltiplas vezes durante a execução do sistema.


A classe _Menu_ possui os métodos que printam os menus interativos (_incializarMenu0_ e _inicializarMenu1_) e, baseado nas decisões do usário, chamam os métodos da classe _Client_.


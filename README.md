# Sobre o Projeto
Este sistema é um simulador de venda de ingressos desenvolvido para a disciplina de Programação Orientada a Objetos. O objetivo principal é demonstrar a gestão de concorrência em um ambiente multithread, garantindo a integridade dos dados (Thread Safety) e a persistência do estado da aplicação através de serialização.

## Tecnologias
* Java versão 25
* IDE: IntelliJ IDEA

## Estrutura de Pacotes
* venda_ingresso.entities: Contém as entidades do modelo de dados, como a classe Ingresso.
* venda_ingresso.enums: Contém o SetorEnum, responsável por centralizar regras de negócio como preços e limites de ocupação.
* venda_ingresso.exceptions: Exceções personalizadas do sistema, como SetorEsgotadoException.
* * venda_ingresso.main: Contém a classe Principal, responsável por inicializar o sistema e orquestrar a execução das threads.
* venda_ingresso.services: Contém a lógica de negócio, incluindo o GerenciadorIngresso (regras de venda), GerenciadorArquivo (persistência) e CompradorRunnable (tarefas das threads).
* venda_ingresso.ui: Classes responsáveis pela interface gráfica em Swing.

## Como Executar
1. Clone este repositório em sua máquina local: `git clone https://github.com/LauraMarinhoM/venda-ingresso-poo.git`
2. Abra o projeto na sua IDE (recomendado: IntelliJ IDEA).
3. Certifique-se de que o SDK do Java está configurado corretamente.
4. Navegue até o pacote "venda_ingresso.main" e execute a classe Principal.
5. Acompanhe a simulação das compras pelo console. Ao final do processamento das threads, a interface gráfica será aberta automaticamente.

## Conceitos Aplicados

1. Multithreading (Concorrência e Sincronização)
   O sistema gerencia múltiplos compradores (threads) tentando acessar e modificar a lista de ingressos ao mesmo tempo. Para evitar condições de corrida, utilizamos a palavra-chave "synchronized" no método "comprarIngresso()". Isso garante que apenas uma thread possa vender um ingresso por vez. Também utilizamos o método "join()" para aguardar as threads finalizarem e criamos uma Thread Daemon que roda em segundo plano.

2. Serialização (Persistência de Dados)
   A serialização foi utilizada para salvar o estado da lista de ingressos em um arquivo binário (ingressos.ser), permitindo que os dados sejam recuperados no futuro. Na classe Ingresso, utilizamos o modificador "transient" no atributo "threadOrigem" (ex: private transient String threadOrigem;). Isso garante que essa informação volátil da execução atual não seja gravada no disco.

## Branches
* main: Branch principal contendo o código final consolidado e estável.
* feature/enums: Branch onde foram implementados os conceitos de Enumeração (SetorEnum) e a integração dos preços dinâmicos.
* feature/threads: Branch focada na implementação de concorrência (CompradorRunnable), sincronização de métodos (synchronized) e a thread daemon.

## Autor
* Nome completo: Laura Marinho Mendes
* Turma: 3º Período - Técnologia em Sistemas para Internet
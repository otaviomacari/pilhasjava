# Verificação de Expressões com Pilha em Java

Projeto desenvolvido em **Java** para verificar se os símbolos de agrupamento de uma expressão estão corretamente balanceados.

A solução utiliza uma **Pilha (Stack)** implementada manualmente através de uma **lista encadeada**, permitindo praticar conceitos de estruturas de dados, classes, objetos, referências, métodos e manipulação de caracteres.

Os símbolos analisados são:

* `(` e `)`
* `[` e `]`
* `{` e `}`

O programa lê uma expressão digitada pelo usuário e informa se ela é válida ou inválida de acordo com a ordem dos símbolos de abertura e fechamento.

---

## Objetivo

O objetivo do projeto é aplicar o conceito de **Pilha (LIFO — Last In, First Out)** em um problema prático.

Durante a leitura da expressão:

* Símbolos de abertura são adicionados à pilha;
* Símbolos de fechamento fazem com que o último símbolo de abertura seja removido;
* O símbolo removido é comparado com o fechamento encontrado;
* Ao final, a pilha precisa estar vazia para que a expressão seja considerada válida.

---

# Conceito de Pilha

A estrutura utilizada segue o princípio:

> **LIFO — Last In, First Out**

Ou seja:

> O último elemento que entra é o primeiro elemento que sai.

Por exemplo, ao inserir:

```text
(
[
{
```

a pilha ficará:

```text
        TOPO
         ↓
        {
        [
        (
```

Se encontrarmos `}`, ele precisa corresponder ao `{`, que está no topo.

Depois da remoção:

```text
        TOPO
         ↓
        [
        (
```

Esse comportamento é fundamental para verificar se os símbolos de uma expressão estão corretamente organizados.

---

# Estrutura do Projeto

O código está concentrado em uma única classe:

```text
Pilhas.java
```

Dentro dela existem:

```text
Pilhas
 ├── No
 ├── Pilha
 ├── corresponde()
 ├── ehAbertura()
 ├── ehFechamento()
 ├── verificarExpressao()
 └── main()
```

---

# Classe `No`

A classe `No` representa um **nó da lista encadeada** utilizada para construir a pilha.

```java
private static class No {
    char dado;
    No prox;

    No(char dado, No prox) {
        this.dado = dado;
        this.prox = prox;
    }
}
```

Cada nó possui duas informações:

### `dado`

Armazena o caractere que está na pilha.

Exemplo:

```text
(
[
{
```

### `prox`

É uma referência para o próximo nó da pilha.

Assim, os nós formam uma estrutura encadeada:

```text
┌───────┐     ┌───────┐     ┌───────┐
│   {   │ ──► │   [   │ ──► │   (   │
│       │     │       │     │       │
└───────┘     └───────┘     └───────┘
```

---

# Classe `Pilha`

A classe `Pilha` representa a própria estrutura de dados.

```java
private static class Pilha {
    private No topo;
}
```

Ela possui apenas uma referência:

```text
topo
```

Essa referência indica qual é o elemento que está no topo da pilha.

Quando a pilha está vazia:

```text
topo → null
```

---

# Método `push()`

```java
void push(char valor) {
    topo = new No(valor, topo);
}
```

O método `push()` adiciona um novo caractere no topo da pilha.

O novo nó recebe:

* o caractere;
* uma referência para o antigo topo.

Por exemplo, inicialmente:

```text
topo → null
```

Após:

```java
push('(');
```

temos:

```text
topo
 ↓
┌─────┐
│  (  │
└─────┘
```

Depois:

```java
push('[');
```

fica:

```text
topo
 ↓
┌─────┐
│  [  │
└──┬──┘
   ↓
┌─────┐
│  (  │
└─────┘
```

O novo elemento sempre passa a ser o topo.

---

# Método `pop()`

```java
char pop() {
    if (isEmpty()) {
        return '\0';
    }

    char valor = topo.dado;
    topo = topo.prox;
    return valor;
}
```

O método `pop()` remove o elemento que está no topo e retorna seu valor.

Primeiro é verificado se a pilha está vazia:

```java
if (isEmpty()) {
    return '\0';
}
```

Caso exista um elemento, seu valor é armazenado:

```java
char valor = topo.dado;
```

Depois o topo passa a apontar para o próximo nó:

```java
topo = topo.prox;
```

Por fim, o valor removido é retornado:

```java
return valor;
```

---

# Método `peek()`

```java
char peek() {
    if (isEmpty()) {
        return '\0';
    }

    return topo.dado;
}
```

O método `peek()` consulta o elemento que está no topo **sem removê-lo**.

Embora esse método não seja utilizado diretamente na função de validação, ele faz parte da implementação tradicional de uma pilha.

Diferença:

```text
peek() → consulta o topo
pop()  → consulta e remove o topo
```

---

# Método `isEmpty()`

```java
boolean isEmpty() {
    return topo == null;
}
```

Verifica se a pilha está vazia.

Retorna:

```text
true  → pilha vazia
false → pilha possui elementos
```

---

# Método `corresponde()`

```java
private static boolean corresponde(char abertura, char fechamento) {
    return (abertura == '(' && fechamento == ')')
        || (abertura == '[' && fechamento == ']')
        || (abertura == '{' && fechamento == '}');
}
```

Esse método verifica se um símbolo de abertura corresponde ao símbolo de fechamento.

As combinações válidas são:

```text
( → )
[ → ]
{ → }
```

Por exemplo:

```text
( → )   verdadeiro
[ → ]   verdadeiro
{ → }   verdadeiro
( → ]   falso
[ → )   falso
```

---

# Método `ehAbertura()`

```java
private static boolean ehAbertura(char c) {
    return c == '(' || c == '[' || c == '{';
}
```

Verifica se o caractere é um símbolo de abertura.

São considerados:

```text
(
[
{
```

---

# Método `ehFechamento()`

```java
private static boolean ehFechamento(char c) {
    return c == ')' || c == ']' || c == '}';
}
```

Verifica se o caractere é um símbolo de fechamento.

São considerados:

```text
)
]
}
```

---

# Método `verificarExpressao()`

O método:

```java
public static boolean verificarExpressao(String exp)
```

é responsável pela lógica principal do programa.

Ele recebe uma `String` contendo a expressão e retorna:

```text
true  → expressão válida
false → expressão inválida
```

---

## 1. Verificação de `null`

Primeiro é verificado se a expressão recebida é `null`:

```java
if (exp == null) {
    return false;
}
```

Caso seja `null`, a expressão é considerada inválida.

---

## 2. Criação da pilha

Depois é criada uma nova pilha:

```java
Pilha pilha = new Pilha();
```

Ela começa vazia.

---

## 3. Percorrendo a expressão

O programa percorre a expressão da esquerda para a direita:

```java
for (int i = 0; i < exp.length(); i++) {
    char c = exp.charAt(i);
```

O método:

```java
exp.charAt(i)
```

obtém o caractere que está na posição `i`.

---

## 4. Encontrando uma abertura

Quando o programa encontra um símbolo de abertura:

```java
if (ehAbertura(c)) {
    pilha.push(c);
}
```

o caractere é colocado na pilha.

Por exemplo, na expressão:

```text
{[(
```

a pilha ficará:

```text
        TOPO
         ↓
        (
        [
        {
```

---

## 5. Encontrando um fechamento

Quando o programa encontra um símbolo de fechamento:

```java
else if (ehFechamento(c))
```

primeiro verifica se existe algum elemento na pilha:

```java
if (pilha.isEmpty()) {
    return false;
}
```

Se a pilha estiver vazia, significa que apareceu um fechamento sem uma abertura correspondente.

Por exemplo:

```text
)
```

Nesse caso, a expressão é inválida.

---

## 6. Removendo o topo

Se a pilha não estiver vazia:

```java
char topo = pilha.pop();
```

O último símbolo de abertura é removido.

Depois ele é comparado com o fechamento atual:

```java
if (!corresponde(topo, c)) {
    return false;
}
```

Se não houver correspondência, a expressão é inválida.

---

# Exemplo de expressão válida

Considere:

```text
{[()]}
```

O programa percorre:

```text
{
```

Adiciona à pilha:

```text
{
```

Depois:

```text
[
```

Pilha:

```text
[
{
```

Depois:

```text
(
```

Pilha:

```text
(
[
{
```

Ao encontrar:

```text
)
```

remove `(`.

Depois:

```text
]
```

remove `[`.

Por fim:

```text
}
```

remove `{`.

A pilha termina vazia:

```text
topo → null
```

Resultado:

```text
Expressao verdadeira!
```

---

# Exemplo de expressão inválida

Considere:

```text
{[(])}
```

A pilha inicialmente recebe:

```text
{
[
(
```

Quando o programa encontra:

```text
]
```

o topo da pilha é:

```text
(
```

Porém:

```text
( ≠ ]
```

Portanto, a ordem dos símbolos está incorreta e o método retorna:

```java
false
```

Resultado:

```text
Expressao falsa!
```

---

# Verificação final

Depois que toda a expressão foi percorrida:

```java
return pilha.isEmpty();
```

A pilha precisa estar vazia.

Isso é necessário porque pode existir uma abertura que nunca foi fechada.

Por exemplo:

```text
{[(]
```

Depois da análise, ainda haverá elementos na pilha.

Consequentemente:

```text
pilha.isEmpty()
```

retornará:

```text
false
```

e a expressão será considerada inválida.

---

# Método `main()`

O método `main()` é responsável pela interação com o usuário.

Primeiro é criado um objeto `Scanner`:

```java
Scanner scanner = new Scanner(System.in);
```

Depois o programa solicita uma expressão:

```java
System.out.print("Digite a expressao: ");
```

A entrada é armazenada:

```java
String expressao = scanner.nextLine();
```

Em seguida, a função de verificação é chamada:

```java
if (verificarExpressao(expressao))
```

Se retornar `true`:

```text
Expressao verdadeira!
```

Caso contrário:

```text
Expressao falsa!
```

Por fim, o `Scanner` é fechado:

```java
scanner.close();
```

---

# Fluxo do programa

```text
                   INÍCIO
                      │
                      ▼
             Usuário digita
                expressão
                      │
                      ▼
             Cria uma pilha
                      │
                      ▼
          Percorre a expressão
           da esquerda → direita
                      │
             ┌────────┴────────┐
             │                 │
         Abertura          Fechamento
             │                 │
             ▼                 ▼
           PUSH          Pilha está vazia?
                               │
                         ┌─────┴─────┐
                         │           │
                        Sim         Não
                         │           │
                         ▼           ▼
                     Inválida       POP
                                     │
                                     ▼
                              Correspondem?
                                     │
                               ┌─────┴─────┐
                               │           │
                              Não         Sim
                               │           │
                               ▼           ▼
                           Inválida      Continua
                                           │
                                           ▼
                                  Fim da expressão
                                           │
                                           ▼
                                    Pilha vazia?
                                           │
                                     ┌─────┴─────┐
                                     │           │
                                    Sim         Não
                                     │           │
                                     ▼           ▼
                                  Válida      Inválida
```

---

# Exemplos de execução

### Expressão válida

Entrada:

```text
Digite a expressao: {[()]}
```

Saída:

```text
Expressao verdadeira!
```

---

### Expressão inválida

Entrada:

```text
Digite a expressao: {[(])}
```

Saída:

```text
Expressao falsa!
```

---

### Expressão válida com outros caracteres

O programa ignora caracteres que não sejam `()`, `[]` ou `{}`.

Por exemplo:

```text
Digite a expressao: (a + b) * [c - d]
```

Resultado:

```text
Expressao verdadeira!
```

---

### Expressão com fechamento sem abertura

Entrada:

```text
Digite a expressao: a + b)
```

Resultado:

```text
Expressao falsa!
```

---

# Complexidade

Considerando uma expressão com `n` caracteres:

### Complexidade de tempo

A expressão é percorrida uma única vez:

```text
O(n)
```

Cada operação da pilha (`push`, `pop` e `isEmpty`) possui custo constante:

```text
O(1)
```

Portanto, a complexidade total é:

```text
O(n)
```

### Complexidade de espaço

No pior caso, todos os caracteres da expressão podem ser símbolos de abertura e precisarão ser armazenados na pilha.

Assim:

```text
O(n)
```

---

# Conceitos praticados

Este projeto trabalha diversos conceitos fundamentais de Java e Estruturas de Dados:

* Java;
* Classes;
* Classes internas `static`;
* Encapsulamento;
* Métodos;
* Objetos;
* Referências;
* Lista encadeada;
* Pilhas;
* Princípio LIFO;
* Ponteiros conceituais através de referências;
* Manipulação de `String`;
* `char`;
* `Scanner`;
* Estruturas condicionais;
* Laços de repetição;
* Complexidade de algoritmos.

---

# Diferença em relação à implementação em C

Uma característica interessante deste projeto é que a pilha não utiliza uma estrutura pronta da biblioteca Java.

Em vez de utilizar:

```java
Stack
```

ou:

```java
ArrayDeque
```

a pilha foi implementada manualmente utilizando a classe `No`.

A estrutura é equivalente conceitualmente a uma lista encadeada em C:

```text
C                         Java

struct No                 class No
char dado                 char dado
struct No* prox           No prox
malloc()                  new No()
NULL                      null
free()                    Garbage Collector
```

No Java, não é necessário utilizar `free()` manualmente. Quando um objeto deixa de possuir referências acessíveis, o **Garbage Collector** pode posteriormente liberar sua memória.

---

# Tecnologias

* **Java**
* **JDK**
* `Scanner`
* Estruturas de dados
* Lista encadeada
* Pilha

---

# Compilação e execução

Com o arquivo salvo como:

```text
Pilhas.java
```

compile utilizando:

```bash
javac Pilhas.java
```

Depois execute:

```bash
java Pilhas
```

O programa solicitará uma expressão:

```text
Digite a expressao:
```

Após a entrada, será exibido se a expressão é válida ou inválida.

---

# Conclusão

Este projeto demonstra uma aplicação prática da estrutura de dados **Pilha**, implementada manualmente em Java utilizando uma **lista encadeada**.

Através do princípio **LIFO — Last In, First Out**, os símbolos de abertura são armazenados e posteriormente comparados com os símbolos de fechamento encontrados na expressão.

A implementação permite compreender não apenas o funcionamento de uma pilha, mas também conceitos importantes da linguagem Java, como **classes internas, referências, objetos, encapsulamento, métodos e Garbage Collector**.

O algoritmo possui complexidade **O(n)** em tempo e **O(n)** em espaço, sendo adequado para verificar o balanceamento de delimitadores em expressões.

import java.util.Scanner;

public class Pilhas {
    // no da pilha: guarda o caractere e o link para o proximo elemento.
    private static class No {
        char dado;
        No prox;

        No(char dado, No prox) {
            this.dado = dado;
            this.prox = prox;
        }
    }

    // estrutura da pilha implementada com lista encadeada.
    private static class Pilha {
        private No topo;

        // insere um caractere no topo da pilha.
        void push(char valor) {
            topo = new No(valor, topo);
        }

        // remove e retorna o caractere do topo.
        char pop() {
            if (isEmpty()) {
                return '\0';
            }

            char valor = topo.dado;
            topo = topo.prox;
            return valor;
        }

        // consulta o topo sem remover.
        char peek() {
            if (isEmpty()) {
                return '\0';
            }

            return topo.dado;
        }

        boolean isEmpty() {
            return topo == null;
        }
    }

    // verifica se o par de simbolos combina corretamente.
    private static boolean corresponde(char abertura, char fechamento) {
        return (abertura == '(' && fechamento == ')')
            || (abertura == '[' && fechamento == ']')
            || (abertura == '{' && fechamento == '}');
    }

    // identifica simbolos de abertura.
    private static boolean ehAbertura(char c) {
        return c == '(' || c == '[' || c == '{';
    }

    // identifica simbolos de fechamento.
    private static boolean ehFechamento(char c) {
        return c == ')' || c == ']' || c == '}';
    }

    // percorre a string e valida se os simbolos estao balanceados.
    public static boolean verificarExpressao(String exp) {
        if (exp == null) {
            return false;
        }

        Pilha pilha = new Pilha();

        // lemos a expressao da esquerda para a direita.
        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);

            // aberturas ficam guardadas na pilha para conferencia futura.
            if (ehAbertura(c)) {
                pilha.push(c);
            // fechamentos precisam bater com o ultimo simbolo aberto.
            } else if (ehFechamento(c)) {
                if (pilha.isEmpty()) {
                    // nao existe abertura correspondente.
                    return false;
                }

                char topo = pilha.pop();
                if (!corresponde(topo, c)) {
                    // a ordem de fechamento esta incorreta.
                    return false;
                }
            }
        }

        // se sobrou algo na pilha, alguma abertura nao foi fechada.
        return pilha.isEmpty();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // le uma expressao digitada pelo usuario.
        System.out.print("Digite a expressao: ");
        String expressao = scanner.nextLine();

        // exibe o resultado da validacao.
        if (verificarExpressao(expressao)) {
            System.out.println("Expressao verdadeira!");
        } else {
            System.out.println("Expressao falsa!");
        }

        scanner.close();
    }
}

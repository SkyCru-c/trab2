import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class JogoDasPalavras {
    private static final int TEMPO_LIMITE_SEGUNDOS = 60;
    private static final int TAMANHO_MINIMO_PALAVRA = 4;
    private static final String DICIONARIO_ARQUIVO = "dictionary.txt";
    private static final Random RANDOM = new Random();
    private static final Scanner SCANNER = new Scanner(System.in);

    private HashSet<String> palavrasJaInformadas;
    private ArrayList<String> palavrasValidas;
    private char letraSorteada;

    public JogoDasPalavras() {
        palavrasJaInformadas = new HashSet<>();
        palavrasValidas = new ArrayList<>();
        letraSorteada = sortearLetra();
        carregarPalavrasValidas();
    }

    public char sortearLetra() {
        return (char) (RANDOM.nextInt(26) + 'a');
    }

    public boolean validarPalavra(String palavra) {
        return palavra.length() >= TAMANHO_MINIMO_PALAVRA && palavrasValidas.contains(palavra);
    }

    private void carregarPalavrasValidas() {
        try (Scanner scanner = new Scanner(getClass().getResourceAsStream(DICIONARIO_ARQUIVO))) {
            while (scanner.hasNextLine()) {
                palavrasValidas.add(scanner.nextLine().toLowerCase());
            }
        }
    }

    public void jogar() {
        System.out.println("Bem-vindo ao jogo das Quantas palavras diferentes eu conheço!");
        System.out.println("O objetivo do jogo é informar o maior número possível de palavras que começam com a letra " + letraSorteada + ".");
        System.out.println("Você tem " + TEMPO_LIMITE_SEGUNDOS + " segundos para informar as palavras.");
        System.out.println("Apenas são permitidas palavras válidas e diferentes com 4 ou mais letras.");
        System.out.println("Caso informe uma palavra repetida, você será informado.");

        long tempoInicio = System.currentTimeMillis();
        while ((System.currentTimeMillis() - tempoInicio) / 1000 < TEMPO_LIMITE_SEGUNDOS) {
            System.out.print("Informe uma palavra: ");
            String palavra = SCANNER.nextLine().toLowerCase();

            if (palavrasJaInformadas.contains(palavra)) {
                System.out.println("Palavra já informada. Tente outra.");
            } else if (!validarPalavra(palavra)) {
                System.out.println("Palavra inválida. Tente outra.");
            } else {
                palavrasJaInformadas.add(palavra);
            }
        }

        System.out.println("Fim do jogo!");
        System.out.println("Você informou " + palavrasJaInformadas.size() + " palavras diferentes:");
        for (String palavra : palavrasJaInformadas) {
            System.out.println(palavra);
        }
    }
}

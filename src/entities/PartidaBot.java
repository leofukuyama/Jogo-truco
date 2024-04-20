package entities;

import java.util.Random;
import java.util.Scanner;

public class PartidaBot {
    private Baralho baralho;
    private final Jogador jogadorHumano;
    private final Jogador computador;
    private final Jogador manilha;
    private int rodada;
    private int contTurno;
    private int pontJogador;
    private int pontComputador;
    private int contHumRod = 0;
    private int contCompRod = 0;
    private int pontTruco = 1;
    private boolean pedidoTruco = true;
    private boolean pedidoTrucoNove = false;
    private final int pontFinalJogo = 12;

    public PartidaBot(Jogador jogadorHumano) {
        this.jogadorHumano = jogadorHumano;
        this.computador = new Jogador("Computador");
        this.manilha = new Jogador("Manilha");
        this.baralho = new Baralho();
        this.contTurno = 1;
        this.rodada = 1;
        this.pontJogador = 0;
        this.pontComputador = 0;
        iniciarRodada();
    }

    private void iniciarRodada() {
        while (determinarVencedorJogo()) {
            System.out.println("Turno " + this.contTurno);

            this.manilha.receberCarta(this.baralho.retirarCarta());

            for (int i = 0; i < 3; i++) {
                this.jogadorHumano.receberCarta(this.baralho.retirarCarta());
                this.computador.receberCarta(this.baralho.retirarCarta());
            }

            jogar();
        }

        if(pontJogador >= this.pontFinalJogo) {
            System.out.println("Parabéns! Você ganhou!");
        }
        else {
            System.out.println("Tente novamente! O computador ganhou!");
        }

        System.out.println("Fim de jogo! Obrigado por jogar!");
    }

    private void jogar() {
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < 3; i++) {
            System.out.println("Rodada " + this.rodada);

            System.out.println("Vira do turno: " + this.manilha.getMao().getFirst());

            this.jogadorHumano.mostrarMao();
            this.computador.mostrarMao();

            int contTruco = -1;
            if (this.pedidoTruco) {
                System.out.println("Quer pedir truco? (y/n)");
                String querTruco = scanner.next().toLowerCase();

                while (!querTruco.equals("y") && !querTruco.equals("n")) {
                    System.out.println("Digite novamente: (y/n)");
                    querTruco = scanner.next().toLowerCase();
                }

                if (querTruco.equals("y")) {
                    contTruco = pedirTruco();
                }
            }

            if (this.pedidoTrucoNove) {
                System.out.println("Quer pedir 9? (y/n)");
                String querNove = scanner.next().toLowerCase();

                while (!querNove.equals("y") && !querNove.equals("n")) {
                    System.out.println("Digite novamente: (y/n)");
                    querNove = scanner.next().toLowerCase();
                }

                if (querNove.equals("y")) {
                    contTruco = pedirTrucoNove();
                }
            }

            if (contTruco == 0 || contTruco == 3) {
                System.out.println("Você venceu o turno!");
                this.pontJogador += this.pontTruco;
                break;
            }
            else if (contTruco == 1 || contTruco == 2) {
                System.out.println("O computador venceu o turno!");
                this.pontComputador += this.pontTruco;
                break;
            }

            if (this.rodada == 1) {
                System.out.println("Sua vez de jogar uma carta: (1, 2, 3)");
            }
            else if (this.rodada == 2) {
                System.out.println("Sua vez de jogar uma carta: (1, 2)");
            }
            else if (this.rodada == 3) {
                System.out.println("Sua vez de jogar uma carta: (1)");
            }

            int cartaJogada = scanner.nextInt();

            if (this.rodada == 1) {
                while (!(cartaJogada == 1) && !(cartaJogada == 2) && !(cartaJogada == 3)) {
                    System.out.println("Digite novamente: (1, 2, 3)");
                    cartaJogada = scanner.nextInt();
                }
            }
            else if (this.rodada == 2) {
                while (!(cartaJogada == 1) && !(cartaJogada == 2)) {
                    System.out.println("Digite novamente: (1, 2)");
                    cartaJogada = scanner.nextInt();
                }
            }
            else if (this.rodada == 3) {
                while (!(cartaJogada == 1)) {
                    System.out.println("Digite novamente: (1)");
                    cartaJogada = scanner.nextInt();
                }
            }

            Carta cartaHum = jogadorHumano.getMao().get(cartaJogada - 1);
            Carta cartaComp = computador.getMao().getFirst();
            System.out.println("Você jogou: " + cartaHum);
            System.out.println("Computador jogou: " + cartaComp);

            computador.getMao().removeFirst();
            jogadorHumano.getMao().remove(cartaJogada - 1);

            determinarVencedorRodada(cartaHum, cartaComp);

            if (this.contHumRod == 2) {
                System.out.println("Você venceu o turno!");
                this.pontJogador += this.pontTruco;
                break;
            }
            else if (this.contCompRod == 2) {
                System.out.println("O computador venceu o turno!");
                this.pontComputador += this.pontTruco;
                break;
            }
        }

        System.out.println("Pontuação da partida:");
        System.out.println(this.jogadorHumano + ": " + this.pontJogador);
        System.out.println(this.computador + ": " + this.pontComputador);

        this.contTurno += 1;
        this.contHumRod = 0;
        this.contCompRod = 0;
        this.pontTruco = 1;
        this.pedidoTruco = true;
        comecarNovoTurno();
    }

    private void determinarVencedorRodada(Carta cartaHum, Carta cartaComp) {
        int pontHumRod = 0;
        int pontCompRod = 0;
        int valorCartaHum = cartaHum.getCharValor();
        int valorCartaComp = cartaComp.getCharValor();
        int valorCartaManilha = this.manilha.getMao().getFirst().getCharValor();

        if (valorCartaManilha == 10) {
            valorCartaManilha = 0;
        }

        if (valorCartaHum == valorCartaManilha + 1) {
            valorCartaHum += 10;
        }
        if (valorCartaComp == valorCartaManilha + 1) {
            valorCartaComp += 10;
        }

        if (valorCartaHum > valorCartaComp) {
            pontHumRod = 1;
        }
        else if (valorCartaHum < valorCartaComp) {
            pontCompRod = 1;
        }
        else {
            if (cartaHum.getNaipe() > cartaComp.getNaipe()) {
                pontHumRod = 1;
            }
            else {
                pontCompRod = 1;
            }
        }

        if (pontHumRod > pontCompRod) {
            System.out.println("Você venceu a rodada!");
            contHumRod += 1;
        }
        else {
            System.out.println("O computador venceu a rodada!");
            contCompRod += 1;
        }

        this.rodada += 1;
        System.out.println("Fim da rodada!");
    }

    private void comecarNovoTurno() {
        this.jogadorHumano.removerMao();
        this.computador.removerMao();
        this.manilha.removerMao();
        this.rodada = 1;
        this.baralho = new Baralho();
    }

    private boolean determinarVencedorJogo() {
        return this.pontJogador < this.pontFinalJogo && this.pontComputador < this.pontFinalJogo;
    }

    private int pedirTruco() {
        Scanner scanner = new Scanner(System.in);
        Random gerador = new Random();

        this.pedidoTruco = false;

        System.out.println(this.jogadorHumano + ": TRUCO!");

        int escolhaBot = gerador.nextInt(100);
        if (escolhaBot < 20) {
            System.out.println("Computador: 6!");
            this.pontTruco = 3;

            System.out.println("Deseja aceitar? (y/n)");
            String respostaSeis = scanner.next().toLowerCase();

            while (!respostaSeis.equals("y") && !respostaSeis.equals("n")) {
                System.out.println("Digite novamente: (y/n)");
                respostaSeis = scanner.next().toLowerCase();
            }

            if (respostaSeis.equals("y")) {
                this.pontTruco = 6;
                this.pedidoTrucoNove = true;
            }
            else {
                System.out.println(this.jogadorHumano + ": Recuso!");
                return 1;
            }
        }
        else if (escolhaBot < 65) {
            System.out.println("Computador: Aceito!");
            this.pontTruco = 3;
        }
        else {
            System.out.println("Computador: Corro!");
            this.pontTruco = 1;
            return 0;
        }
        return -1;
    }

    private int pedirTrucoNove() {
        Scanner scanner = new Scanner(System.in);
        Random gerador = new Random();

        this.pedidoTrucoNove = false;

        System.out.println(this.jogadorHumano + ": NOVE!");

        int escolhaBot = gerador.nextInt(100);
        if (escolhaBot < 20) {
            System.out.println("Computador: 12!");
            this.pontTruco = 9;

            System.out.println("Deseja aceitar? (y/n)");
            String respostaDoze = scanner.next().toLowerCase();

            while (!respostaDoze.equals("y") && !respostaDoze.equals("n")) {
                System.out.println("Digite novamente: (y/n)");
                respostaDoze = scanner.next().toLowerCase();
            }

            if (respostaDoze.equals("y")) {
                this.pontTruco = 12;
            }
            else {
                System.out.println(this.jogadorHumano + ": Recuso!");
                return 2;
            }
        }
        else if (escolhaBot < 65) {
            System.out.println("Computador: Aceito!");
            this.pontTruco = 9;
        }
        else {
            System.out.println("Computador: Corro!");
            this.pontTruco = 6;
            return 3;
        }
        return -1;
    }
}

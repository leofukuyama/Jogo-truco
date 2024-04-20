package application;

import entities.Jogador;
import entities.PartidaBot;

public class Main {
    public static void main(String[] args) {
        System.out.println("BEM VINDO AO JOGO DE TRUCO!");

        Jogador jogador = new Jogador("Leonardo");
        PartidaBot partidaBot = new PartidaBot(jogador);
    }
}

package entities;

import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private final String nome;
    private List<Carta> mao;

    public Jogador(String nome) {
        this.nome = nome;
        this.mao = new ArrayList<>();
    }

    public void receberCarta(Carta carta) {
        this.mao.add(carta);
    }

    public List<Carta> getMao() {
        return this.mao;
    }

    public void removerMao() {
        this.mao = new ArrayList<>();
    }

    public void mostrarMao() {
        int contador = 0;
        System.out.println(nome + " tem na mão:");
        for (Carta carta : this.mao) {
            contador += 1;
            System.out.println(contador + " - " + carta.toString());
        }
    }

    public String toString() {
        return this.nome;
    }
}

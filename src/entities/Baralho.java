package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Baralho {
    private final List<Carta> cartas;

    public Baralho() {
        this.cartas = new ArrayList<>();
        String[] valores = {"4", "5", "6", "7", "Q", "J", "K", "A", "2", "3"};

        for (Naipe naipe : Naipe.values()) {
            for (String valor : valores) {
                this.cartas.add(new Carta(valor, naipe));
            }
        }

        embaralhar();
    }

    private void embaralhar() {
        Collections.shuffle(this.cartas);
    }

    public Carta retirarCarta() {
        if (this.cartas.isEmpty()) {
            return null;
        }
        return this.cartas.removeFirst();
    }
}

package entities;

public class Carta {
    private final String valor;
    private final Naipe naipe;

    public Carta(String valor, Naipe naipe) {
        this.valor = valor;
        this.naipe = naipe;
    }

    public char getCharValor() {
        return switch (this.valor) {
            case "4" -> 1;
            case "5" -> 2;
            case "6" -> 3;
            case "7" -> 4;
            case "Q" -> 5;
            case "J" -> 6;
            case "K" -> 7;
            case "A" -> 8;
            case "2" -> 9;
            case "3" -> 10;
            default -> 'X';
        };
    }

    public int getNaipe() {
        return switch (this.naipe) {
            case OURO -> 1;
            case ESPADAS -> 2;
            case COPAS -> 3;
            case PAUS -> 4;
        };
    }

    public String toString() {
        return valor + " de " + naipe;
    }
}

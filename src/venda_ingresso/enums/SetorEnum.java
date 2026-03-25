package venda_ingresso.enums;

public enum SetorEnum {
    AMARELO("Amarelo", 180.00),
    AZUL("Azul", 100.00),
    BRANCO("Branco", 60.00),
    VERDE("Verde", 350.00);

    private final String nome;
    private final double valor;

    SetorEnum(String nome, double valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public String getNome() { return nome; }
    public double getValor() { return valor; }

    @Override
    public String toString() { return nome; }

}

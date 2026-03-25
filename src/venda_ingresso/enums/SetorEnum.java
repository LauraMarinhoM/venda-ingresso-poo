package venda_ingresso.enums;

public enum SetorEnum {
    AMARELO("Amarelo", 180.00, 10),
    AZUL("Azul", 100.00, 20),
    BRANCO("Branco", 60.00, 30),
    VERDE("Verde", 350.00, 5);

    private final String nome;
    private final double valor;
    private final int limiteIngressos;

    SetorEnum(String nome, double valor, int limiteIngressos) {
        this.nome = nome;
        this.valor = valor;
        this.limiteIngressos = limiteIngressos;
    }

    public String getNome() { return nome; }
    public double getValor() { return valor; }
    public int getLimiteIngressos() { return limiteIngressos; }

    @Override
    public String toString() { return nome; }

}

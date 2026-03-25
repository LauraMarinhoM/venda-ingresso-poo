package venda_ingresso.services;

import venda_ingresso.entities.Ingresso;
import venda_ingresso.enums.SetorEnum;
import java.time.LocalDateTime;

public class CompradorRunnable implements Runnable {
    private final String nome;
    private final SetorEnum setor;
    private final int quantidade;
    private final GerenciadorIngresso gerenciador;

    public CompradorRunnable(String nome, SetorEnum setor, int quantidade, GerenciadorIngresso gerenciador) {
        this.nome = nome;
        this.setor = setor;
        this.quantidade = quantidade;
        this.gerenciador = gerenciador;

    }

    @Override
    public void run() {
        try {
            // Requisito M02: Simula latência de rede
            Thread.sleep(50);

            Ingresso ingresso = new Ingresso();
            ingresso.setNome(this.nome);
            ingresso.setSetor(this.setor.getNome());
            ingresso.setQuantidade(this.quantidade);
            ingresso.setValor(this.setor.getValor());
            ingresso.setValorTotal(this.setor.getValor() * this.quantidade);
            ingresso.setDataHora(LocalDateTime.now());


            gerenciador.comprarIngresso(ingresso);

        } catch (InterruptedException e) {
            // Requisito M02: Trata interrupção conforme boa prática
            Thread.currentThread().interrupt();
        }
    }
}

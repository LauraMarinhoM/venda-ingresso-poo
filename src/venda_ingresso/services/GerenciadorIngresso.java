/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package venda_ingresso.services;

import venda_ingresso.entities.Ingresso;
import venda_ingresso.exceptions.SetorEsgotadoException;
import java.util.ArrayList;

/**
 *
 * @author Junior
 */
public class GerenciadorIngresso {

    private ArrayList<Ingresso> ingressos;
    private int prox = 0; // R10: Removido o static

    public GerenciadorIngresso() {
        ingressos = new ArrayList<>();
    }

    public boolean comprarIngresso(Ingresso ingresso) {
        // R09: Limite de 10 por setor
        long count = ingressos.stream()
                .filter(i -> i.getSetor().equals(ingresso.getSetor()))
                .count();

        if (count >= 10) {
            throw new SetorEsgotadoException("Setor " + ingresso.getSetor() + " esgotado!");
        }

        ingresso.setCodigo(++prox);
        return ingressos.add(ingresso);
    }

    public ArrayList<Ingresso> getIngressos() { return ingressos; }

    public void setIngressos(ArrayList<Ingresso> ingressos) {
        this.ingressos = ingressos;
        this.prox = ingressos.size();
    }
}
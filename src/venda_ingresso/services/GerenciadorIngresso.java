/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package venda_ingresso.services;

import venda_ingresso.entities.Ingresso;
import venda_ingresso.enums.SetorEnum;
import venda_ingresso.exceptions.SetorEsgotadoException;
import java.util.ArrayList;

/**
 *
 * @author Junior
 */
public class GerenciadorIngresso {

    private ArrayList<Ingresso> ingressos;
    private int prox = 0;

    public GerenciadorIngresso() {
        ingressos = new ArrayList<>();
    }

    public synchronized boolean comprarIngresso(Ingresso ingresso) {

        SetorEnum setorEnum = null;
        for (SetorEnum s : SetorEnum.values()) {
            if (s.getNome().equalsIgnoreCase(ingresso.getSetor())) {
                setorEnum = s;
                break;
            }
        }


        long count = ingressos.stream()
                .filter(i -> i.getSetor().equals(ingresso.getSetor()))
                .count();

        if (setorEnum != null && count >= setorEnum.getLimiteIngressos()) {
            throw new SetorEsgotadoException("Setor " + ingresso.getSetor() + " esgotado!");
        }

        ingresso.setThreadOrigem(Thread.currentThread().getName());

        ingresso.setCodigo(++prox);
        return ingressos.add(ingresso);
    }

    public synchronized ArrayList<Ingresso> getIngressos() {
        // Retorna uma nova lista para evitar erros de modificação concorrente
        return new ArrayList<>(this.ingressos);
    }

}
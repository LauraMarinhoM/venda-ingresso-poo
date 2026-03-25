/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package venda_ingresso.services;

import venda_ingresso.entities.Ingresso;
import venda_ingresso.enums.SetorEnum;
import venda_ingresso.exceptions.IngressoInexistenteException;
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

    public void setIngressos(ArrayList<Ingresso> ingressos) {
        this.ingressos = ingressos;
    }

    public Ingresso buscarIngresso(String nome) {
        // Percorre a lista de ingressos procurando pelo nome
        for (Ingresso ingresso : ingressos) {
            if (ingresso.getNome().equalsIgnoreCase(nome)) {
                return ingresso;
            }
        }
        throw new IngressoInexistenteException("Erro: Nenhum ingresso encontrado no nome de '" + nome + "'.");
    }
}
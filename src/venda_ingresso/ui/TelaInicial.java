/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package venda_ingresso.ui;

import venda_ingresso.entities.Ingresso;
import venda_ingresso.services.GerenciadorArquivo;
import venda_ingresso.services.GerenciadorIngresso;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author Junior
 */
public class TelaInicial extends JFrame {

    private JButton btnGerarRelatorio;
    private JButton btnComprar;
    private JPanel painelFundo;
    private GerenciadorIngresso gerenciador;

    public TelaInicial() {
        this.gerenciador = new GerenciadorIngresso();


        List<Ingresso> listaCarregada = GerenciadorArquivo.desserializar("ingressos.ser");
        if (listaCarregada != null && !listaCarregada.isEmpty()) {
            this.gerenciador.setIngressos(new ArrayList<>(listaCarregada));
        }

        criarComponentesTela();
    }

    private void criarComponentesTela() {
        btnComprar = new JButton("Comprar Ingresso");
        btnGerarRelatorio = new JButton("Gerar Relatório");

        btnComprar.addActionListener((e) -> {
            JanelaCadastroIngresso janela = new JanelaCadastroIngresso(this, true, gerenciador);
            janela.setVisible(true);
        });

        btnGerarRelatorio.addActionListener((e) -> {
            JanelaGrafica janelaGrafica = new JanelaGrafica(this, true, gerenciador);
            janelaGrafica.imprimirRelatorio(gerenciador.getIngressos());
            janelaGrafica.setVisible(true);

            GerenciadorArquivo.exportarTxt(gerenciador.getIngressos(), "relatorio.txt");
        });

        painelFundo = new JPanel();
        painelFundo.add(btnComprar);
        painelFundo.add(btnGerarRelatorio);

        add(painelFundo);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setSize(300, 200);
        setVisible(true);
    }
}
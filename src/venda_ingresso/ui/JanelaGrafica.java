/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package venda_ingresso.ui;

import venda_ingresso.entities.Ingresso;
import venda_ingresso.services.GerenciadorIngresso;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Junior
 */
public class JanelaGrafica extends JDialog {

    private JPanel painelFundo;
    private JTable tabelaIngressos;
    private JScrollPane scroll;
    private DefaultTableModel modelo;
    private GerenciadorIngresso gerenciador;

    public JanelaGrafica(Frame parent, boolean isModal, GerenciadorIngresso gerenciador) {
        super(parent, isModal);
        this.gerenciador = gerenciador;
        modelo = new DefaultTableModel();
        criarTabela();
        criarComponentes();
    }

    private void criarTabela() {
        tabelaIngressos = new JTable(modelo);

        // Adicionando as colunas conforme a estrutura de dados
        modelo.addColumn("Cód");
        modelo.addColumn("Nome");
        modelo.addColumn("Setor");
        modelo.addColumn("Tipo"); // Nova Coluna para Meia/Inteira
        modelo.addColumn("Qtd");
        modelo.addColumn("Valor Unit.");
        modelo.addColumn("Total");
        modelo.addColumn("Data e Hora");

        // Ajuste de larguras para melhor visualização
        tabelaIngressos.getColumnModel().getColumn(0).setPreferredWidth(30);
        tabelaIngressos.getColumnModel().getColumn(1).setPreferredWidth(120);
        tabelaIngressos.getColumnModel().getColumn(3).setPreferredWidth(60);
    }

    private void criarComponentes() {
        scroll = new JScrollPane(tabelaIngressos);
        painelFundo = new JPanel(new BorderLayout());
        painelFundo.add(scroll, BorderLayout.CENTER);

        add(painelFundo);

        setTitle("Relatório de Ingressos Vendidos");
        pack();
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void imprimirRelatorio(ArrayList<Ingresso> ingressos) {
        modelo.setNumRows(0);

        for (Ingresso c : ingressos) {

            String tipo = c.isMeiaEntrada() ? "Meia" : "Inteira";

            modelo.addRow(new Object[]{
                    c.getCodigo(),
                    c.getNome(),
                    c.getSetor(),
                    tipo,
                    c.getQuantidade(),
                    String.format("R$ %.2f", c.getValor()),
                    String.format("R$ %.2f", c.getValorTotal()),
                    c.getDataHora()
            });
        }
    }
}
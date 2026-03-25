/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package venda_ingresso.ui;

import venda_ingresso.entities.Ingresso;
import venda_ingresso.enums.SetorEnum;
import venda_ingresso.services.GerenciadorArquivo;
import venda_ingresso.services.GerenciadorIngresso;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

/**
 *
 * @author Junior
 */
public class JanelaCadastroIngresso extends JDialog {
    private JTextField txtNome, txtQtde;
    private JComboBox<SetorEnum> cbxSetores;
    private JCheckBox chkMeia; // Campo para Meia-Entrada
    private JButton btnSalvar;
    private GerenciadorIngresso gerenciador;

    public JanelaCadastroIngresso(Frame parent, boolean modal, GerenciadorIngresso gerenciador) {
        super(parent, modal);
        this.gerenciador = gerenciador;
        initComponents();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GerenciadorArquivo.serializar(gerenciador.getIngressos(), "ingressos.ser");
            }
        });
    }

    private void initComponents() {

        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel(" Nome do Comprador:"));
        txtNome = new JTextField();
        add(txtNome);

        add(new JLabel(" Setor:"));
        cbxSetores = new JComboBox<>(SetorEnum.values()); // R12: Uso do Enum
        add(cbxSetores);

        add(new JLabel(" Quantidade:"));
        txtQtde = new JTextField();
        add(txtQtde);

        add(new JLabel(" Meia-entrada:"));
        chkMeia = new JCheckBox("Aplicar 50% de desconto");
        add(chkMeia);

        btnSalvar = new JButton("Finalizar Compra");
        btnSalvar.addActionListener(e -> executarCompra());
        add(btnSalvar);

        setTitle("Cadastro de Venda");
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void executarCompra() {
        try {

            int qtd = Integer.parseInt(txtQtde.getText());
            SetorEnum setorSelecionado = (SetorEnum) cbxSetores.getSelectedItem();
            boolean ehMeia = chkMeia.isSelected();


            double valorUnitario = ehMeia ? setorSelecionado.getValor() / 2.0 : setorSelecionado.getValor();

            Ingresso novo = new Ingresso();
            novo.setNome(txtNome.getText());
            novo.setSetor(setorSelecionado.getNome());
            novo.setMeiaEntrada(ehMeia);
            novo.setValor(valorUnitario);
            novo.setQuantidade(qtd);
            novo.setValorTotal(valorUnitario * qtd);
            novo.setDataHora(LocalDateTime.now());


            if (gerenciador.comprarIngresso(novo)) {
                JOptionPane.showMessageDialog(this, "Venda realizada com sucesso!\nValor Total: R$ " + novo.getValorTotal());

                // R13: Garante o salvamento imediato após sucesso
                GerenciadorArquivo.serializar(gerenciador.getIngressos(), "ingressos.ser");
                dispose();
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro: A quantidade deve ser um número inteiro válido.");
        } catch (RuntimeException ex) {
            // Captura QuantidadeInvalidaException e SetorEsgotadoException
            JOptionPane.showMessageDialog(this, "Atenção: " + ex.getMessage());
        }
    }
}
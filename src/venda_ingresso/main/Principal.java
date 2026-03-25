/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package venda_ingresso.main;

import venda_ingresso.ui.TelaInicial;
import venda_ingresso.enums.SetorEnum;
import venda_ingresso.services.CompradorRunnable;
import venda_ingresso.services.GerenciadorArquivo;
import venda_ingresso.services.GerenciadorIngresso;

/**
 *
 * @author Junior
 */
public class Principal {

    public static void main(String[] args) {

        GerenciadorIngresso gerenciador = new GerenciadorIngresso();

        //M06
        Thread daemonSalvar = new Thread(() -> {
            try {
                while (true) {
                    // Serializa a lista atual de ingressos
                    GerenciadorArquivo.serializar(gerenciador.getIngressos(), "ingressos.ser");
                    Thread.sleep(500); // Aguarda 500ms
                }
            } catch (InterruptedException e) {
                // Requisito M06: Encerrada com interrupt()
                System.out.println("Daemon de salvamento interrompida com sucesso.");
            }
        });

        daemonSalvar.setDaemon(true); // Define como Daemon
        daemonSalvar.start();

        // M05
        Thread t1 = new Thread(new CompradorRunnable("Ana", SetorEnum.VERDE, 1, gerenciador), "Thread-Ana");
        Thread t2 = new Thread(new CompradorRunnable("Beto", SetorEnum.AZUL, 2, gerenciador), "Thread-Beto");
        Thread t3 = new Thread(new CompradorRunnable("Carla", SetorEnum.AMARELO, 1, gerenciador), "Thread-Carla");
        Thread t4 = new Thread(new CompradorRunnable("Dani", SetorEnum.BRANCO, 3, gerenciador), "Thread-Dani");

        // Inicia as threads de compradores
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            //M05: Aguarda com join() antes de seguir
            t1.join();
            t2.join();
            t3.join();
            t4.join();

            //M06: Encerrar a daemon após o join() de todos
            daemonSalvar.interrupt();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Abre a interface gráfica para ver o resultado final
        java.awt.EventQueue.invokeLater(() -> {
            new TelaInicial().setVisible(true);
        });
    }


    
}

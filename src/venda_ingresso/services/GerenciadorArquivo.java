package venda_ingresso.services;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import venda_ingresso.entities.Ingresso;

public class GerenciadorArquivo {

    public static void serializar(List<Ingresso> lista, String path) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        } finally {
            System.out.println("Operação de escrita finalizada.");
        }
    }

    public static List<Ingresso> desserializar(String path) {
        File arquivo = new File(path);
        if (!arquivo.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            return (List<Ingresso>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            System.out.println("Operação de leitura finalizada.");
        }
    }

    public static void exportarTxt(List<Ingresso> lista, String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write("RELATÓRIO DE VENDAS\n");
            writer.write("===================\n");
            for (Ingresso i : lista) {
                writer.write(String.format("Cód: %d | Nome: %s | Setor: %s | Total: R$ %.2f\n",
                        i.getCodigo(), i.getNome(), i.getSetor(), i.getValorTotal()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

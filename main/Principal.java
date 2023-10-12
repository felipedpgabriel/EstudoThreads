package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Principal
{
    private static final int NUM_CLIENTES = 3;
    private static final int PORT = 11111;
    private static ArrayList<Thread> clientes = new ArrayList<Thread>();
    public static void main(String[] args) throws InterruptedException, IOException
    {        
        System.out.println("Inicia Principal.");
        // Abre canal de conexao
        ServerSocket serverSocket = new ServerSocket(PORT);
        // Instancia Servidor
        Thread servidor = new Servidor(serverSocket);
        servidor.start();
        // Cria clientes
        for(int i=1;i<=NUM_CLIENTES;i++)
        {
            Cliente cliente = new Cliente("Cliente" + i, PORT); // IMP adicionar host individual para cada
            clientes.add(cliente);
            cliente.start();
        }
        // Condicional o fim do programa ao fim da vida dos clientes
        aguardaThreads(clientes);
        // Encerra programa
        System.out.println("Encerrando Principal");
        serverSocket.close();

        System.exit(0);
    }

    /**Roda o metodo join em todos as Threads de uma lista
     * @param lista
     * @throws InterruptedException
     */
    private static void aguardaThreads(ArrayList<Thread> lista) throws InterruptedException
    {
        for(int i=0;i<lista.size();i++)
        {
            lista.get(i).join();
        }
    }
}

package main;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

public class Principal
{
    private static final int numClients = 2;
    private static String inTxt = "";
    private static final String keyClose = "sair";
    private static int port = 11111;
    public static void main(String[] args) throws IOException, InterruptedException
    {
        System.out.println("Inicia a bagaca!");

        // 1 - Abre conexao
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Porta " + port + " aberta!");

        ConnectCS cs = new ConnectCS(serverSocket, numClients);
        System.out.println("Aguardando clientes...");

        for(int i=0;i<numClients;i++)
        {
            System.out.println("Criando cliente...");
            Cliente cliente = new Cliente("127.0.0." + (i+1), port, "Cliente" + (i+1));
            System.out.println(cliente.getNome() + " | " + cliente.getServIP() +" criado!");
            // try {
            //     Thread.sleep(100);
            // } catch (Exception e) {
            //     // TODO: handle exception
            // }
        }

        cs.join();

        System.out.println("Encerrando programa...");
        serverSocket.close(); // conexao

        System.out.println("Fim do programa!");
    }
    
}


package main;

import java.net.ServerSocket;
import java.net.Socket;

public class ConnectCS extends Thread
{
    private ServerSocket ss;
    private int numClients;
    private Socket socket;

    public ConnectCS(ServerSocket ss, int numClients) {
        this.ss = ss;
        this.numClients = numClients;
        this.start();
    }

    @Override
    public void run()
    {
        try 
        {
            for(int i=0;i<numClients;i++)
            {
                // 2 - Aguarda cliente
                System.out.println("Aguardando conexao...");
                socket = ss.accept();
                System.out.println("Cliente conectado!");
                // 3 - Cria uma thread para cuidar de cada
                Thread t = new Servidor(socket);
                t.start();
                t.join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Encerrando conexoes...");
    }
}

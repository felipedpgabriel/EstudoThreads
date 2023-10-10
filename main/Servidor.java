package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Classe para testar Threads por meio da interface Runnable
 */
public class Servidor implements Runnable
{
    // private static ServerSocket server;
    private Socket socket;

    public Servidor(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public void run()
    {
        try
        {
            System.out.println("Aguardadno mensagem...");
            // 4 - Define entrada do servidor
            DataInputStream entrada = new DataInputStream(socket.getInputStream());
            String mensagem = entrada.readUTF();
            String novaMensagem = mensagem = mensagem.toUpperCase();

            System.out.println("Devolvendo mensagem...");
            // 5 - Define saida do servidor
            DataOutputStream saida = new DataOutputStream(socket.getOutputStream());
            saida.writeUTF(novaMensagem);

            System.out.println("Encerrando...");
            // 6 - Fecha streams de entrada e saida e sockets de comunicacao e conexao
            entrada.close();
            saida.close();
            socket.close(); // comunicacao    
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}

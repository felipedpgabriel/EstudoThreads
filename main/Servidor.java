package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Classe para testar Threads por meio da interface Runnable
 */
public class Servidor extends Thread
{
    // private static ServerSocket server;
    private Socket socket;
    private DataInputStream entrada;
    private DataOutputStream saida;
    private static boolean terminada = false;
    private static int countRot = 1;

    public Servidor(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public void run()
    {
        while(!socket.isClosed())
        {
            try
            {
                System.out.println("Aguardando solicitacao...");
                // 4 - Define entrada do servidor
                entrada = new DataInputStream(socket.getInputStream());
                String mensagem = entrada.readUTF();
                if(mensagem.equals("rota"))
                {
                    System.out.println("Liberando rota...");
                    this.liberarRota();
                }
                else if(mensagem.equals("encerrar"))
                {
                    System.out.println("Encerrando canal...");
                    socket.close();
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        try {
            System.out.println("Encerrando...");
            // 6 - Fecha streams de entrada e saida e sockets de comunicacao e conexao
            entrada.close();
            saida.close();
            if (!socket.isClosed())
            {
                socket.close(); // comunicacao
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            terminada = true;
        }
    }

    public synchronized void liberarRota() throws InterruptedException
    {
        // 5 - Define saida do servidor
        try {
            sleep(5000);
            saida = new DataOutputStream(socket.getOutputStream());
            saida.writeUTF("rota " + countRot);
            countRot++;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static boolean isTerminada() {
        return terminada;
    }
}
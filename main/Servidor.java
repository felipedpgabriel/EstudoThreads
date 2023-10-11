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
    private static int countRot = 1;
    private static boolean rotListAcessed = false;
    private String msgOut = "";


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
                System.out.println("Aguardando mensagem...");
                // 4 - Define entrada do servidor
                String msgIn = read();
                if(msgIn.equals("rota"))
                {
                    System.out.println("Rota solicitada!");
                    do
                    {
                        if(rotListAcessed && !msgOut.equals("ocupado"))
                        {
                            System.out.println("Liberador de rotas ocupado");
                            write("ocupado");
                        }
                        else if(!rotListAcessed)
                        {
                            rotListAcessed = true;
                            System.out.println("Liberando rota...");
                            this.liberarRota();
                        }
                    }while(msgOut.equals("ocupado"));
                }
                else if(msgIn.equals("recebido"))
                {
                    System.out.println("Confirmacao de rota recebida!");
                    rotListAcessed = false;
                    notifyAll();
                }
                else if(msgIn.equals("encerrar"))
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
    }

    public synchronized void liberarRota() throws InterruptedException
    {
        // 5 - Define saida do servidor
        try {
            sleep(4000);
            this.write("rota " + countRot);
            countRot++;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private String read() throws IOException
    {
        entrada = new DataInputStream(socket.getInputStream());
        return this.entrada.readUTF();
    }

    private void write(String texto) throws IOException
    {
        saida = new DataOutputStream(socket.getOutputStream());
        this.saida.writeUTF(texto);
        this.msgOut = texto;
    }    
}
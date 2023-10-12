package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor extends Thread
{
    // atributos de servidor
    private ServerSocket serverSocket;
    // atributos de sincronizacao
    private Object monitor = new Object();
    private static boolean liberado = true;
    // atributos da classe
    private static int countRotas = 1;

    public Servidor(ServerSocket serverSocket) // serverSocket passado como parametro evita erros ao encerrar a Thread
    {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run()
    {
        try
        {
            System.out.println("Servidor iniciado...");

            while (true) // IMP tentar trocar para !socket.isClosed() -> instanciá-la antes
            {
                // conecta os clientes -> IMP mudar para ser feito paralelamente (ou n)
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado");

                Thread clienteThread = new Thread(() -> 
                { // lança uma thread para comunicacao -> IMP criar uma classe separada para melhor organizacao
                    try
                    {
                        // variaveis de entrada e saida do servidor
                        DataInputStream entrada = new DataInputStream(socket.getInputStream());
                        DataOutputStream saida = new DataOutputStream(socket.getOutputStream());

                        String mensagem = "";
                        while(!mensagem.equals("encerrado")) // loop do sistema
                        {
                            mensagem = entrada.readUTF(); // lê solicitacao do cliente
                            if (mensagem.equals("rota"))
                            {
                                synchronized (monitor)
                                {
                                    String resposta;
                                    if (isLiberado())
                                    {
                                        liberado = false;
                                        System.out.println("Liberando rota...");
                                        resposta = liberarRotas();
                                    }
                                    else
                                    {
                                        System.out.println("Ocupado, aguarde a rota.");
                                        resposta = "ocupado";
                                    }
                                    saida.writeUTF(resposta);
                                    if (!resposta.equals("ocupado"))
                                    {
                                        System.out.println("Aguardando fim da corrida...");
                                        mensagem = entrada.readUTF(); // Aguarda confirmação do cliente
                                        if (mensagem.equals("recebido"))
                                        {
                                            liberado = true;
                                            System.out.println("Confirmacao de corrida iniciada.");
                                            monitor.notify(); // Notifica o próximo cliente
                                        }
                                    }
                                }
                            }
                            else if (mensagem.equals("encerrado"))
                            {
                                break;
                            }
                        }

                        System.out.println("Encerrando canal.");
                        entrada.close();
                        saida.close();
                        socket.close();
                        // serverSocket.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                });
                clienteThread.start();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**Define se o trecho de acesso sincronizadao esta liberado
     * @return liberado - boolean
     */
    private boolean isLiberado()
    {
        synchronized (monitor)
        {
            return liberado;
        }
    }

    /**Monta uma String para servir de rota
     * @return rota - String
     */
    private String liberarRotas()
    {
        synchronized (monitor)
        {
            try
            {
                Thread.sleep(3000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            String rota = "rota " + countRotas;
            countRotas++;
            return rota;
        }
    }
}

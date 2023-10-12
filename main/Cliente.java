package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente extends Thread
{
    // atributos de cliente
    private Socket socket;
    private int servPort;
    // IMP private String cliHost;
    private DataInputStream entrada;
    private DataOutputStream saida;
    // atributos de sincronizacao
    private boolean ocupado = false;
    private Object monitor = new Object(); // sincronizacao
    // atributos da classe
    private String nome;

    public Cliente(String nome, int servPort) { // IMP adc host e depois javadoc
        this.nome = nome;
        this.servPort = servPort;
        // start(); IMP analisar necessidade
    }

    @Override
    public void run() {
        try {
            // Estabelece conexão com o servidor
            socket = new Socket("localhost", servPort); // IMP modificar o host (unico para cada)
            entrada = new DataInputStream(socket.getInputStream());
            saida = new DataOutputStream(socket.getOutputStream());

            for (int i = 0; i < 2; i++)
            { // loop e sua condicional
                synchronized (monitor) 
                { // aguarda
                    while (ocupado) 
                    {
                        monitor.wait();
                    }
                    // Solicita uma rota ao servidor
                    System.out.println(nome + " solicitando rota..."); // DEBUG
                    saida.writeUTF("rota");
                    String resposta = entrada.readUTF();
                    if (resposta.equals("ocupado")) 
                    { // IMP nunca entrou aqui
                        ocupado = true;
                        System.out.println(nome + ": Aguardando rota..."); // DEBUG
                        monitor.wait(); // Aguarda até ser notificado pelo servidor
                    } 
                    else 
                    {
                        System.out.println(nome + ": "+ resposta +" recebida. Dirigindo..."); // DEBUG
                        saida.writeUTF("recebido");
                        dirigir();
                        // monitor.notify();
                    }
                }
            }

            // Envia mensagem de encerramento ao servidor
            System.out.println("Encerrando " + nome);
            saida.writeUTF("encerrado");

            // Fecha as conexões
            entrada.close();
            saida.close();
            socket.close();
        } 
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    /**Simula a execucao de uma rota de transito (gasta um tempo fazendo algo)
     * @throws InterruptedException
     */
    private void dirigir() throws InterruptedException
    {
        for (int i = 12; i >= 0; i--)
        {
            System.out.println(nome + ": " + i);
            sleep(500);
        }
        ocupado = false;
        synchronized (monitor)
        {
            monitor.notify(); // Notifica o servidor que está livre
        }
    }
}


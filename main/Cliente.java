package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.xml.crypto.Data;

/**
 * Classe para testar Threads por meio da heranca a classe Thread
 */
public class Cliente extends Thread
{
    private String servIP;
    private int servPort;
    private String nome;
    private String msgOut = "";
    private final String keyClose = "encerrar";
    private DataOutputStream saida;
    private Socket socket;
    private DataInputStream entrada;
    private int rotas = 0;

    public Cliente(String servIP, int servPort, String nome)
    {
        this.servIP = servIP;
        this.servPort = servPort;
        this.nome = nome;
        this.start();
    }

    @Override
    public void run()
    {
        // conceta om o servidor
        try
        {
            System.out.println("Inicia conexao");
            socket = new Socket(servIP, servPort);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        while(!msgOut.equals(keyClose))
        {
            try {
                while(rotas < 2)
                {
                    System.out.println(this.nome + " solicitando rota...");
                    this.write("rota"); // solicita rota
                    while(!msgOut.equals("recebido")) // aguarda ate receber e executar a rota
                    {
                        System.out.println(this.nome + " aguardando rota...");
                        String msgIn = this.read();
                        if(msgIn.equals("ocupado"))
                        {
                            System.out.println("Aguardando liberacao...");
                            synchronized(this)
                            {
                                wait();
                            }
                        }
                        else
                        {
                            this.write("recebido");
                            System.out.println(this.nome + " => " + msgIn + " recebida!");
                            this.dirigir();
                        }
                    }
                }
                write("encerrar");
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        try {
            System.out.println("Encerrando " + getNome() + "...");
            entrada.close();
            saida.close();
            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void dirigir() throws InterruptedException
    {
        for(int i=12;i>0;i--)
        {
            System.out.println(getNome() + ": " + i);
            sleep(500);
        }
        rotas++;
    }

    private void write(String texto) throws IOException
    {
        saida = new DataOutputStream(socket.getOutputStream());
        this.saida.writeUTF(texto);
        this.msgOut = texto;
    }

    private String read() throws IOException
    {
        entrada = new DataInputStream(socket.getInputStream());
        return this.entrada.readUTF();
    }

    public String getNome() {
        return nome;
    }

    public String getServIP() {
        return servIP;
    }

    public int getServPort() {
        return servPort;
    }
}

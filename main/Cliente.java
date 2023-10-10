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
    private boolean estaSuspensa = false;
    private boolean foiTerminada = false;
    private String inTxt = "";
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

        while(!inTxt.equals(keyClose))
        {
            synchronized(this)
            {
                while(estaSuspensa)
                {
                    try {
                        wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(this.foiTerminada)
                {
                    break;
                }
            }

            try {
                while(rotas < 2)
                {
                    System.out.println(this.nome + " solicitando rota...");
                    write("rota");
                    System.out.println(this.nome + " => " + read() + " recebida!");
                    this.dirigir();
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

    public void write(String texto) throws IOException
    {
        saida = new DataOutputStream(socket.getOutputStream());
        this.saida.writeUTF(texto);
        this.inTxt = texto;
    }

    public String read() throws IOException
    {
        entrada = new DataInputStream(socket.getInputStream());
        return this.entrada.readUTF();
    }

    public void suspender()
    {
        this.estaSuspensa = true;
        System.out.println("Cliente " + this.nome + "suspenso!");
    }

    public synchronized void resumir()
    {
        this.estaSuspensa = false;
        notify();
        System.out.println("Cliente " + this.nome + "on!");
    }

    public synchronized void parar()
    {
        this.foiTerminada = true;
        // notify();
        System.out.println("Cliente " + this.nome + " encerrado!");
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

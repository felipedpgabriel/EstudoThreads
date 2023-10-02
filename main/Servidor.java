package main;

/**
 * Classe para testar Threads por meio da interface Runnable
 */
public class Servidor implements Runnable
{
    private int senha;
    private int tempo;

    public Servidor(int senha, int tempo)
    {
        this.senha = senha;
        this.tempo = tempo;
        // Thread t = new Thread(this);
        // t.start();
    }

    @Override
    public void run()
    {
        try
        {
            for(int i=0;i<6;i++)
            {
                System.out.println("Teste de Servidor " + i + ".");
                Thread.sleep(tempo);
            }
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        System.out.println("Servidor " + this.senha + " terminado");

    }

    public int getSenha()
    {
        return this.senha;
    }

    public void setSenha(int newSenha)
    {
        this.senha = newSenha;
    }
}

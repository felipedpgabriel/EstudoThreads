package main;

/**
 * Classe para testar Threads por meio da interface Runnable
 */
public class Servidor implements Runnable
{
    private String nome;
    private int tempo;
    private boolean alguemAbastecendo = false;

    public Servidor(String nome, int tempo)
    {
        this.nome = nome;
        this.tempo = tempo;
        // Thread t = new Thread(this);
        // t.start();
    }

    @Override
    public void run()
    {
        teste();
    }

    public void teste()
    {
        try
        {
            for(int i=0;i<6;i++)
            {
                System.out.println("Servidor " + i + ".");
                Thread.sleep(tempo);
            }
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.out.println("Deu bo no metodo teste da thread Servidor " + this.nome);
        }

        System.out.println("Teste de servidor " + this.nome + " terminado!");
    }

    public String getNome()
    {
        return this.nome;
    }

    public synchronized int abastecer(int tanque, String nome)
    {
        try {
            System.out.println("Cliente " + nome + " abastecendo.");
            setAbastecimento(true);
            Thread.sleep(2000);
            tanque += 5;
        } catch (Exception e) {
            System.out.println("Deu B.O. no sleep do abastecimento");
        }
        System.out.println("Cliente " + nome + " terminou de abastecer.");
        setAbastecimento(false);
        // notifyAll();

        return tanque;
    }

    public boolean temAlguemAbastecendo()
    {
        return this.alguemAbastecendo;
    }

    public void setAbastecimento(boolean estado)
    {
        this.alguemAbastecendo = estado;
    }

    
}

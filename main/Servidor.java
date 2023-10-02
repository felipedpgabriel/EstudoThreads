package main;

/**
 * Classe para testar Threads por meio da interface Runnable
 */
public class Servidor implements Runnable
{
    private String nome;
    private int senha;
    private int tempo;
    private int soma;

    public Servidor(String nome, int senha, int tempo)
    {
        this.nome = nome;
        this.senha = senha;
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
                System.out.println("Teste de Servidor " + i + ".");
                Thread.sleep(tempo);
            }
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.out.println("Deu bo no metodo teste da thread Servidor " + this.nome);
        }

        System.out.println("Servidor " + this.senha + " terminado!");
    }

    public int getSenha()
    {
        return this.senha;
    }

    public void setSenha(int newSenha)
    {
        this.senha = newSenha;
    }

    public String getNome()
    {
        return this.nome;
    }

    public int getSoma()
    {
        return this.soma;
    }

    public synchronized int somar(String nome)
    {
        soma = 0;
        for(int i=0;i<6;i++)
        {
            soma += i;
            System.out.println("Soma do cliente " + nome + " = " + soma);
            try {
                Thread.sleep(200);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        return soma;
    }
}

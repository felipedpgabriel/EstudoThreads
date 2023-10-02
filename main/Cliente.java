package main;
/**
 * Classe para testar Threads por meio da heranca a classe Thread
 */
public class Cliente extends Thread
{
    private String nome;
    private int idC;
    private int tempo;
    private int total = 0;
    private static Servidor serv = new Servidor("01", 12345, 900);

    public Cliente(String nome, int idC, int tempo)
    {
        this.nome = nome;
        this.idC = idC;
        this.tempo = tempo;
        start();
    }

    @Override
    public void run()
    {
        // teste();
        this.total = serv.somar(this.nome);
        System.out.println("Resultado de " + this.nome + " = " + this.total + ".");
    }

    /** 
     * Executa o metodo join(), ja com a estrutura try-catch
     * */
    public void holdOn()
    {
        try {
            join();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Deu bo no holdON da thread Cliente" + this.nome);
        }
    }

    public void teste()
    {
        try
        {
            for(int i=0;i<6;i++)
            {
                System.out.println("Teste de Cliente " + this.getName() + " " + i + ".");
                sleep(tempo);
            }
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.out.println("Deu bo no metodo teste da thread Cliente" + this.nome);
        }

        System.out.println("Cliente " + this.idC + " terminado");
    }

    public int getidC()
    {
        return this.idC;
    }

    public String getNome()
    {
        return this.nome;
    }

    public int getTotal()
    {
        return total;
    }
}

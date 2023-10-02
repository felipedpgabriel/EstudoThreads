package main;
/**
 * Classe para testar Threads por meio da heranca a classe Thread
 */
public class Cliente extends Thread
{
    private int idC;
    private int tempo;

    public Cliente(int idC, int tempo)
    {
        this.idC = idC;
        this.tempo = tempo;
        start();
    }

    @Override
    public void run()
    {
        try
        {
            for(int i=0;i<6;i++)
            {
                System.out.println("Teste de Cliente " + i + ".");
                sleep(tempo);
            }
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        System.out.println("Cliente " + this.idC + " terminado");
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
        }
    }

    public int getidC()
    {
        return this.idC;
    }
}

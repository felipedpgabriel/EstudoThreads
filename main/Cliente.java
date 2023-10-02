package main;
/**
 * Classe para testar Threads por meio da heranca a classe Thread
 */
public class Cliente extends Thread
{
    private String nome;
    private int tempo;
    private static Servidor serv = new Servidor("01", 900);
    private int tanque = 20;
    private int countAbast = 0;

    public Cliente(String nome, int tempo)
    {
        this.nome = nome;
        this.tempo = tempo;
        start();
    }

    @Override
    public void run()
    {
        while(this.countAbast < 3)
        {
            this.dirigir();
            if (this.tanque == 0)
            {
                if(serv.temAlguemAbastecendo())
                {
                    try {
                        System.out.println("Cliente " + this.nome + " aguardando abastecimento.");
                        while(serv.temAlguemAbastecendo())
                        {
                            wait();
                        }
                    } catch (Exception e) {
                        
                    }
                }
                int newTanque = serv.abastecer(this.tanque, this.nome);
                this.setTanque(newTanque);
                countAbast ++;
            }
        }
        System.out.println("Cliente " + this.nome + " terminou.");
        // teste();
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

        System.out.println("Cliente " + this.nome + " terminado");
    }

    public String getNome()
    {
        return this.nome;
    }

    public void dirigir()
    {
        try {
            sleep(tempo);
            tanque -= 1;
            System.out.println("Tanque " + this.nome + " = " + this.tanque + ".");
        } catch (Exception e) {
            System.out.println("Deu B.O. no metodo dirigir() da Thread Cliente " + this.nome + ".");
        }
    }

    public void setTanque(int newTanque)
    {
        this.tanque = newTanque;
    }
}

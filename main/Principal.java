package main;

public class Principal
{
    public static void main(String[] args)
    {
        System.out.println("Inicia a bagaca!");
        Cliente cliente1 = new Cliente("#01",1001,300);
        Cliente cliente2 = new Cliente("#02",1002,450);
        // Servidor servidor = new Servidor("#01",12345,900);

        // Thread t = new Thread(servidor);
        // t.start();
        // try {
        //     t.join();
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

        System.out.println("Fim do programa!");
    }
    
}


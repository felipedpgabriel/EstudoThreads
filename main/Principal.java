package main;

public class Principal
{
    public static void main(String[] args)
    {
        System.out.println("Inicia a bagaca!");
        Cliente cliente = new Cliente(10010,300);
        Servidor servidor = new Servidor(12345,900);
        cliente.start();

        cliente.holdOn();

        Thread t = new Thread(servidor);
        t.start();

        try {
            t.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Fim do programa!");
    }
    
}


package main;

import java.lang.Thread.State;

public class Principal
{
    public static void main(String[] args)
    {
        System.out.println("Inicia a bagaca!");
        Cliente cliente1 = new Cliente("#01", 300);
        Cliente cliente2 = new Cliente("#02",350);
        Cliente cliente3 = new Cliente("#03",400);

        try {
            cliente1.suspender();
            System.out.println("Inicio=>#01: "+cliente1.getState());
            Thread.sleep(2000);
            cliente2.suspender();
            System.out.println("Estagio1 => #02: "+cliente2.getState());
            cliente1.resumir();
            cliente3.parar();
            System.out.println("Estagio1 => #01: "+cliente1.getState());
            Thread.sleep(500);
            System.out.println("teste delay => #01: "+cliente1.getState());
            Thread.sleep(2000);
            cliente2.resumir();
            System.out.println("Estagio2 => #02: "+cliente2.getState());
            Thread.sleep(500);
            System.out.println("teste delay => #02: "+cliente2.getState());
            cliente1.parar();
            System.out.println("Cliente 1 state: "+cliente1.getState());
            // if(cliente2.getState() == State.TERMINATED && cliente3.getState() == State.TERMINATED)
            // {
            //     System.out.println(cliente2.getState() + " " + cliente3.getState());
            //     cliente1.parar();
            //     System.out.println(cliente1.getState());
            // }
            cliente1.join();
            cliente2.join();
            cliente3.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("Fim do programa!");
    }
    
}


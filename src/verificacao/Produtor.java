package verificacao;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Produtor extends Thread 
{

    Buffer buffer;
    int id;
    int espera;
    Random r = new Random();
    
    //a classe produtor recebe os valores vindos do main
    public Produtor(Buffer buffer, int id, int espera) 
    {
        this.buffer = buffer;
        this.id = id;
        this.espera = espera;
    }

    @Override
    public void run() 
    {
    	//verifica se há espaço disponível no buffer e se estiver adiciona o valor nele
        while (true) 
        {
            int valor = r.nextInt(15);

            if (!buffer.Livre()) {
                buffer.inserirItem(valor);

                System.out.println("Produtor " + id + " criou " + valor);
            }

            try {
                sleep(r.nextInt(espera));
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}

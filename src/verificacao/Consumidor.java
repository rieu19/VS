package verificacao;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Consumidor extends Thread 
{

	Buffer buffer;
	int id;
	int espera;
	Random r = new Random();
	
	//reebendo os valores do main
	public Consumidor(Buffer buffer, int id, int espera) 
	{
		this.buffer = buffer;
		this.id = id;
		this.espera = espera;
	}
	
	@Override
	public void run() 
	{
		//verifica se tem conteudo no buffer e, se tiver, pega o valor.
		while (true) 
		{
			if (buffer.Livre()) 
			{
				int valor = buffer.removeItem();
			
				System.out.println("Consumidor " + id + " pegou " + valor);
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

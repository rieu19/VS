package verificacao;
import java.util.concurrent.Semaphore;



public class Main 
{
	//quantidade de consumidores e produtores
	static int c = 20;
	static int p = 5;
	
	//tempo de espera mámixo para a thread dormir
	static int espera = 2000;
	
	public static void main(String[] args)
	{
		//criação dos semáforos e do mutex
		Semaphore semaProdutor = new Semaphore(10);
        Semaphore semaConstrutor = new Semaphore(10);
        Semaphore mutex = new Semaphore(1);
        Buffer buffer = new Buffer(10, semaProdutor, semaConstrutor, mutex);
		
// instanciamento de classes consumidoras e produtoras de acordo com a quantidade selecionada pelo usuario
 //na variavel
        for (int i = 0; i < c; i++)
        {
        	Consumidor consumidor = new Consumidor(buffer,i, espera);
        	consumidor.start();
        }
        
        for (int i = 0; i < p; i++) 
        {
        	Produtor produtor = new Produtor(buffer, i, espera);
        	produtor.start();
        }
                
        
	}

}

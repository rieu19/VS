package verificacao;
import java.util.concurrent.Semaphore;

public class Buffer 
{

    Semaphore semaProdutor;
    Semaphore semaConsumidor;
    Semaphore mutex;
    int vetorValores[];
    int tail, head, capacidade;
    boolean disponivel;

    public Buffer(int capacidade, Semaphore semaProdutor, Semaphore semaConsumidor, Semaphore mutex) 
    {
        this.capacidade = capacidade;
        vetorValores = new int[capacidade];
        this.semaProdutor = semaProdutor;
        this.semaConsumidor = semaConsumidor;
        this.mutex = mutex;
        tail = 0;
        head = 0;
        disponivel = false;
    }

    //insere um valor no buffer, atraves do produtor
    public void inserirItem(int valor) {
        try{
            semaProdutor.acquire();
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if (checagem(head) != tail) 
        {
            vetorValores[head] = valor;
            head = checagem(head);
        }
        semaProdutor.release();
    }
    //remove um dos valores que estava no buffer pelo consumidor
    public int removeItem() 
    {
    	int valor = 0;
        
        try{
            semaConsumidor.acquire();
            tail = checagem(tail);
            
            if (tail == 0) {
                valor = vetorValores[capacidade - 1];
            } else 
            {
                valor = vetorValores[tail - 1];
            }
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        semaConsumidor.release();
        
        return valor;
    }
    
    //Verifica se o semaforo ainda permite acesso 
    public boolean Livre() 
    {
       boolean temporariaLivre = false;
        
        try {
            semaProdutor.acquire();
            temporariaLivre = tail != head;
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        semaProdutor.release();
        
        return temporariaLivre;
    }

    //checa se o mutex ainda permite acesso
    int checagem(int x) 
    {
        int chec = 0;
        
        try {
            mutex.acquire();
            if (x + 1 >= capacidade) 
            {
                chec = 0;
            } else {
                chec = x + 1;
            }
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
           
        	e1.printStackTrace();
        }
        mutex.release();

        return chec;
    }
}
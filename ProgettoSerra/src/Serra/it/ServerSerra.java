package Serra.it;
import java.net.ServerSocket; 	// Socket che si mette in ascolto
import java.net.Socket;			// Server che accetta connessione
import java.util.Collections;
import java.util.Vector;
import java.io.BufferedReader; 	// Per leggere Stream (1/2)
import java.io.InputStreamReader; // Per leggere Stream (2/2)
import java.io.PrintWriter; 	// Per scrivere Stream

public class ServerSerra implements Runnable {

	private static ServerSocket ss;
	private static boolean GoAway;	// serve per bloccare ciclo infinito
	public Vector accesospento;
	public Vector luminositastanza;
	public Vector<Integer> v;
	public Vector c;

	public ServerSerra(int PORT) throws Exception {
		//creazione serra
		c=new Vector();  
		accesospento();  
		luminositastanza();
		ss = new ServerSocket(PORT); 	// server in ascolto di connessioni
		System.out.println("TCP Server Listening on Port: " + PORT + "..");
	}
	//la mia serra è costituita da un vettore che contiene se è acceso o spento e da un vettore che contiene il valore delle di illuminazione delle singole stanze
	public void accesospento()
	{
		accesospento=new Vector(7);
		for(int i=0;i<8;i++)
		{
			accesospento.addElement(0);
			
		}
		
	}
	public void luminositastanza()
	{
		luminositastanza=new Vector(7);
		for(int i=0;i<8;i++)
		{
			luminositastanza.addElement(0);
			
		}
		
	}
	
	public void stampaserra()
	{
		for(int i=0;i<8;i++)
		{
			System.out.println("SERRA N"+":"+(i+1)+"    ACCESO/SPENTO:"+accesospento.elementAt(i)+"     ||    VALORE ILLUMINAZIONE:"+luminositastanza.elementAt(i));
		}
		
	}
	

	@Override
	public void run() {
		GoAway = true;
		Socket s = null;
		BufferedReader r = null; 	// oggetto che legge lo stream
		PrintWriter w = null; 	// oggetto che scrive sullo stream
		String strData; 			// messaggio che leggo o che scrivo sullo stream

		for (; GoAway;) {
			try {
				s = ss.accept(); 	// accetto la connessione

				// la lettura dello stream è affidata a due oggetti
				r = new BufferedReader(new InputStreamReader(s.getInputStream()));
				// la scrittura ad uno solo NB: importante il parametro TRUE!!
				w = new PrintWriter(s.getOutputStream(), true);

				strData = r.readLine();
				//ho una stringa devo spezzarla in due
				v=new Vector(); //inserisco gli 8  valori della serra su un vettore 
				
				String valoriserra=strData.substring(0,8); //prendo i primi 8 valori della stringa che mi dicono se una stanza è accesa spenta
				String luminosita=strData.substring(8,strData.length()); //i restanti valori che corrispondono alla luminosità li metto in una variabile intera eseguendo un casting
				for(int i=0;i<valoriserra.length();i++)
				{
						v.addElement(Integer.parseInt(valoriserra.substring(i,(i+1)))); //inserisco nel vettore ogni singolo bit(0,1) per poi succesivamente verificare se acceso o spento
					
				}
				
				
				//ora devo verificare che le stanze siano accese o spente e se sono accese corrispondergli il valore della luminosità
				for(int i=0;i<v.size();i++)
				{
					if(v.elementAt(i)==1)
					{
						accesospento.add(i,1 );
						luminositastanza.add(i,luminosita);
						
					}

				}
				//stampa serra modificata
				stampaserra(); 
				
				//System.out.println("[recive] " + s.getRemoteSocketAddress() + " << "+strData+  "..");
				// scrivo sullo stream e ripulisco lo stream con il flush
				//w.println(strData);
				w.flush();

				//System.out.println("[sendTo] " + s.getRemoteSocketAddress() + " >> " + strData+ "..");

				// chiudo sia r, w che il socket in quanto alla prossima connessione li riapro
				r.close();
				w.close();
				s.close();
			} catch (Exception e) {
				System.err.println("Messaggio di errore");
			}
		}
	}

	public static void main(String[] args) throws Exception {
		new Thread(new ServerSerra((args.length > 0) ? Integer.parseInt(args[0]) : 12345)).start();
	}
}


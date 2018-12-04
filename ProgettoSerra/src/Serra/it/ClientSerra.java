/* P ROGETTO DEL PROFESSOR BIAGIO ROSARIO GRECO 
 * GRUPPO: BIASI FRANCESCO, ALESSANDRINI LUCA ,TESSERIN DAVID
 * TIPOLOGIA DI SOCKET: DATAGRAM SOCKET
 * 
 */
package Serra.it;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

public class ClientSerra {
	
	public ClientSerra(String HOST, int PORT, String MSG) throws Exception {
		DatagramSocket s = new DatagramSocket();
		s.send(new DatagramPacket(MSG.getBytes(), MSG.length(), InetAddress.getByName(HOST), PORT));
		System.out.println(HOST + ":" + PORT + " send >> " + MSG);

		s.close();
	}
	

	public static void main(String[] args) throws Exception {
		Vector v=new Vector(7); //dichiarazione vettore utile per memorizzare la seconda parte del messaggio 
		final String HOST = (args.length > 0) ? args[0] : "localhost";
		final int PORT = (args.length > 1) ? Integer.parseInt(args[1]) : 12345;
		Scanner Scan=new Scanner(System.in); 
		System.out.println("NB: LE STANZE DELLA SERRA SONO NUMERATO DALLA NUMERO 1 ALLA NUMERO 8");
		
		String stanzaonoff = "";
		System.out.println("QUALE STANZA DESIDERA ACCENDERE?(PREGO INSERISCA IL NUMERO DI STANZA SEPARATA DALLO SPAZIO ");
		String s=Scan.nextLine(); //lettura delle stanze che l utente vuole accendere, separate dallo spazio 
		System.out.println("PREGO INSERISCA UNA LUMINOSITA COMPRESA TRA 0 E 255 DA APPLICARE SULLE STANZE: ");
		String illuminazione=Scan.nextLine(); //lettura della luminosità 
		String MSG="";
		String temp="";
		StringTokenizer st=new StringTokenizer(s);
		String i="";
		
		for(int j=0;j<8;j++)  //creazione di un vettore vuoto per impostare la seconda parte del messaggio a tutti 0
		{
			v.addElement(0);
			//System.out.println("POSIZIONE "+(j+1)+":"+v.elementAt(j)); //stampa per verifica			
		}
		
		while(st.hasMoreTokens())  //prelevo ogni numero inserio dall utente e imposto la cella del vettore corrispondente a 1 cioè accendendola
		{
			i=st.nextToken();
			int a=Integer.valueOf(i);
			v.add((a-1), 1);
			//System.out.println(a);
		}
		
		for(int j=0;j<8;j++)
		{
			temp=temp+String.valueOf(v.elementAt(j)); //memorizzo ogni singolo valore del vettore su una stringa cosi da ottenere 8 bit
			MSG=illuminazione+temp; //concateno con l illuminazione cosi da formare un messaggio da 16 bit
			System.out.println("POSIZIONE "+(j+1)+":"+v.elementAt(j));//stampa di verifica
			
		}
		MSG.getBytes();
		//System.out.println(MSG);
		new ClientSerra(HOST, PORT, MSG);	
	}
}


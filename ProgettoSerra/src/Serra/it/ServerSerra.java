package Serra.it;

//Server MultiThreaD
import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class ServerSerra implements Runnable {
	final DatagramSocket ss;
	final DatagramPacket pkg;
	private boolean goAway = true;

	public ServerSerra(int PORT) throws Exception {
		pkg = new DatagramPacket(new byte[256],256);
		ss = new DatagramSocket(PORT);
		System.out.println("UDP Server Listening on Port: " + PORT +"..");
	}

	@Override
	public void run() {
		goAway = true;
		String data = "";

		for( ; goAway; ) {
			try {
				ss.receive(pkg);
				data = new String(pkg.getData(), 0, pkg.getLength()).trim();
				
				System.out.println(pkg.getAddress() + ":" + pkg.getPort() + " >> " + data + "..");
			} catch(Exception e) {
				System.err.println(e);
			}
		}
	}

	public void stop() {
		goAway = true;
	}

	public static void main(String[] args) throws Exception {
		final int PORT = (args.length > 0) ? Integer.parseInt(args[0]) : 12345;

		new Thread(new ServerSerra(PORT)).start();
	}
}

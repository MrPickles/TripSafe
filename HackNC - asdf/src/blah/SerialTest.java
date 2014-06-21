package blah;

// add external jar c/programfiles86/lib --> rxtxcomm

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.util.Enumeration;

import javax.swing.JOptionPane;

public class SerialTest implements SerialPortEventListener {
	SerialPort serialPort;
	/** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { "/dev/tty.usbserial-A9007UX1", // Mac
																				// OS
																				// X
			"/dev/ttyUSB0", // Linux
			"COM1",
			"COM2",
			"COM5", // Windows
			"COM3",
			"COM4",
			"COM6",
			"COM7",
			"COM8",
			"COM9",
			"COM10",
			"COM11",
			"COM12",
	};
	/**
	 * A BufferedReader which will be fed by a InputStreamReader converting the
	 * bytes into characters making the displayed results codepage independent
	 */
	private BufferedReader input;
	/** The output stream to the port */
	private OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;

	public void initialize() {
		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		// First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum
					.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(
					serialPort.getInputStream()));
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * This should be called when you stop using the port. This will prevent
	 * port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String inputLine = input.readLine();
//				System.out.println(inputLine + "asdf");
				
				isTemp = false;
				
				try {
					Integer.parseInt(inputLine);
				} catch(NumberFormatException e) { // if there are characters (letters)
					isTemp = true;
				}
				if (isTemp) {
					checkTemp(inputLine);
				} else {
					checkLaser(inputLine);
				}
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		// Ignore all the other eventTypes, but you should consider the other
		// ones.
	}
	
	private static boolean isTemp; // temp or photo reading
	
	static final int MAJICNUMBAH = 500;
	static boolean occurances = false;
	static boolean laserIsConnected = false;
	//static int counter = 0;
	static boolean yofo = false;
	static long qwe = 0L;
	
	static void checkLaser(String inputLine) {
		try{
			int x = Integer.parseInt(inputLine);
			
			if (x < MAJICNUMBAH) {
				laserIsConnected = false;
			} else {
				laserIsConnected = true;
			}
			if ((!laserIsConnected && !occurances)) {
				occurances = true;
				qwe = System.nanoTime();
				//counter++;
				doSendGridEmailStuffForLaser(); //send email!!! TODO
				//System.out.println("email sent!");
				//System.out.println("jigga wants cheese" + laserIsConnected + occurances);
			} else if (laserIsConnected && occurances && (System.nanoTime() - qwe > 5000000000L)) {
				occurances = false;
			}
		} catch(Exception e) {
			// lol
		}
		
		

	}
	
	static void checkTemp(String inputLine) {
		inputLine = inputLine.replaceAll("The temperature in Farienheit is: ", "");
		// change above as necessary
		double x = Double.parseDouble(inputLine);
		
		if (x >= 76.0 && !yofo) {
			yofo = true;
			doSendGridEmailStuffForFire();
		} 
		
		
	}
	
	/**
	 * Sends the emails. (burglar)
	 */
	static void doSendGridEmailStuffForLaser() {
		System.out.println("email sent!");
		String[] asdf = new String[1];
		asdf[0] = s;
		Sample.main(asdf);
	}
	
	/**
	 * Sends the email for fire
	 */
	static void doSendGridEmailStuffForFire() {
		String[] asdf = new String[1];
		asdf[0] = s;
		Sample.main2(asdf);
		System.out.println("FIRE!@!@!@!@!@!@!@");
	}

	static String s;
	public static void main(String[] args) throws Exception {
		s = JOptionPane.showInputDialog("Enter your email!");
		SerialTest main = new SerialTest();
		main.initialize();
		Thread t = new Thread() {
			public void run() {
				// the following line will keep this app alive for 1000 seconds,
				// waiting for events to occur and responding to them (printing
				// incoming messages to console).
				try {
					Thread.sleep(1000000);
				} catch (InterruptedException ie) {
				}
			}
		};
		t.start();
		System.out.println("Started");
	}
}
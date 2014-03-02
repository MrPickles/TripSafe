package blah;
import com.github.sendgrid.SendGrid;
import java.util.Date;

public class Sample {
	
	private static final String TO = "homeowner370@gmail.com";
	private static final String FROM = "liu.andrew.x@gmail.com";
	private static final String SUBJ = "Laser Trip Wire Activated";
	private static final String TEXT = "Omg! Somebody tripped the laser!!! D:";
	private static final String SUBJ2 = "HOUSE FIRE!!!";
	private static final String TEXT2 = "Your house is on fire!!! D:";
	private static SendGrid sendgrid = new SendGrid("jeffhe", "Hacknc1!");

	public static void main(String[] args) {
//		SendGrid sendgrid = new SendGrid("craigweiss70", "reinhardt1!");
		Date d = new Date();
		String s = d.toString();
		sendgrid.addTo(TO);
		sendgrid.setFrom(FROM);
		sendgrid.setSubject(SUBJ);
		sendgrid.setText(TEXT + "\n" + s);

		sendgrid.send();

		//System.out.println("done");
	}
	
	public static void main2() {
//		SendGrid sendgrid = new SendGrid("craigweiss70", "reinhardt1!");
		Date d = new Date();
		String s = d.toString();
		sendgrid.addTo(TO);
		sendgrid.setFrom(FROM);
		sendgrid.setSubject(SUBJ2);
		sendgrid.setText(TEXT2 + "\n" + s);

		sendgrid.send();
	}

}

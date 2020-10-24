

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Collections;
import java.util.Properties;
import java.util.ArrayList;

public class SecretSanta{
	public static String subject = "Secret Santa 2020!";

	public static void main(String[] args) {
		//TO-DO: Create list of participants
		ArrayList<Person> persons = new ArrayList<Person>();
		persons.add(new Person("name", "email"));

		//Shuffle the list
		ArrayList<Person> shuffledPersons = new ArrayList<Person>(persons);
		Collections.shuffle(shuffledPersons);

		//send out the emails

		/*Enter the username/password for the Gmail account
		you want to send the emails from */
	    String from = "email";
	    String pass = "password";

	    Properties properties = System.getProperties();
	    String host = "smtp.gmail.com";
	    properties.put("mail.smtp.starttls.enable", "true");
	    properties.put("mail.smtp.host", host);
	    properties.put("mail.smtp.user", from);
	    properties.put("mail.smtp.password", pass);
	    properties.put("mail.smtp.port", "587");
	    properties.put("mail.smtp.auth", "true");

	    Session session = Session.getDefaultInstance(properties);

	    try {
	    	for (int i=0; i<persons.size()-1; i++) {
	    	    String to = shuffledPersons.get(i).getEmail();
	    		MimeMessage message = new MimeMessage(session);
	 	        message.setFrom(new InternetAddress(from));
	 	        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	 	        message.setSubject(subject);
	 	        message.setText(buildMessage(shuffledPersons.get(i), shuffledPersons.get(i+1)));
	 	        Transport transport = session.getTransport("smtp");
	            transport.connect(host, from, pass);
	            transport.sendMessage(message, message.getAllRecipients());
	            System.out.println("Sent message successfully....");
			}
		    String to = shuffledPersons.get(persons.size()-1).getEmail();
	    	MimeMessage message = new MimeMessage(session);
		    message.setFrom(new InternetAddress(from));
		    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		    message.setSubject(subject);
 	        message.setText(buildMessage(shuffledPersons.get(persons.size()-1), shuffledPersons.get(0)));
 	        Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            System.out.println("Sent message successfully....");
	    } catch (MessagingException mex) {
		    System.out.println("Error when sending message...");
		    mex.printStackTrace();
	    }

	}

	private static String buildMessage(Person to, Person target){
		StringBuilder sb = new StringBuilder();
	    sb.append("Hi ");
	    sb.append(to.getName());
	    sb.append(",\nYou will be the Secret Santa for ");
	    sb.append(target.getName());
	    sb.append("!\nHave fun!\n The Secret Santa Company");
	    return sb.toString();
	}


	public static class Person {
		private String name;
		private String email;

		Person(String name, String email) {
			this.name = name;
			this.email = email;
		}
		public String getName() {
			return this.name;
		}
		public String getEmail(){
			return this.email;
		}
	}
}

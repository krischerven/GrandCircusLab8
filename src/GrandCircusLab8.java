import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;

public class GrandCircusLab8 {
	
	public static void main(final String[] args) throws FileNotFoundException {

		final class Person {
			public String name;
			public String hometown;
			public String favoritefood;
			
			@SuppressWarnings("unused")
			public String datumize() {
				return "Name: " + name + "\n" +
						"Hometown: " + hometown + "\n" +
						"Favorite Food: " + favoritefood + "\n";
			}
		}
		
		final class Getter {
			public int getInt(final int max, final Scanner sc) {
				while (true) { // using while (true) fixes the return error
					int next = 0;
					try {
						next = sc.nextInt();
					// ignore literally anything that isn't 1 - 10
					} catch (InputMismatchException e) {
					}
					sc.nextLine(); // discard garbage line
					if (next > 0 && next <= max) {
						return next;
					} else {
						System.out.println("This student does not exist. Please enter a number between 1 and " + max+".");
					}
				}
			}
		}
		
		// parse the file
		final Scanner sc = new Scanner(new FileReader(System.getProperty("user.dir")+"/src/database"));
		final StringBuilder sb = new StringBuilder();
		while (sc.hasNextLine()) {
		    sb.append(sc.nextLine()+"\n");
		}
		sc.close();
		
		// create people
		Person person = null;
		final String[] lines = sb.toString().split("\n");
		final ArrayList<Person> people = new ArrayList<Person>();
		int iter = 0;
				
		for (String line : lines) {
			// entries always have to be in order;
			// the names (name: x) in database are just for human-readability
			if (line.startsWith("@entry")) {
				person = new Person();
			} else {
				line = line.split(": ")[1];
				switch (iter++) {
					case 0: {
						person.name = line;
						break;
					}
					case 1: {
						person.hometown = line;
						break;
					}
					case 2: {
						person.favoritefood = line;
						people.add(person);
						iter = 0;
						break;
					}
				}
			}
		}
		
		boolean cont = true;
		final Scanner sc2 = new Scanner(System.in);
		System.out.println("Welcome to our Java class. Which student would you like to learn more about? (1 - 10)");
		while (cont) {
			System.out.println("Please select a student from 1 to " + people.size() + ".");
			
			final int num = (new Getter()).getInt(people.size(), sc2);
			final Person selected = people.get(num-1);
			boolean cont2 = true;

			System.out.println("Student #" + num + " is " + selected.name + ". "
					+ "What would you like to know about " + selected.name + "? (options: hometown, favorite food)");
			while (cont2) {
				final String entry = sc2.nextLine();
				switch (entry.toLowerCase().trim()) {
					case "hometown": {
						System.out.println(person.name + " is from " + selected.hometown);
						break;
					}
					case "favorite food":
					case "food": {
						System.out.println(person.name + "'s favorite food is " + selected.favoritefood.toLowerCase());
						break;
					}
					default: {
						System.out.println("\""+entry+"\"" + " is not an option in the database. Try \"hometown\""
								+ " or \"favorite food\".");
						continue;
					}
				}
				System.out.println("Would you like to know more? (y/n)");
				final String next = sc2.nextLine().toLowerCase().trim();
				cont2 = next.equals("y") || next.equals("yes");
				if (cont2) {
					System.out.println("What else do you want to know?");
				}
			}
			System.out.println("Would you like to select another student? (y/n)");
			final String next = sc2.nextLine().toLowerCase().trim();
			cont = next.equals("y") || next.equals("yes");
		}
		System.out.println("Goodbye.");
	}
}
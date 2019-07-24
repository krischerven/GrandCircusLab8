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
			public String datumize() {
				return "Name: " + name + "\n" +
						"Hometown: " + hometown + "\n" +
						"Favorite Food: " + favoritefood + "\n";
			}
		}
		
		final class IntGetter {
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
			final int num = (new IntGetter()).getInt(people.size(), sc2);
			System.out.println(people.get(num-1).datumize());
			System.out.println("Continue? (y/n)");
			final String yesno = sc2.nextLine().toLowerCase().trim();
			cont = (yesno.equals("y") || yesno.equals("yes"));
		}
	}
}
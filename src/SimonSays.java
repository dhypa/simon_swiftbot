import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import swiftbot.Button;
import swiftbot.SwiftBotAPI;
import swiftbot.Underlight;


public class SimonSays {
	static SwiftBotAPI swiftBot;
	public static void main(String[] args) throws InterruptedException {
		try {
			swiftBot = new SwiftBotAPI();
		} catch (Exception e) {
			/*
			 * idk what this is lol
			 */
			System.out.println("\nI2C disabled!");
			System.out.println("Run the following command:");
			System.out.println("sudo raspi-config nonint do_i2c 0\n");
			System.exit(5);}


		Scanner reader = new Scanner(System.in);
		System.out.println("SIMON");
		buttonFunctions();

		for (;;) {  // this is the most cursed way to create a loop that ive ever seen

			System.out.println("\n"+"1 = Play\n" + "2 = Check high scores!\n" + "3 = Exit Program");
			System.out.println("\nEnter a number:");
			String inp = reader.next();

			switch (inp) {
			case "1" -> {

				playGame();
			}
			case "2" ->{
				displayHighScores();
			}
			case "3" ->{
				reader.close();
				System.exit(0);
			}
			default -> {
				System.out.println("PLEASE ENTER A VALID NUMBER");
			}
			}	
		}
	}

	//CONTAINS ALL RGB VALUES FOR COLOURS
	public static final int red[]= {255, 0, 0}, yellow[]= {244, 252, 3}, blue[]= {52, 99, 209}, green[]= {3, 252, 78} ;
	public static ArrayList<String> sequence = new ArrayList<String>();

	public static void displaySequence(ArrayList<String> sequence) throws InterruptedException {
		// MIDDLE_LEFT X green, MIDDLE_RIGHT A red, BACK_LEFT Y yellow, and BACK_RIGHT B blue
		for (String colour:sequence) {
			switch (colour) {
			case "red" -> {
				swiftBot.setUnderlight(Underlight.MIDDLE_RIGHT, red);
				Thread.sleep(600);
				swiftBot.disableUnderlight(Underlight.MIDDLE_RIGHT);
				
			}
			case "green" -> {
				swiftBot.setUnderlight(Underlight.MIDDLE_LEFT, green);
				Thread.sleep(600);
				swiftBot.disableUnderlight(Underlight.MIDDLE_LEFT);
				
			}
			case "blue" -> {
				swiftBot.setUnderlight(Underlight.BACK_RIGHT, blue);
				Thread.sleep(600);
				swiftBot.disableUnderlight(Underlight.BACK_RIGHT);
				
			}
			case "yellow" -> {
				swiftBot.setUnderlight(Underlight.BACK_LEFT, yellow);
				Thread.sleep(600);
				swiftBot.disableUnderlight(Underlight.BACK_LEFT);
				
			}
			}
		}
	}

	public static void incrementSequence() {
		int random_int = (int)Math.floor(Math.random() * (4 - 1 + 1) + 1);
		switch(random_int) {
		case 1-> {sequence.add("red")    ;}
		case 2-> {sequence.add("green")  ;}
		case 3-> {sequence.add("blue")   ;}
		case 4-> {sequence.add("yellow") ;}
		}
	}

	// MIDDLE_LEFT X green, MIDDLE_RIGHT A red, BACK_LEFT Y yellow, and BACK_RIGHT B blue
	public static void buttonFunctions() {
		swiftBot.enableButton(Button.A, () -> {
			if (sequence.get(counter)== "green") {
				// display the corresponding light
				swiftBot.setUnderlight(Underlight.MIDDLE_LEFT, green);
				try {
					Thread.sleep(200);
				} catch(InterruptedException e) {
					System.out.println("got interrupted!");
				}
				swiftBot.disableUnderlight(Underlight.MIDDLE_LEFT);
				++counter;
				userSequenceValidator();
			}
			else {
				if (lives>0) {
					try {
						System.out.println("You've lost a life!");
						loseLife();
					} catch(InterruptedException e) {
						System.out.println("got interrupted!");
					}
				}
				else {
					try {
						
						endGame();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}	
		});
		swiftBot.enableButton(Button.X, () -> {
			if (sequence.get(counter)== "red") {
				// display the corresponding light
				swiftBot.setUnderlight(Underlight.MIDDLE_RIGHT, red);
				try {
					Thread.sleep(200);
				} catch(InterruptedException e) {
					System.out.println("got interrupted!");
				}
				swiftBot.disableUnderlight(Underlight.MIDDLE_RIGHT);
				++counter;
				userSequenceValidator();
			}
			else {
				if (lives>0) {
					try {
						System.out.println("You have lost a life!");
						loseLife();
					} catch(InterruptedException e) {
						System.out.println("got interrupted!");
					}
				}
				else {
					try {
						
						endGame();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}	

		});
		swiftBot.enableButton(Button.B, () -> {
			if (sequence.get(counter)== "yellow") {
				// display the corresponding light
				swiftBot.setUnderlight(Underlight.BACK_LEFT, yellow);
				try {
					Thread.sleep(200);
				} catch(InterruptedException e) {
					System.out.println("got interrupted!");
				}
				swiftBot.disableUnderlight(Underlight.BACK_LEFT);
				++counter;
				userSequenceValidator();
			}
			else {
				if (lives>0) {
					try {
						System.out.println("You have lost a life!");
						loseLife();
					} catch(InterruptedException e) {
						System.out.println("got interrupted!");
					}
				}
				else {
					try {
						endGame();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		swiftBot.enableButton(Button.Y, () -> {
			if (sequence.get(counter)== "blue") {
				// display the corresponding light
				swiftBot.setUnderlight(Underlight.BACK_RIGHT, blue);
				try {
					Thread.sleep(200);
				} catch(InterruptedException e) {
					System.out.println("got interrupted!");
				}
				swiftBot.disableUnderlight(Underlight.BACK_RIGHT);
				++counter;
				userSequenceValidator();
			}
			else {
				if (lives>0) {
					try {
						System.out.println("You have lost a life!");
						loseLife();
					} catch(InterruptedException e) {
						System.out.println("got interrupted!");
					}
				}
				else {
					try {
						endGame();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		});
	}

	public static void userSequenceValidator(){
		if (counter==sequence.size()) {
			isInRound=false;
		}
	}

	public static void loseLife() throws InterruptedException {
		swiftBot.setUnderlight(Underlight.MIDDLE_RIGHT, red);
		swiftBot.setUnderlight(Underlight.MIDDLE_LEFT, red);
		swiftBot.setUnderlight(Underlight.BACK_RIGHT, red);
		swiftBot.setUnderlight(Underlight.BACK_LEFT, red);
		try {
			Thread.sleep(200);
		} catch(InterruptedException e) {
			System.out.println("got interrupted!");
		}
		--lives;
		swiftBot.disableUnderlights();
		System.out.println("WRONG COLOUR! Try again!");
	}

	private static final String HIGH_SCORE_FILE = "high_scores.txt";
	public static void endGame() throws InterruptedException {
		swiftBot.disableAllButtons();
		isInRound=false;
		List<ScoreEntry> highScores = loadHighScores();
		if (isHighScore(newScore, highScores)) {
			System.out.println("Congratulations! You have achieved a high score!");
			highScores.add(new ScoreEntry(name, newScore));

			Collections.sort(highScores, Collections.reverseOrder());

			if (highScores.size() > 10) {
				highScores = highScores.subList(0, 10);
			}
			saveHighScores(highScores);
		}
		if (newScore>=5) {
			celebration();
		}
		swiftBot.disableButtonLights();
		System.exit(1);
	}

	public static boolean isHighScore(int newScore, List<ScoreEntry> highScores) {
		return highScores.isEmpty() || newScore > highScores.get(highScores.size() - 1).getScore();
	} 

	public static List<ScoreEntry> loadHighScores() {

		List<ScoreEntry> highScores = new ArrayList<>();
		File file = new File(HIGH_SCORE_FILE);
		try { file.createNewFile(); } catch (IOException e) { /* EEEEEEE */}

		try (BufferedReader reader = new BufferedReader(new FileReader(HIGH_SCORE_FILE))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				String playerName = parts[0].trim();
				int score = Integer.parseInt(parts[1].trim());
				highScores.add(new ScoreEntry(playerName, score));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return highScores;
	}

	public static void displayHighScores() {
		List<ScoreEntry> highScores = loadHighScores();
		File file = new File(HIGH_SCORE_FILE);
		try { file.createNewFile(); } catch (IOException e) { System.out.println("File not found");}
		try (BufferedReader reader = new BufferedReader(new FileReader(HIGH_SCORE_FILE))) {
			String line;
			System.out.println("\n");
			System.out.println("\n");
			System.out.println("###########  HIGH SCORES  ###########");
			System.out.println("\n");
			while ((line = reader.readLine()) != null) { 
				String[] parts = line.split(",");
				String playerName = parts[0].trim();
				int score = Integer.parseInt(parts[1].trim());
				System.out.println("Player "+playerName+" has scored "+score);
			}
			System.out.println("\n");
			System.out.println("#####################################");
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static void saveHighScores(List<ScoreEntry> highScores) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(HIGH_SCORE_FILE))) {
			for (ScoreEntry entry : highScores) {
				writer.write(entry.getName() + "," + entry.getScore());
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static class ScoreEntry implements Comparable<ScoreEntry> {
		private final String name;
		private final int score;

		public ScoreEntry(String name, int score) {
			this.name = name;
			this.score = score;
		}

		public String getName() {
			return name;
		}

		public int getScore() {
			return score;
		}

		@Override
		public int compareTo(ScoreEntry other) {
			return Integer.compare(this.score, other.score);
		}
	}	

	public static void celebration() throws InterruptedException {
		if (newScore>=5){
			Thread.sleep(1000);
			createCelebrationSequence();
			swiftBot.move(100, 100, 2000);
			swiftBot.move(-100, -100, 2000);
			swiftBot.move(-50, +50, 500);
			displaySequence(celebrationSequence);
			swiftBot.disableUnderlights();
			System.exit(1);
		}
	}

	public static ArrayList<String> celebrationSequence = new ArrayList<String>();
	public static void createCelebrationSequence() {
		for (int i=0;i<5;i++) {
			int random_int = (int)Math.floor(Math.random() * (4 - 1 + 1) + 1);
			switch(random_int) {
			case 1-> {celebrationSequence.add("red")    ;}
			case 2-> {celebrationSequence.add("green")  ;}
			case 3-> {celebrationSequence.add("blue")   ;}
			case 4-> {celebrationSequence.add("yellow") ;}
			}
		}
	}


	// javac -cp "SwiftBotAPI-5.1.0.jar" SimonSays.java
	// java -cp "SwiftBotAPI-5.1.0.jar": SimonSays
	public static int round=0,newScore=0,lives=3,counter=0;
	public static String name;
	public static boolean isInRound=false;
	public static void playGame() throws InterruptedException {
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter your name");
		if (reader.hasNextLine()) {
			name = reader.nextLine();
		}
		if (name!= null) {
			System.out.println("/n"+"Nice to meet you " + name + ", the game will begin now");

			for (;;) {
				++round;
				System.out.println("Round " + round + ", Your score is " + newScore);
				if (round%5 == 0) {
					System.out.println("Would you like to continue playing? (Y/N)");
					String ans = reader.nextLine().toLowerCase();
					if (ans.equals("n")) {
						System.out.println("See you again champ!");
						endGame(); 
					}
				}
				isInRound=true;
				// user is given the option to quit or continue every 5th round

				System.out.println("Round " + round + ", Your score is " + newScore);


				// MIDDLE_LEFT green, MIDDLE_RIGHT red, BACK_LEFT yellow, and BACK_RIGHT blue
				//loops through sequence and displays RBG values
				incrementSequence();
				displaySequence(sequence);

				//Validates the sequence entered by the user
				counter=0;
				while (isInRound) {
					Thread.sleep(100);

				}


				// adds round to the newScore.
				newScore+=1;    
			}
		}
	}
}






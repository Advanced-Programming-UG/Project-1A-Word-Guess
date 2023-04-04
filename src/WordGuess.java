import java.util.Scanner;
import java.util.Random;

public class WordGuess {
	
	public static Scanner scanner = new Scanner( System.in );

	public static void main(String[] args) throws InterruptedException {
		while( true ) {
			String Word = mainMenu();       // Kalameye random ya vared shode ro tooye word zakhire mikone
			if( Word.charAt(0) != '0' )     // Agar word "0" bashe varede bazi nemishe va restart mishe
				game( Word );               // Varede mohite bazi mishe
		}
	}
	
	// Kalameye random ya vared shode ro voroudi migire va bazi ro modiriat mikone
	public static void game( String Word ) throws InterruptedException {
		int numberOfCharacters = calculateNumberOfCharacters( Word );
		int notYetGuessed = numberOfCharacters;
		int chances = numberOfCharacters * 2;
		int leftChances = chances;
		boolean[] guessedCorrect = new boolean [ numberOfCharacters ];   // Har harfe reshte ke dorost hads zade beshe, inja true mishe
		char[] wrongCharacters = new char[ chances ];
		String msg = "";                                                 // har chizi in tou zakhire she, be onvane hoshdar namayesh dade mishe
		
		while( leftChances > 0 && notYetGuessed > 0 ) {                  // Edame peyda kone ta mogheii ke nabakhte va naborde
			String input = showGame( msg , Word, guessedCorrect, wrongCharacters );    // UI va ajzaye bazi ro namayesh mide va az karbar harf(reshte) voroudi migire
			input = input.trim();
			msg = "";
			
			if( input.length() != 1 ) {                                  // Age voroudi yek harf nabood
				msg = "</ Invalid input >";
			} else if( input.charAt(0) == '+' ) {                        // Age hint mikhast
				hint( Word );
			} else if( input.charAt(0) == '0' ) {                        // Age mikhast az bazi biad biroon
				return;
			} else {                                                     // Age voroudi harf bood
				boolean found = false;
				int j = 0;                                               // Array guessedCorrect (Word bedoone space) ro peymaiesh mikone
				for( int i = 0 ; i < Word.length() ; i++ ) {             // Tooye reshte check mikone bebine hads doroste ya na
					if( Word.charAt(i) != ' ' ) {
						if( guessedCorrect[j] == false ) {
							if( Word.toLowerCase().charAt(i) == input.toLowerCase().charAt(0) ) {
								found = true;
								guessedCorrect[j] = true;
								notYetGuessed--;
							}
						}
						j++;
					}
				}
				if( found == false ) {                                   // Age hads eshteba bood zakhirash kone
					wrongCharacters[ chances - leftChances ] = input.toUpperCase().charAt(0);
					leftChances--;
				}
			}
		}
		
		boolean win = false;                                             // Bord ya baakht ro zakhire mikone
		if( leftChances == 0 ) {
			win = false;
		} else if( notYetGuessed == 0 ) {
			win = true;
		}
		outro( win );                                                    // Bord ya baakht ro e'laam mikone
		return;
	}
	
	// UIe bazi ro namayesh mide va harfe (reshteie) voroudie karbar ro tahvile game() mide
	public static String showGame( String msg, String Word, boolean[] guessedCorrect, char[] wrongCharacters ) {
		clearConsole();
		System.out.println();
		System.out.print( "\t" );
		System.out.println( msg );
		printWord( Word, guessedCorrect );
		printWrongGuesses( Word, wrongCharacters );
		System.out.println();
		System.out.println();
		System.out.print( "\t" );
		System.out.print( "Guess a character > ");
		return scanner.nextLine();
	}
	
	// Modiriat konandeie safeie avvale bazi
	public static String mainMenu() {
		String input = showMainMenu( "" );                  // Safeie avvale bazi ro namayesh mide va voroudie karbar ro migire
		while( true ) {
			if( input.charAt(0) == '0' ) {                  // Khorouj
				System.exit(0);
			} else if( input.charAt(0) == '1' ) {           // Vared kardane dastie kalame
				return chooseWord();
			} else if( input.charAt(0) == '2' ) {           // Saakhte randome kalame
				return generateWord();
			}
			input = showMainMenu( "</ Invalid input >" );   // Dar gheire in soorat, voroudi mo'tabar nist
		}
	}

	// Safeie avvale bazi ro namayesh mide va voroudie karbar ro khorouji mide
	public static String showMainMenu( String msg ) {
		clearConsole();
		System.out.println();
		System.out.println();
		System.out.print( "\t" );
		System.out.println( "Word Guess Game" );
		System.out.print( "\t" );
		System.out.println( "1) Choose a word" );
		System.out.print( "\t" );
		System.out.println( "2) Generate a random word" );
		System.out.print( "\t" );
		System.out.println( msg );
		System.out.println();
		System.out.print( "\t" );
		System.out.print( "> " );
		return scanner.nextLine();
	}
 	
	// Kalame ro az karbar voroudi migire
	public static String chooseWord() {
		String input = showChooseWord( "" );
		input = input.trim();
		while( input.length() == 0 ) {
			input = showChooseWord( "</ Invalid input >" );
			input = input.trim();
		}		
		return input;
	}
	
	public static String showChooseWord( String msg ) {
		clearConsole();
		System.out.println();
		System.out.println();
		System.out.print( "\t" );
		System.out.println( msg );
		System.out.print( "\t" );
		System.out.println( "Enter a Word:" );
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.print( "\t" );
		System.out.print( "> " );
		return scanner.nextLine();
	}
	
	// Beyne 1 ta 3 kalameye random tolid mikone va dar ghaalebe yek reshte khorouji mide
	public static String generateWord() {
		Random random = new Random();
		String generatedWord = "";
		int numberOfWords = random.nextInt(3) + 1;           // Mitoone 1, 2 ya 3 bashe

		for( int i = 0 ; i < numberOfWords ; i++ ) {
			if( i > 0 ) {
				generatedWord += " ";                        // Beine kalamaat space mizare
			}

			int lengthOfTheWord = random.nextInt(6) + 5;     // Toole reshte beine 5 ta 10 hast
			for( int j = 0 ; j < lengthOfTheWord ; j++ ) {
				char randomCharacter = (char) ( random.nextInt(26) + 'a' );
				if( j == 0 ) {                               // Charactere avval ro bozorg minevise
					generatedWord += (char) ( randomCharacter - 32 );
				} else {
					generatedWord += randomCharacter;
				}
			}
		}
		
		return generatedWord;
	}
	
	// Space ha ro nemishmore
	public static int calculateNumberOfCharacters( String Word ) {
		int n = 0;
		for( int i = 0 ; i < Word.length(); i++ ) {
			if( Word.charAt(i) != ' ' ) {
				n++;
			}
		}
		return n;
	}
	
	// Kalame ro dar UI be soorate monaaseb neshoon mide
	public static void printWord( String Word, boolean[] guessedCorrect ) {
		System.out.print( "\t" );
		int j = 0;
		for( int i = 0 ; i < Word.length() ; i++ ) {
			if( Word.charAt(i) == ' ' ) {
				System.out.print( "  " );				
			} else if( guessedCorrect[j] ) {
				System.out.print( Word.charAt(i) + " " );
				j++;
			} else {
				System.out.print( "_ " );
				j++;
			}
		}
		System.out.println();
		return;
	}
	
	// Character haie eshteba ro zire horoofe word namayesh mide
	public static void printWrongGuesses( String Word, char[] wrongCharacters ) {
		System.out.print( "\t" );
		int j = 0;
		for( int i = 0 ; i < Word.length(); i++ ) {
			if( Word.charAt(i) == ' ' ) {
				System.out.print( "  " );
			} else if( wrongCharacters[j] == '\0' ) {
				System.out.print( "  " );
				j++;
			} else {
				System.out.print( wrongCharacters[j] + " " );
				j++;
			}
		}
		System.out.println();
		System.out.print( "\t" );
		for( int i = 0 ; i < Word.length(); i++ ) {
			if( Word.charAt(i) == ' ' ) {
				System.out.print( "  " );
			} else if( wrongCharacters[j] == '\0' ) {
				System.out.print( "  " );
				j++;
			} else {
				System.out.print( wrongCharacters[j] + " " );
				j++;
			}
		}
		System.out.println();
		return;
	}
	
	// Kalameye kaamel ro be modate 1s namayesh mide
	public static void hint( String Word ) throws InterruptedException {
		clearConsole();
		System.out.print( "\t" );
		for( int i = 0 ; i < Word.length() ; i++ ) {
			System.out.print( Word.charAt(i) + " " );
		}
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		Thread.sleep( 1000 );
		return;
	}
	
	// Dar payane bazi, neshoon mide karbar barande shode ya bazande
	public static void outro( boolean win ) throws InterruptedException {
		clearConsole();
		System.out.print( "\t" );
		if( win == true ) {
			System.out.println( "You Win!" );
		} else {
			System.out.println( "You Lose!" );
		}
		System.out.println();
		System.out.println();
		System.out.println();
		Thread.sleep( 3000 );
		return;
	}
		
	// Ye 'aalame enter chaap mikone ta karbar khorouji haie ghabli ro nabine
	public static void clearConsole() {
		for( int i = 0 ; i < 80 ; i++ ) {
			System.out.println();
		}
		return;
	}
	 	
}
package jets.projects;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import jets.projects.session_saving.NormalUserSavedSession;
import jets.projects.session_saving.MyEncrypter;
import jets.projects.session_saving.SessionSaver;

public class App {
    private static final Scanner scanner = new Scanner(System.in);
    private static final MyEncrypter myEncryper
            = new MyEncrypter();
    private static final SessionSaver sessionSaver
            = new SessionSaver();
    
    public static void main( String[] args ) {
        System.out.println( "Hello to my simple encrypt program." );
        
        while (true) {
            displayMenu();
            processInput();
        }
    }
    
    private static void displayMenu() {
        System.out.println("1. Generate a key.");
        System.out.println("2. Encrypt a message (Using input key).");
        System.out.println("3. Decrypt a message (Using input key).");
        System.out.println("4. Encrypt a message (Using the saved key).");
        System.out.println("5. Decrypt a message (Using the saved key).");
        System.out.println("6. Create Session data file.");
        System.out.println("7. Load and delete Session data file.");
        System.out.println("e. Exit the program.");
        System.out.println();
    }
    
    private static void processInput() {
        String input = enterLineFromUser(
                "Enter a choice from the menu: ");

        switch (input.charAt(0)) {
            case '1' -> {
                generateKey();
                break;
            }
            case '2' -> {
                encryptFromInputKey();
                break;
            }
            case '3' -> {
                decryptFromInputKey();
                break;
            }
            case '4' -> {
                encryptFromSavedKey();
                break;
            }
            case '5' -> {
                decryptFromSavedKey();
                break;
            }
            case '6' -> {
                createSessionDataFile();
                break;
            }
            case '7' -> {
                loadAndDeleteSessionDataFile();
                System.out.println();
                break;
            }
            case 'e' -> {
                System.exit(0);
                break;
            }
            default -> {
                System.err.println("The input must be from the menu.");
                System.out.println();
            }  
        }
    }
    
    public static void generateKey() {
        String key = myEncryper.generateNewKey();
        System.out.println("The key :" + key);

        System.out.println();
    }
    
    private static void encryptFromInputKey() {
        String message = enterLineFromUser(
                "Enter the message: ");
        String key = enterLineFromUser(
                "Enter the key: ");

        String encrypedMessage = myEncryper.encrypt(
                message, key);
        System.out.println("The encryped message: "
                + encrypedMessage);

        System.out.println();
    }
    
    private static void decryptFromInputKey() {
        String encrypedMessage = enterLineFromUser(
                "Enter the encryped message: ");
        String key = enterLineFromUser(
                "Enter the key: ");

        String message = myEncryper.decrypt(
                encrypedMessage, key);
        System.out.println("The decryped message: "
                + message);

        System.out.println();
    }
    
    public static void encryptFromSavedKey() {
        String message = enterLineFromUser(
                "Enter the message: ");
        String key = loadSavedKey();
        if (key == null) {
            return;
        }

        String encrypedMessage = myEncryper.encrypt(
                message, key);
        System.out.println("The encryped message: "
                + encrypedMessage);

        System.out.println();
    }
    
    public static void decryptFromSavedKey() {
        String encrypedMessage = enterLineFromUser(
                "Enter the encryped message: ");
        String key = loadSavedKey();
        if (key == null) {
            return;
        }

        String message = myEncryper.decrypt(
                encrypedMessage, key);
        System.out.println("The decryped message: "
                + message);

        System.out.println();
    }
    
    private static void createSessionDataFile() {
        String phoneNumber = enterLineFromUser(
                "Enter a phone number: ");
        String password = enterLineFromUser(
                "Enter a password: ");
        
        NormalUserSavedSession session = new NormalUserSavedSession(
                phoneNumber, password);
        
        sessionSaver.save(session);
        System.out.println();
    }
    
    private static void loadAndDeleteSessionDataFile() {
        NormalUserSavedSession savedSession = sessionSaver.load();
        if (savedSession == null) {
            return;
        }
        boolean isDeleted = sessionSaver.deleteSessionFile();
        if (!isDeleted) {
            System.err.println("Failed to delete session file.");
        }
        
        System.out.println("Saved Session: " + savedSession);
    }
    
    // Util functions.
    private static String enterLineFromUser(String promptMessage) {
        String line;
        try {
            while (true) {
                System.out.print(promptMessage);
                line = scanner.nextLine();
                if (line == null) {
                    System.err.println("Input cannot be null.");
                } else if (line.isEmpty()) {
                    System.err.println("Input cannot be empty.");
                } else {
                    return line;
                }
            }
        } catch (NoSuchElementException ex) {
            System.out.println("\nProgram will terminate.");
            System.exit(1);
            // Should not be executed.
            return null;
        }
    }
    
    private static final String KEY_FILE_PATH = "session_key.txt";
    private static String loadSavedKey() {
        InputStream inputStream = App.class.getClassLoader()
                .getResourceAsStream(KEY_FILE_PATH);
        if (inputStream == null) {
            System.err.println("Could not load the key file.");
            return null;
        }
        
        byte[] fileContet;
        try {
            fileContet = inputStream.readAllBytes();
        } catch (IOException ex) {
            System.err.println("Could not read the content of the key file.");
            System.err.println(ex.getMessage());
            return null;
        }
        
        return new String(fileContet);
    }
}

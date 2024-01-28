import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
// import java.util.Date;
import java.util.Formatter;
import java.util.Scanner;

public class PersonalDiary {
    Scanner in = new Scanner(System.in);

    private String name, password;

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    void newUser() {
        System.out.println("\t\t\tSign Up");

        System.out.println("Create an Account");
        System.out.println("Enter Your Name : ");
        String newUsername = in.nextLine();
        setName(newUsername);

        System.out.println("Enter Password : ");
        String newUserpassword = in.nextLine();
        setPassword(newUserpassword);

        try {
            Formatter f = new Formatter(new FileOutputStream("UserData.txt", true));

            if (new File("UserData.txt").length() == 0) {
                f.format("%s\t\t\t%s\n", "Name", "Password");
            }

            f.format("%s\t\t%s\n", getName(), getPassword());
            f.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("Account Created Successfully..");
        diaryContent();
    }

    String userLogin() {
        System.out.println("\t\t\tLogin");

        System.out.println("Enter Your Name :");
        String username = in.nextLine();
        System.out.println("Enter Password :");
        String password = in.nextLine();

        try (Scanner fileScanner = new Scanner(new File("UserData.txt"))) {
            fileScanner.nextLine();
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("\t\t");
                if (parts.length == 2 && parts[0].trim().equals(username) && parts[1].trim().equals(password)) {
                    System.out.println("Login successful!");
                    return username;
                }
            }
            System.out.println("Incorrect username or password.");
        } catch (FileNotFoundException e) {
            System.out.println("User data file not found.");
        }
        return null;
    }

    void diaryContent() {
        int choice = 0;
        String username = userLogin();
        if (username != null) {
            System.out.println("\t\t\t" + username + "'s Diary");
            System.out.println("1. Write Something");
            System.out.println("2. Read");
            choice = in.nextInt();
            in.nextLine();
        }

        if (choice == 1)
            write(username);
        else
            read(username);
    }

    void write(String username) {
        String filename = username + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(filename, true))) {
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = dateFormat.format(currentDate);

            System.out.println("Write ");
            String note = in.nextLine();

            writer.format("%s\n%s\n\n", formattedDate, note);
            System.out.println("Entry Added Successfully");
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    void read(String username) {
        try (Scanner scanner = new Scanner(new File(username + ".txt"))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            System.out.println("Enter the date (yyyy-MM-dd) to view notes:");
            String inputDateStr = in.nextLine().trim();

            Date inputDate = dateFormat.parse(inputDateStr);

            boolean found = false;
            while (scanner.hasNextLine()) {
                String dateLine = scanner.nextLine().trim();
                if (dateLine.isEmpty()) {
                    continue;
                }
                String noteLine = scanner.nextLine().trim(); // Read the note
                Date entryDate = dateFormat.parse(dateLine);
                if (entryDate.equals(inputDate)) {
                    System.out.println("Note for " + inputDateStr + ":");
                    System.out.println(noteLine);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("No notes found for the specified date.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("User data file not found.");
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
        }
    }

    public static void main(String[] args) {
        PersonalDiary pd = new PersonalDiary();

        System.out.println("New User : ");
        System.out.println("1.YES 2.NO");
        int newuser = pd.in.nextInt();
        pd.in.nextLine();

        if (newuser == 1)
            pd.newUser();
        else
            pd.diaryContent();
    }
}

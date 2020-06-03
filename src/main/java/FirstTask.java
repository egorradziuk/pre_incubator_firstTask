import java.util.*;

public class FirstTask {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String str = "";
        boolean isCorrect = false;

        while(!isCorrect) {
            System.out.println("Input something: ");
            str = scanner.nextLine();
            isCorrect = Validation.isCorrect(str);
        }
        scanner.close();
        System.out.println("Your string is correct.");
    }


}



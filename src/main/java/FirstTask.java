import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class FirstTask {


    private static final Lock LOCK = new ReentrantLock();
    private static final CommonResource COMMON_RESOURCE = new CommonResource();

    public static void main(String[] args) {
//        workWithConsole();
//        checkingExceptionWork();
//        checkingLambdaWork();
        checkingThreadsWork();
    }

    public static void workWithConsole() {
        Scanner scanner = new Scanner(System.in);
        String str = "";
        boolean isCorrect = false;

        while(!isCorrect) {
            System.out.println("Input something: ");
            str = scanner.nextLine();
            isCorrect = Validation.isCorrect(str.toCharArray());
        }
        scanner.close();
        System.out.println("Your string is correct.");
    }

    public static void checkingExceptionWork() {
        Scanner scanner = new Scanner(System.in);
        String str = "";
        boolean isCorrect = false;

        while(!isCorrect) {
            System.out.println("Input something: ");
            str = scanner.nextLine();
            try {
                Validation.checkCorrection(str.toCharArray());
                isCorrect = true;
            } catch(MyException e) {
                System.out.println(e);
            }
        }
        scanner.close();
        System.out.println("Your string is correct.");
    }

    public static void checkingLambdaWork() {
        Scanner scanner = new Scanner(System.in);
        String str = "";
        boolean isCorrect = false;

        while(!isCorrect) {
            System.out.println("Input something: ");
            str = scanner.nextLine();
            try {
                Validation.checkCorrection(findBrackets(str)
                        .toString().toCharArray());
                isCorrect = true;
            } catch(MyException e) {
                System.out.println(e);
            }
        }
        scanner.close();
        System.out.println("Your string is correct.");
    }

    public static List<Character> findBrackets(String str) {
        List<Character> list = str.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        list = list.stream().filter(
                s -> Validation.LIST_OPEN_BRACKETS.contains(String.valueOf(s))
                        || Validation.LIST_CLOSE_BRACKETS.contains(String
                        .valueOf(s)))
                .collect(Collectors.toList());
        return list;
    }

    public static void checkingThreadsWork() {

        for (int i = 0; i < 1000; i++) {
            new MyThread(COMMON_RESOURCE, LOCK).start();
        }
    }

}



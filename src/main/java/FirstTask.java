import com.sun.istack.internal.NotNull;

import javax.annotation.PostConstruct;
import java.lang.annotation.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
//        checkingThreadsWork();
        checkingStringFields(new Demo1());
//        checkingReturnValues(new Demo());
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

    public static void checkingStringFields(Object obj) {

        Field[] fields = obj.getClass().getFields();
        for (Field field : fields) {
            System.out.println("Field name: " + field.getName()
                    + " (type: " + field.getType().getSimpleName() + ")");
            if (field.getType().equals(String.class)) {
                try {
                    Validation.checkCorrection(field.get(obj).toString().toCharArray());
                    System.out.println("The field \"" + field.getName()
                                    + "\" is correct.");
                } catch (MyException e) {
                    System.out.println(e);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void checkingReturnValues(Object obj) {

        Method[] methods = obj.getClass().getMethods();
        for (Method m : methods) {
            if (m.getAnnotation(S.class) != null) {
                if (m.getReturnType().equals(String.class)) {
                    //System.out.println(m.getName());
                    try {
                        String result = String.valueOf(m.invoke(obj));
                        Validation.checkCorrection(result.toCharArray());
                        System.out.println("Return value form method \""
                                + m.getName() + "\" is correct.");
                    } catch (MyException e) {
                        System.out.println(e);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}


class Demo {
    public int a = 1;
    public String str = "{[<>]}";

    @S("asd")
    public String getStr() {
        return str;
    }
}

class Demo1 {
    public int a = 1;
    public String str = "{[<()>]}";
    public String str2 = "{[<()>]";
    public String str3 = "{[()>]}";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface S {
    String value();// default "S: default value";
}

@Retention(RetentionPolicy.RUNTIME)
@interface A {
    String value();
    String description() default "A: default value";
}

@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
@interface Unique {}

@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
@interface MaxLen {
    int value();
}
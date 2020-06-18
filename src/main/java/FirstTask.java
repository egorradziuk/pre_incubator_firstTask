import com.sun.istack.internal.NotNull;

import javax.annotation.PostConstruct;
import java.io.InputStream;
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

        new Thread(new MonitoringThread()).start();
//        workWithConsole();
//        checkingExceptionWork();
//        checkingLambdaWork();
//        checkingThreadsWork();
//        checkingStringFields(new Demo1());
//        checkingReturnValues(Demo.class);

        new Demo().getStr();
        new Demo1().setStr3("{[()<>]}");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Demo1().setStr("{A>");
        new Demo1().setStr3("{A[<452> ]./}");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Demo1().setStr("{[()<>]}");
        new Demo1().setStr3("{A[<452<> ]./}");

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

    public static void checkingReturnValues(Class<?> c) {

        Method[] methods = c.getMethods();
        for (Method m : methods) {
            if (m.getAnnotation(MyAnno.class) != null) {
                if (m.getReturnType().equals(String.class)) {
//                    System.out.println(m.getName());
                    try {
                        String result = null;
                        try {
                            result = String.valueOf(m.invoke(c.newInstance()));
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        }
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

@MyAnno
class Demo {

    private int a = 1;
    @CheckField
    private String str = "{[<>]}";

    public Demo() {
        ApplicationContext.add(this);
    }


    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @CheckMethod
    public String getStr() {
        return str;
    }

}

@MyAnno
class Demo1 {
    private int a;

    @CheckField
    private String str;
    private String str2;
    @CheckField
    public String str3;

    public Demo1() {
        ApplicationContext.add(this);
    }

    @CheckMethod
    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
    @CheckMethod
    public String getStr2() {
        return str2;
    }

    public void setStr2(String str2) {
        this.str2 = str2;
    }

    public String getStr3() {
        return str3;
    }

    public void setStr3(String str3) {
        this.str3 = str3;
    }
}


class A {
    public static String s1;
    public String s2;

    static {
        s1 = "static init block A";
        System.out.println(s1);
    }

    {
        s2 = "init block A";
        System.out.println(s2);
    }

    public A() {
        System.out.println("constructor A");
    }
}

class B extends A {
    public static String s1;
    public String s2;

    static {
        s1 = "static init block B";
        System.out.println(s1);
    }

    {
        s2 = "init block B";
        System.out.println(s2);
    }

    public B() {
        super();
        System.out.println("constructor B");
    }
}

class C {
    public static void main(String[] args) {
        String s = B.s1;
        System.out.println(s);
    }
}

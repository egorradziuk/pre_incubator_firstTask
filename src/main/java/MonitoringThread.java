import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

public class MonitoringThread implements Runnable {

    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (ApplicationContext.getList() != null) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Iterator iterator = ApplicationContext.getList().iterator();
            while (iterator.hasNext()) {
                Object obj = iterator.next();
                if (obj.getClass().isAnnotationPresent(MyAnno.class)) {
                    checkMethods(obj.getClass());
//                    checkFields(obj.getClass());
                    iterator.remove();
                } else {
                    iterator.remove();
                }
            }
        }
    }

    private void checkMethods(Object obj) {
        Method[] methods = obj.getClass().getMethods();
        for (Method m : methods) {
            if (m.getAnnotation(CheckMethod.class) != null) {
                if (m.getReturnType().equals(String.class)) {
                    try {
                        String result = (String) m.invoke(obj);
                        if (result != null) {
                            Validation.checkCorrection
                                    (result.toCharArray());
                            System.out.println("Return value form method \""
                                    + m.getName() + "\" is correct.");
                        }
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

    private void checkFields(Object obj) {
        Field[] fields = obj.getClass().getFields();
        for (Field f : fields) {
            if (f.getAnnotation(CheckField.class) != null) {
                if (f.getType().equals(String.class)) {
                    try {
                        String result = (String) f.get(obj);
                        if (result != null) {
                            Validation.checkCorrection
                                    (result.toCharArray());
                            System.out.println("Return value form field \""
                                    + f.getName() + "\" is correct.");
                        }
                    } catch (MyException e) {
                        System.out.println(e);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

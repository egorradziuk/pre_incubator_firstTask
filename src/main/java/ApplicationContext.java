import java.util.ArrayList;
import java.util.List;

public class ApplicationContext {

    private static List<Object> list = new ArrayList<>();

    public static void remove(Object o) {
        list.remove(o);
    }

    public static void add(Object o) {
        list.add(o);
    }

    public static List<Object> getList() {
        return list;
    }
}

import java.util.*;

public class Validation {

    public static final List<String> LIST_OPEN_BRACKETS = new ArrayList<>
            (Arrays.asList("[","{","<","("));
    public static final List<String> LIST_CLOSE_BRACKETS = new ArrayList<>
            (Arrays.asList("]","}",">",")"));
    public static Map<String, String> MAP = new HashMap<String, String>
            () {{
        put("]","[");
        put("}","{");
        put(">","<");
        put(")","(");
    }};

    public static boolean isCorrect(char[] array) {
        ArrayDeque<String> adq = new ArrayDeque<>();

        for(char c : array){
            String symbol = String.valueOf(c);
            if (LIST_OPEN_BRACKETS.contains(symbol)) {
                adq.addLast(symbol);
            } else if (LIST_CLOSE_BRACKETS.contains(symbol)) {
                if (adq.isEmpty() || !adq.pollLast().equals(MAP.get(symbol))) {
                    return false;
                }
            }
        }
        return adq.isEmpty();
    }

    public static void checkCorrection(char[] array) throws MyException {
        ArrayDeque<String> adq = new ArrayDeque<>();

        for(char c : array){
            String symbol = String.valueOf(c);
            if (LIST_OPEN_BRACKETS.contains(symbol)) {
                adq.addLast(symbol);
            } else if (LIST_CLOSE_BRACKETS.contains(symbol)) {
                if (adq.isEmpty() || !adq.pollLast().equals(MAP.get(symbol))) {
                    throw new MyException(c);
                }
            }
        }
        if (!adq.isEmpty()) {
            throw new MyException();
        }
    }


}

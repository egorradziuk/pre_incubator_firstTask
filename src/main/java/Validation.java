import java.util.*;

public class Validation {

    private static final List<String> LIST_OPEN_BRACKETS = new ArrayList<>
            (Arrays.asList("[","{","<","("));
    private static final List<String> LIST_CLOSE_BRACKETS = new ArrayList<>
            (Arrays.asList("]","}",">",")"));
    private static Map<String, String> MAP = new HashMap<String, String>
            () {{
        put("]","[");
        put("}","{");
        put(">","<");
        put(")","(");
    }};

    public static boolean isCorrect(String str) {
        ArrayDeque<String> adq = new ArrayDeque<>();

        for(char c : str.toCharArray()){
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
}

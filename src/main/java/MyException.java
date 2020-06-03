public class MyException extends Exception {

    MyException(char c) {
        super("MyException. This bracket '" + c + "' is in the wrong place.");
    }

    MyException() {
        super("MyException. There are brackets haven't got opposite brackets.");
    }

}

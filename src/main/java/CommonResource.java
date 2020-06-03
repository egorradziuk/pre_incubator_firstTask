import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class CommonResource{

    private final String FILE_PATH = "file.txt";

    public StringBuilder readFromFile(){

        StringBuilder sb = new StringBuilder();
        String str;

        try(BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            while((str = br.readLine()) != null) {
                sb.append(str);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        System.out.println(sb.toString());
        return sb;
    }
}
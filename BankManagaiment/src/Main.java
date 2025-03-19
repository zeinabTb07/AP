import java.io.IOException;
import java.util.zip.DataFormatException;

public class Main {
    public static void main(String[] args) throws IOException, DataFormatException {
        DataBase dataBase = new DataBase();
        DataBase.writeJSON(new JSONObject(dataBase.readTransactions()).getMap() , "src/test.txt");

    }
}
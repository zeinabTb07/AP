import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class BankSystem {
    static DataBase dataBase ;
    static  ArrayList<Transaction> transactions ;
    static ArrayList<Account> accounts ;
    public BankSystem () throws DataFormatException, IOException {
        Scanner scan = new Scanner(System.in);
        dataBase = new DataBase();
        transactions = new ArrayList<>();
        accounts = new ArrayList<>();
        updateDataBase();
    }
    private void updateDataBase() throws IOException, DataFormatException {
        transactions = new ArrayList<>();
        accounts = new ArrayList<>();
        JSONObject transactionJSON = new JSONObject(dataBase.readTransactions());
        JSONObject accountsJSON = new JSONObject(dataBase.readAccounts());
        for(JSONValue value : accountsJSON.getMap().values()){
            accounts.add(Account.JOSNObjectToAccount((JSONObject) value));
        }
        for(JSONValue value : transactionJSON.getMap().values()){
            transactions.add(Account.JOSNObjectToAccount((JSONObject) value));
        }

    }
    public void addTransaction (Transaction t){
        transactions.add(t);
    }
    public void addAccount(Account a){
        accounts.add(a);
    }
    String hashPassword (String password ){
        int n = specialSum(password);
        return decTohex(n);
    }
    private int specialSum(String string){
        int ans = 0 ;
        int l = string.length();
        for(char ch : string.toCharArray()){
            ans+= l*((int) ch);
        }
        return ans;
    }
    private static String decTohex(int n) {
        StringBuilder sb = new StringBuilder();
        int[] hexNum = new int[16];
        int i = 0;
        while (n != 0) {
            hexNum[i] = n % 16;
            n = n / 16;
            i++;
        }
        for (int j = i - 1; j >= 0; j--) {
            if (hexNum[j] > 9)
                sb.append((char) (55 + hexNum[j]));
            else sb.append(hexNum[j]);
        }
        return sb.toString();
    }
}

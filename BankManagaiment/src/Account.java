import java.util.HashMap;

public class Account {
    static int accountNum = 0 ;
    private int AccountNumber;
    String userName ;
    private String password ;
    double balance ;
    public Account (String name , String pass){
        accountNum++;
        AccountNumber = accountNum ;
        userName = name ;
        password = pass ;
        balance = 0 ;
    }
    public String getJSONKey (){
        return String.valueOf(getAccountNumber());
    }
    public JSONObject getAccountJSONObject() {
        JSONObject object = new JSONObject();
        HashMap <String , JSONValue> map = new HashMap<>();
        map.put("userName" , new JSONString(userName));
        map.put("password" , new JSONString(password));
        map.put("balance" , new JSONNumber(balance));
        object.setMap(map);
        return object;
    }
    public static  Account JOSNObjectToAccount (JSONObject object) {
        HashMap map = object.getMap();
        JSONString name = (JSONString) map.get("userName");
        JSONString pass = (JSONString) map.get("password");
        JSONNumber num = (JSONNumber) map.get("balance");

        Account account = new Account(name.getValue(), pass.getValue());
        account.balance = num.getValue();
        return account ;
    }

    public String getUserName() {
        return userName;
    }

    public int getAccountNumber() {
        return AccountNumber;
    }

    public double getBalance() {
        return balance;
    }
    public String getPassword(){
        return password ;
    }
}

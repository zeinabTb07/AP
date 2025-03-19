import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

abstract public class Transaction {
    protected static int transactionID = 1 ;
    protected int ID ;
    protected boolean status ;
    protected double amount ;
    protected String time ;

    String getJSONKey () {
        return String.valueOf(ID);
    }
    protected JSONObject getJSON () {
        JSONObject object = new JSONObject();
        HashMap<String , JSONValue> map = new HashMap<>();
        map.put("status" , new JSONBool(status));
        map.put("amount" , new JSONTime(time));
        object.setMap(map);
        return object;
    }
}

class Transfer extends Transaction {
    private Account from ;
    private Account to ;
    public Transfer (Account from , Account to , double amount) throws FileNotFoundException {
        this.from = from ;
        this.to = to ;
        super.amount = amount ;
        Transaction.transactionID++;
        ID = Transaction.transactionID;
        time = "T " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/mm/dd HH:mm")) + " T" ;
        status = from.balance >= amount ;
        if (status) {
            from.balance-=amount;
            to.balance+=amount;
            System.out.println("Transfer done successfully ");
        } else System.out.println("There is not enough money for transaction");
    }

    public JSONObject getJSONObject () {
        JSONObject object = super.getJSON();
        HashMap<String , JSONValue> map = new HashMap<>();
        map.put("to" , new JSONString(to.getJSONKey()));
        map.put("from" , new JSONString(from.getJSONKey()));
        object.setMap(map);
        return object;
    }

    public static  Transfer JOSNObjectToTransfer (JSONObject object) throws FileNotFoundException {
        HashMap map = object.getMap();
        JSONString toNumber = (JSONString) map.get("to");
        JSONString fromNumber = (JSONString) map.get("from");
        JSONNumber num = (JSONNumber) map.get("amount");
        JSONBool status = (JSONBool) map.get("status");
        JSONTime time = (JSONTime) map.get("time");

         Transfer transaction = new Transfer(
                 BankSystem.accounts.get(Integer.valueOf(fromNumber.getValue())),
                 BankSystem.accounts.get(Integer.valueOf(toNumber.getValue())) ,
                 num.getValue());

         transaction.status = status.getValue();
         transaction.time = time.getValue();
         return transaction ;

    }

    public boolean getStatus (){
        return super.status ;
    }

    public Account getFrom() {
        return from;
    }

    public Account getTo() {
        return to;
    }
    public double getAmount() {
        return amount;
    }
    public  String getTime () {
        return time ;
    }
    public int getID () {
        return ID ;
    }
}

class Deposit extends Transaction {
    private Account to ;
    public Deposit ( Account to , double amount) throws FileNotFoundException {
        this.to = to ;
        super.amount = amount ;
        Transaction.transactionID++;
        ID = Transaction.transactionID;
        time = "T " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/mm/dd HH:mm")) + " T" ;
        status = true ;
        if (status) {
            to.balance+=amount;
            System.out.println("Deposit done successfully , your current balance is : " + to.getBalance());
        }
    }
    public JSONObject getJSONObject () {
        JSONObject object = super.getJSON();
        HashMap<String , JSONValue> map = new HashMap<>();
        map.put("to" , new JSONString(to.getJSONKey()));
        object.setMap(map);
        return object;
    }
    public boolean getStatus (){
        return super.status ;
    }

    public Account getTo() {
        return to;
    }
    public double getAmount() {
        return amount;
    }
    public  String getTime () {
        return time ;
    }
    public int getID () {
        return ID ;
    }

}

class Withdraw extends Transaction {
    private Account from ;
    public Withdraw (Account from , double amount) throws FileNotFoundException {
        this.from = from ;
        super.amount = amount ;
        Transaction.transactionID++;
        ID = Transaction.transactionID;
        time = "T " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/mm/dd HH:mm")) + " T" ;
        status = from.balance >= amount ;
        if (status) {
            from.balance-=amount;
            System.out.println("Withdrawal done successfully , your current balance is : " + from.getBalance());
        } else System.out.println("There is not enough money for transaction");
    }
    public JSONObject getJSONObject () {
        JSONObject object = super.getJSON();
        HashMap<String , JSONValue> map = new HashMap<>();
        map.put("from" , new JSONString(from.getJSONKey()));
        object.setMap(map);
        return object;
    }
    public boolean getStatus (){
        return super.status ;
    }

    public Account getFrom() {
        return from;
    }

    public double getAmount() {
        return amount;
    }
    public  String getTime () {
        return time ;
    }
    public int getID () {
        return ID ;
    }
}


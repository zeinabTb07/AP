package Bank;

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BankSystem {
    ArrayList<Account> accounts = new ArrayList<>() ;
    public BankSystem(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome To This Bank . Please Inter Your Request");
        System.out.println("Type signup + Your Name + Your Password To Creat New Account");
        System.out.println("Type login + Your Name + Your Password To Inter Your Account");
        while (scan.hasNext()){
            String line = scan.nextLine();
            Account mainAccount = null ;
            ArrayList<String> commands = new ArrayList<>();
            for(String st : line.split(" ")){
                if(!st.isEmpty()){
                    commands.add(st);
                }
            }
            if(commands.get(0) .equals("signup")){
                Account account = new Account(commands.get(1)  , commands.get(2));
                if(userNameNotUsed(commands.get(1) , this.accounts)) {
                    this.accounts.add(account);
                    mainAccount = account ;
                } else {
                    System.out.println("This Username Is Used");
                }
            } else if(commands.get(0) .equals("login")){
                Account account = findAccount(commands.get(1) , this.accounts) ;
                if(account==null) System.out.println("There Is No account With This Username");
                else if(commands.get(2).equals(account.getPassWord())){
                    mainAccount = account ;
                } else System.out.println("Incorrect Password");
            } else {
                System.out.println("Improper Command");
            }
            if(mainAccount!= null){
                System.out.println("Welcome "+mainAccount.getUserName() + ". What Do You Want?");
                System.out.println("Type balance To See Your Balance");
                System.out.println("Type transfer + SomeOne's username + amount Of money to Transfer Money");
                System.out.println("Type withdraw + amount of money To withdrawal Your Money");
                System.out.println("Type deposit + amount of money To add Extra Money To Your Money");
                System.out.println("Type history to See History Of Tractions");
                String command = "";

                while (true) {
                    line = scan.nextLine();
                    commands = new ArrayList<>();
                    for (String st : line.split(" ")) {
                        if (!st.isEmpty()) {
                            commands.add(st);
                        }
                    }
                    command = commands.get(0);
                    if (command.equals("back")) break;
                    try { //fg
                        if (command.equals("balance"))
                            System.out.println("Your Current Balance Is : " + mainAccount.balance);
                        else if (command.equals("transfer")) {
                            mainAccount.history.add(new Transfer(mainAccount, findAccount(commands.get(1), this.accounts), Long.valueOf(commands.get(2)), this.accounts));
                            findAccount(commands.get(1), this.accounts).history.add(new Transfer("deposit", findAccount(commands.get(1), this.accounts), Long.valueOf(commands.get(2))));
                        } else if (command.equals("withdraw") || command.equals("deposit")) {
                            mainAccount.history.add(new Transfer(command, mainAccount, Long.valueOf(commands.get(1))));
                        } else if (command.equals("history")) printHistory(mainAccount);
                        else System.out.println("Improper Command");

                    } catch(Exception e){

                    }
                }

            }
            System.out.println("Type signup + Your Name + Your Password To Creat New Account");
            System.out.println("Type login + Your Name + Your Password To Inter Your Account");
        }
        scan.close();
    }
    public static boolean userNameNotUsed(String userName , ArrayList<Account> arrayList){
        for(Account account : arrayList){
            if(userName.equals(account.getUserName())) return false;
        }
        return true ;
    }
    public static Account findAccount (String userName , ArrayList<Account> arrayList){
        for(Account account : arrayList){
            if(userName.equals(account.getUserName())) return account;
        }
        return null;
    }
    public void printHistory(Account a){
        for(Transfer transfer : a.history){
            System.out.print(" Type : " + transfer.type);
            System.out.print(" Doing Account : " + transfer.givingAccount.getUserName());
            if(transfer.receivingAccount != null)  System.out.print(" Doing Account : " + transfer.givingAccount.getUserName());
            System.out.print(" Amount: " + transfer.amount);
            if(transfer.possible) System.out.print(" Traction Done sucesfully ");
            else System.out.print(" Traction Failed ");
            System.out.println(transfer.time);
        }
    }
}
class Account {
    private String userName ;
    private String passWord ;
    Long balance ;
    ArrayList<Transfer> history;
    public Account ( String userName , String passWord ){
        this.userName = userName;
        this.passWord = passWord;
        this.balance = 0L ;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }
}
class Transfer {
    String type ;
    Account givingAccount ;
    Account receivingAccount ;
    Long amount ;
    String time ;
    boolean possible ;
    public Transfer (Account givingAccount , Account receivingAccount , Long amount , ArrayList<Account> Bank){
        this.type = "transfer" ;
        this.givingAccount = givingAccount ;
        if(!Bank.contains(receivingAccount)){
            System.out.println("ReceivingAccount Do Not Exist");
            possible = false ;
        } else this.receivingAccount = receivingAccount ;
        if(givingAccount.balance < amount){
            possible = false ;
            System.out.println("There Is Not Enough Money For Transfer");
        } else possible = true ;
        this.amount = amount ;
        time = 'T' + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/mm/dd HH:mm")) ;
        if(possible){
            givingAccount.balance-=amount;
            receivingAccount.balance+=amount;
        }
    }
    public Transfer (String type, Account givingAccount , Long amount ){
        this.type = type;
        this.givingAccount = givingAccount ;
        if(givingAccount.balance < amount && type.equals("withdraw")){
            possible = false ;
            System.out.println("There Is Not Enough Money For Withdraw");
        } else possible = true ;
        this.amount = amount ;
        time =  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/mm/dd HH:mm")) ;
        if(possible){
            if(type.equals("withdraw")){
                givingAccount.balance-=amount;
            } else givingAccount.balance+=amount;
        }
        time = 'T' + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/mm/dd HH:mm")) ;
    }
}

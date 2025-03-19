import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DataBase  {
    File accounts ;
    File transactions ;
    FileReader fr ;
    public DataBase (){
        accounts = new File("src/accounts.txt");
        transactions = new File("src/transactions.txt");
    }

    public StringBuilder readAccounts () throws IOException {
        StringBuilder sb = new StringBuilder();
        fr = new FileReader(accounts);
        int i ;
        while ((i = fr.read())!=-1){
            sb.append((char)i);
        }

        return sb ;
    }

    public StringBuilder readTransactions() throws IOException {
        StringBuilder sb = new StringBuilder();
        fr = new FileReader(transactions);
        int i ;
        while ((i = fr.read())!=-1){
            sb.append((char)i);
        }
        return sb ;
    }

    public static void writeJSON(HashMap<String, JSONValue> map, String path) throws IOException {
        File file = new File(path);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            writeJSONobj(map, bw);
        }
    }

    private static void writeJSONobj (HashMap<String , JSONValue> map , BufferedWriter bw) throws IOException{
        bw.write("{");
        boolean isFirstValue = true ;
        for(String key : map.keySet()){
            if(!isFirstValue) {
                bw.write(" , ");
            }
            bw.newLine();
            JSONValue val = map.get(key);
            bw.write("\"" + key + "\"" + " : ");
            if(val instanceof JSONObject) writeJSONobj(((JSONObject) val).getMap() , bw);
            else if (val instanceof JSONArray) writeJSONarray(((JSONArray) val).getArray() , bw);
            else if (val instanceof JSONNull) writeJSONnull(bw);
            else if (val instanceof JSONString ) writeJSONstring(((JSONString) val).getValue() , bw);
            else if (val instanceof JSONBool) writeJSONbool(((JSONBool) val).getValue() , bw);
            else if (val instanceof JSONNumber) writeJSONnumber(((JSONNumber) val).getValue(), bw);
            else if (val instanceof JSONTime) writeJSONtime(((JSONTime) val).getValue() , bw);
            isFirstValue = false;
        }
        bw.newLine();
        bw.write("}");

    }
    private static void writeJSONarray(ArrayList<JSONValue> array , BufferedWriter bw) throws IOException {
        bw.write("[");
        boolean isFirstValue = true ;
        for(JSONValue val : array){
            if(!isFirstValue) bw.write(" , ");
            if(val instanceof JSONObject) writeJSONobj(((JSONObject) val).getMap() , bw);
            else if (val instanceof JSONArray) writeJSONarray(((JSONArray) val).getArray() , bw);
            else if (val instanceof JSONNull) writeJSONnull(bw);
            else if (val instanceof JSONString ) writeJSONstring(((JSONString) val).getValue() , bw);
            else if (val instanceof JSONBool) writeJSONbool(((JSONBool) val).getValue() , bw);
            else if (val instanceof JSONNumber) writeJSONnumber(((JSONNumber) val).getValue(), bw);
            else if (val instanceof JSONTime) writeJSONtime(((JSONTime) val).getValue() , bw);
            isFirstValue = false;
        }
        bw.write("]");
    }
    private static void writeJSONtime (String time , BufferedWriter bw) throws IOException {
        bw.write(time);
    }
    private static void writeJSONstring (String string , BufferedWriter bw) throws IOException {
        bw.write("\"" + string + "\"");
    }
    private static void writeJSONbool (boolean bool , BufferedWriter bw) throws IOException {
        String val ;
        if(bool) val = "true" ; else val = "false";
        bw.write(val);
    }
    private static void writeJSONnull (BufferedWriter bw) throws IOException {
        bw.write("null");
    }
    private static void writeJSONnumber (double num , BufferedWriter bw) throws IOException {
        bw.write(String.valueOf(num));
    }

}

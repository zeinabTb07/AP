import java.util.HashMap;
import java.util.zip.DataFormatException;

public class JSONObject extends JSONValue {
    private  HashMap <String , JSONValue> map ;
    public  JSONObject (){
        map = new HashMap<>();
    }

    public void setMap(HashMap<String, JSONValue> map) {
        this.map = map;
    }

    public HashMap<String, JSONValue> getMap() {
        return map;
    }

    public void addValue (String key , JSONValue value){
        map.put(key , value) ;
    }

    public String GenerateKey(StringBuilder sb) throws DataFormatException {
        if(sb.charAt(0)!='"') throw new DataFormatException("\" missed");
        sb.deleteCharAt(0);
        int i = 0 ;
        while (sb.charAt(i)!='"') i++;
        String key = sb.substring(0 , i);
        sb.delete(0 , i+1);
        return key;
    }

    public JSONObject (StringBuilder sb) throws DataFormatException {
        map = new HashMap<>();
        if(sb.charAt(0)!='{') throw new DataFormatException("{ missed");
        sb.deleteCharAt(0);
        while (!sb.isEmpty()) {
            removeUpComingSpace(sb);
            String key = GenerateKey(sb);
            removeUpComingSpace(sb);
            if (sb.charAt(0) != ':') throw new DataFormatException(": missed");
            sb.deleteCharAt(0);
            removeUpComingSpace(sb);

            JSONValue value;
            if (sb.charAt(0) == 'T') value = new JSONTime(sb);
            else if (sb.charAt(0) == '"') value = new JSONString(sb);
            else if (sb.charAt(0) == 'n') value = new JSONNull(sb);
            else if (sb.charAt(0) == 't' || sb.charAt(0) == 'f') value = new JSONBool(sb);
            else if (Character.isDigit(sb.charAt(0)) || sb.charAt(0) == '.') value = new JSONNumber(sb);
            else if (sb.charAt(0) == '{') {
                int i = 1;
                int j = 1;
                while (i != 0) {
                    if (sb.charAt(j) == '{') i++;
                    else if (sb.charAt(j) == '}') i--;
                    j++;
                }
                value = new JSONObject(new StringBuilder(sb.substring(0, j)));
                sb.delete(0, j);
            } else if (sb.charAt(0) == '[') {
                int i = 1;
                int j = 1;
                while (i != 0) {
                    if (sb.charAt(j) == '[') i++;
                    else if (sb.charAt(j) == ']') i--;
                    j++;
                }
                value = new JSONArray(new StringBuilder(sb.substring(0, j)));
                sb.delete(0, j);
            } else throw new DataFormatException("No suitable jsonValue");
            map.put(key, value);
            removeUpComingSpace(sb);
            if (!sb.isEmpty()) {
                if(sb.charAt(0)=='}'){
                    sb.deleteCharAt(0);
                    removeUpComingSpace(sb);
                    if(!sb.isEmpty()) throw  new DataFormatException("mess!");
                } else if (sb.charAt(0) == ',') {
                    sb.deleteCharAt(0);
                    removeUpComingSpace(sb);
                } else {
                 throw new DataFormatException(", missed");
                }
            }
        }

    }


}

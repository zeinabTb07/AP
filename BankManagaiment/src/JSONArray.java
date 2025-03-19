import java.util.ArrayList;
import java.util.zip.DataFormatException;

public class JSONArray extends JSONValue {
    private ArrayList<JSONValue> array;
    public JSONArray () {
        array = new ArrayList<>();
    }

    public ArrayList<JSONValue> getArray() {
        return array;
    }

    public void setArray(ArrayList<JSONValue> array) {
        this.array = array;
    }
    public void addValue (JSONValue value) {
        array.add(value);
    }

    public JSONArray (StringBuilder sb) throws DataFormatException {
        if (sb.charAt(0) != '[') throw new DataFormatException("[ missed");
        sb.deleteCharAt(0);
        while (!sb.isEmpty()) {
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
            array.add(value);
            removeUpComingSpace(sb);
            if (!sb.isEmpty()) {
                if(sb.charAt(0)==']'){
                    sb.deleteCharAt(0);
                    removeUpComingSpace(sb);
                    if(!sb.isEmpty()) throw new DataFormatException("mess!");
                } else if (sb.charAt(0)!=',') throw new DataFormatException(", missed");
                else {
                    sb.deleteCharAt(0);
                    removeUpComingSpace(sb);
                }
            }
        }
    }

}

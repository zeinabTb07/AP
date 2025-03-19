abstract public class JSONValue {
    public static void removeUpComingSpace (StringBuilder sb) {
        while (!sb.isEmpty() && (sb.charAt(0)==' ' || sb.charAt(0)=='\r' || sb.charAt(0)=='\n')){
             sb.deleteCharAt(0);
        }
    }
}

import java.util.zip.DataFormatException;

abstract public class JSONLiteral extends JSONValue {

}
class JSONString extends JSONLiteral {
    private String value ;
    public String getValue () {
        return value;
    }
    private void setValue(String string) {
        value = string ;
    }
    public JSONString (String string){
        value = string ;
    }
    public JSONString(StringBuilder sb) throws DataFormatException {
        if(sb.charAt(0)!='"') throw  new DataFormatException("\" missed");
        int i = 1;
        while (sb.charAt(i)!='"'){
            i++ ;
            if(sb.charAt(i)=='\\'){
             while (sb.charAt(i)=='\\') i+=2 ;
            }
        }
        value = sb.substring(1 , i);
        sb.delete(0 , i+1);
    }
}
class JSONBool extends JSONLiteral {
    private boolean value;

    public JSONBool(boolean bool) {
        value = bool;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    public JSONBool(StringBuilder sb) throws DataFormatException {
        if (sb.substring(0, 4).equals("true")) {
            value = true;
            sb.delete(0, 4);
        } else if (sb.substring(0, 5).equals("false")) {
            value = false;
            sb.delete(0, 5);
        } else throw new DataFormatException("Not a boolean");
    }
}

    class JSONNumber extends JSONLiteral{
        private Double value ;

        public JSONNumber (StringBuilder sb) {
            int i = 0 ;
            while (Character.isDigit(sb.charAt(i)) || sb.charAt(i)=='.'){
                i++ ;
            }
            value = Double.valueOf(sb.substring(0 , i)) ;
            sb.delete(0 , i);
        }
        public JSONNumber (Double num) {
            value = num ;
        }
        public double getValue() {
            return value;
        }
        private void setValue(double number){
            value = number;
        }
    }

    class JSONTime extends JSONLiteral {
        private String value ;
        public JSONTime (String time){
            value = time ;
        }
        public String getValue (){
            return value ;
        }

        public void setValue(String value) {
            this.value = value;
        }


        public JSONTime (StringBuilder sb) throws DataFormatException {
            if(sb.charAt(0)=='T') {
                for(int i = 1 ; i < sb.length() ; i++){
                    if(sb.charAt(i)=='T') {
                        value = sb.substring(0 , i+1);
                        sb.delete(0 , i + 1);
                        break;
                    }
                }
            } else throw new DataFormatException("T missed");
        }

    }
    class JSONNull extends JSONLiteral {
        public JSONNull (StringBuilder sb) throws DataFormatException {
            if(sb.substring(0 , 4).equals("null")){
                sb.delete(0 , 4);
            } else throw new DataFormatException("Not null");
        }
    }







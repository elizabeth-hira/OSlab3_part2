import java.util.StringTokenizer;

public class Request {

    public enum RequestType { GET, POST }
    public RequestType type;

    private String key;
    private String value;

    public String getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }

    private void setKey(String key) {
        this.key = key;
    }
    private void setValue(String value) {
        this.value = value;
    }
    private void setType(RequestType type){
        this.type = type;
    }

    public static Request parse(String line) throws Exception{
        Request request = new Request();
        StringTokenizer tokenizer = new StringTokenizer(line, " ");
        String strType = tokenizer.nextToken();
        request.setType(RequestType.valueOf(strType));
        String url = tokenizer.nextToken();
        tokenizer = new StringTokenizer(url, "/?&=");
        try {
            request.setKey(tokenizer.nextToken());
            request.setValue(tokenizer.nextToken());
        }
        catch(Exception err) {
            err.printStackTrace();
        }
        if(tokenizer.hasMoreTokens())
            throw new Exception("Invalid request");
        return request;
    }
}
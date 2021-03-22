import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.StringTokenizer;

public class Request {

    public enum RequestType { GET, POST }

    @Getter @Setter(AccessLevel.PRIVATE)
    private RequestType type;

    @Getter @Setter(AccessLevel.PRIVATE)
    private String key;

    @Getter @Setter(AccessLevel.PRIVATE)
    private String value;

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
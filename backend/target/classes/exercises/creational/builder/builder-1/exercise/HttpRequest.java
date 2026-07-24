import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// Provided — do not edit
class HttpRequest {
    final String method;
    final String url;
    final Map<String, String> headers;
    final String body;

    HttpRequest(String method, String url, Map<String, String> headers, String body) {
        this.method  = method;
        this.url     = url;
        this.headers = Collections.unmodifiableMap(new HashMap<>(headers));
        this.body    = body;
    }
}

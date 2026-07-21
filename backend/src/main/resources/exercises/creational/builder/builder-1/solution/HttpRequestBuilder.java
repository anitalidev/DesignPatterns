import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

class HttpRequestBuilder {
    private String method;
    private String url;
    private Map<String, String> headers = new HashMap<>();
    private String body;

    public HttpRequestBuilder method(String method) {
        this.method = method;
        return this;
    }

    public HttpRequestBuilder url(String url) {
        this.url = url;
        return this;
    }

    public HttpRequestBuilder header(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    public HttpRequestBuilder body(String body) {
        this.body = body;
        return this;
    }

    public HttpRequest build() {
        if (method == null || method.isBlank()) throw new IllegalStateException("method is required");
        if (url    == null || url.isBlank())    throw new IllegalStateException("url is required");
        return new HttpRequest(method, url, headers, body);
    }
}

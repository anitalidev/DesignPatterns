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

// TODO: complete this builder
class HttpRequestBuilder {
    // TODO: declare fields for method, url, headers, and body

    public HttpRequestBuilder method(String method) {
        // TODO: store method, return this
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public HttpRequestBuilder url(String url) {
        // TODO: store url, return this
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public HttpRequestBuilder header(String key, String value) {
        // TODO: add to headers map, return this
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public HttpRequestBuilder body(String body) {
        // TODO: store body, return this
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public HttpRequest build() {
        // TODO: validate that method and url are set, then return a new HttpRequest
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

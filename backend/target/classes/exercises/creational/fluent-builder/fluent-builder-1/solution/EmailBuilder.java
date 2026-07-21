import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Email {
    final String to;
    final List<String> cc;
    final String subject;
    final String body;
    final List<String> attachments;

    Email(String to, List<String> cc, String subject, String body, List<String> attachments) {
        this.to          = to;
        this.cc          = Collections.unmodifiableList(new ArrayList<>(cc));
        this.subject     = subject;
        this.body        = body;
        this.attachments = Collections.unmodifiableList(new ArrayList<>(attachments));
    }
}

class EmailBuilder {
    private String to;
    private List<String> cc          = new ArrayList<>();
    private String subject;
    private String body;
    private List<String> attachments = new ArrayList<>();

    public EmailBuilder to(String to) {
        this.to = to;
        return this;
    }

    public EmailBuilder cc(String cc) {
        this.cc.add(cc);
        return this;
    }

    public EmailBuilder subject(String subject) {
        this.subject = subject;
        return this;
    }

    public EmailBuilder body(String body) {
        this.body = body;
        return this;
    }

    public EmailBuilder attach(String filename) {
        this.attachments.add(filename);
        return this;
    }

    public Email build() {
        if (to == null || to.isBlank()) throw new IllegalStateException("'to' is required");
        return new Email(to, cc, subject, body, attachments);
    }
}

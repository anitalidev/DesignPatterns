import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Provided — do not edit
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

// TODO: complete this fluent builder
class EmailBuilder {
    // TODO: declare fields

    public EmailBuilder to(String to) {
        // TODO: store to, return this
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public EmailBuilder cc(String cc) {
        // TODO: add to cc list, return this
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public EmailBuilder subject(String subject) {
        // TODO: store subject, return this
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public EmailBuilder body(String body) {
        // TODO: store body, return this
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public EmailBuilder attach(String filename) {
        // TODO: add to attachments list, return this
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Email build() {
        // TODO: validate 'to' is set, then return a new Email
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

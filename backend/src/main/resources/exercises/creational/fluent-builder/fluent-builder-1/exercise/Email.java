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

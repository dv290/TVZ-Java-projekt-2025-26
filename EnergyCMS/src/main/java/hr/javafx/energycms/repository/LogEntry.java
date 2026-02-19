package hr.javafx.energycms.repository;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDateTime;

public class LogEntry {
    private String action;
    private LocalDateTime timestamp;

    public LogEntry() {}

    public LogEntry(String action, LocalDateTime timestamp) {
        this.action = action;
        this.timestamp = timestamp;
    }

    @XmlElement
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}

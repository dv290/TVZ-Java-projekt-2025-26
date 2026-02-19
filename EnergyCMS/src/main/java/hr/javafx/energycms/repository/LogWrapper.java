package hr.javafx.energycms.repository;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "logs")
public class LogWrapper {
    private List<LogEntry> entries;

    @XmlElement(name = "logEntry")
    public List<LogEntry> getEntries() { return entries; }
    public void setEntries(List<LogEntry> entries) { this.entries = entries; }
}

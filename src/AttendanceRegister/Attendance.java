package AttendanceRegister;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Attendance {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    private Date date;
    private String subject;
    private Map<Participant, Boolean> map;

    Attendance(String date, String subject) {
        this.date = dateFormat.parse(date, new ParsePosition(0));
        this.subject = subject;
        map = new HashMap<>();
    }

    void addParticipant(Participant newParticipant) {
        try {
            map.put(newParticipant, false);
        } catch (Exception exc) {
        }
    }

    Date getDate() {
        return date;
    }

    void setDate(String newDate) {
        this.date = dateFormat.parse(newDate, new ParsePosition(0));
    }

    String getFormattedDate() {
        return dateFormat.format(date);
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "date=" + date +
                ", subject='" + subject + '\'' +
                ", map=" + map +
                ", dateFormat=" + dateFormat +
                '}';
    }

    String getSubject() {
        return subject;
    }

    void setSubject(String subject) {
        this.subject = subject;
    }

    Map<Participant, Boolean> getMap() {
        return map;
    }

}

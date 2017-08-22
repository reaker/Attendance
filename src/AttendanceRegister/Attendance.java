package AttendanceRegister;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Attendance {

    private Date date;
    private String subject;
    private Map<Participant, Boolean> map;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    Attendance(String date, String subject) {
        this.date = dateFormat.parse(date,new ParsePosition(0));
        this.subject = subject;
        map=new HashMap<>();
    }

    public void addParticipant(Participant newParticipant) {
        map.put(newParticipant,false);
    }

    void addParticipant(String firstName, String lastName, String phoneNumber, String date){
        map.put(new Participant(firstName,lastName,Integer.parseInt(phoneNumber),date),false);
    }

    Date getDate() {
        return date;
    }
    String getFormattedDate() {
        return dateFormat.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    void setDate(String newDate) {
        this.date = dateFormat.parse(newDate,new ParsePosition(0));
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Map<Participant, Boolean> getMap() {
        return map;
    }

    public void setMap(Map<Participant, Boolean> map) {
        this.map = map;
    }
}

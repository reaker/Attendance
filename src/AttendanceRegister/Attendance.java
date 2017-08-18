package AttendanceRegister;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Attendance {

    private Date date;
    private String subject;
    private Map<Integer, Participant> map;
    private Map<Integer, Boolean> mapPresence;

    public Attendance(Date date, String subject, Map<Integer, Participant> map) {
        this.date = date;
        this.subject = subject;
        this.map = new HashMap<Integer, Participant>();
    }

    Attendance(String date, String subject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date date1 = dateFormat.parse(date,new ParsePosition(0));
        this.date = date1;
        this.subject = subject;
        map=new HashMap<>();
        mapPresence=new HashMap<>();
    }


    public void addParticipant(Participant newParticipant, Integer key) {
        this.map.put(key, newParticipant);
    }

    void addParticipant(String firstName, String lastName, String phoneNumber, String date){
        Participant newParticipant= new Participant(firstName,lastName,Integer.parseInt(phoneNumber),date);
        int participantID= newParticipant.getNewId();
        map.put(participantID,newParticipant);
    }

    void addParticipant(int participantID,String firstName, String lastName, String phoneNumber, String date){
        Participant newParticipant= new Participant(participantID,firstName,lastName,Integer.parseInt(phoneNumber),date);
        map.put(participantID,newParticipant);
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Map<Integer, Participant> getMap() {
        return map;
    }

    public void setMap(Map<Integer, Participant> map) {
        this.map = map;
    }
}

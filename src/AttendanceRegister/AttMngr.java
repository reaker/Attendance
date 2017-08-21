package AttendanceRegister;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class AttMngr {

    private static int maxAttendanceID = 0;
    private static int minAttendanceID = 0;
    private static AttMngr instance = null;

    private static List<Integer> IDList = new ArrayList<>();

    private static int actualKeyMap=0;
    private Map<Integer, Attendance> attendanceMap;
    private NodeList attendanceNodeList;
    private Path filePath = Paths.get("C:/Java/AttendanceRegister/atta.xml");


    private AttMngr() {
        loadData();
    }

    public static AttMngr getInstance() {
        if (instance == null) instance = new AttMngr();
        return instance;
    }

    private void loadData() {
        attendanceMap = new HashMap<>();
        File xmlFileToOpen = new File(String.valueOf(filePath));
        if (!xmlFileToOpen.exists()) {
            System.out.println("File doesn't exist. Creating new.");
            //create new
            try {
                FileWriter fr = new FileWriter(xmlFileToOpen);
                fr.write("<?xml version=\"1.0\"?>");
                fr.write("\n<attendanceRegister>");
                fr.write("\n</attendanceRegister>");
                fr.close();
                maxAttendanceID = 0;
                minAttendanceID = 0;

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            loadXML(xmlFileToOpen);

            //getting max and min attendanceID
            int i=0;
            for (Map.Entry<Integer,Attendance> entry : attendanceMap.entrySet()) {
//                System.out.println("Key: "+ entry.getKey()+" Value: " + entry.getValue());
                if (i==0){
                    minAttendanceID=entry.getKey();
                    i++;
                    setActualKeyMap(minAttendanceID);
//                    System.out.println("Min is: " + minAttendanceID);
                }
                else if (entry.getKey()==attendanceMap.size()){
                    maxAttendanceID=entry.getKey();
//                    System.out.println("max is: " +maxAttendanceID);
                }
            }
        }
    }

    int getActualKeyMap() {
        return actualKeyMap;
    }

    void setActualKeyMap(int newActualKeyMap) {
        actualKeyMap = newActualKeyMap;
//        System.out.println("Actual key map: "+actualKeyMap);
    }

    void setFirstAvailableKeyMapForNotEmptyMap(){
        for (Map.Entry<Integer,Attendance> entry : attendanceMap.entrySet()) {
            int firstAvailable;
            firstAvailable= entry.getKey();
            setActualKeyMap(firstAvailable);
            break;
        }
    }

    private void loadXML(File xmlFileToConvert) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFileToConvert);
            doc.getDocumentElement().normalize();
            attendanceNodeList = doc.getElementsByTagName("attendance");

            System.out.print("Creating Attendance Manager from XML file...");

            for (int i = 0; i < attendanceNodeList.getLength(); i++) {
                NodeList attendanceChildNodes = (attendanceNodeList.item(i)).getChildNodes();

                Element actualAttendance = (Element) attendanceNodeList.item(i);
                int actualAttendanceID = Integer.parseInt(actualAttendance.getAttribute("id"));
                String date = null, subject = null;

                for (int j = 0; j < attendanceChildNodes.getLength(); j++) {
                    Element element = (Element) attendanceChildNodes.item(j);
                    switch (element.getNodeName()) {
                        case "date": {
                            date = element.getTextContent();
                            continue;
                        }
                        case "subject": {
                            subject = element.getTextContent();
                            attendanceMap.put(actualAttendanceID, new Attendance(date, subject));
                            IDList.add(actualAttendanceID);
                            continue;
                        }
                        case "participant": {
                            int actualParticipantID = Integer.parseInt(element.getAttribute("id"));
                            NodeList participantChildNodes = element.getChildNodes();
                            Element elementP;
                            String firstName = null, lastName = null, phoneNumber = null, birthDate = null;

                            for (int k = 0; k < participantChildNodes.getLength(); k++) {
                                elementP = (Element) participantChildNodes.item(k);

                                switch (elementP.getNodeName()) {
                                    case "firstname": {
                                        firstName = elementP.getTextContent();
                                        continue;
                                    }
                                    case "lastname": {
                                        lastName = elementP.getTextContent();
                                        continue;
                                    }
                                    case "phoneNumber": {
                                        phoneNumber = elementP.getTextContent();
                                        continue;
                                    }
                                    case "birthDate": {
                                        birthDate = elementP.getTextContent();
                                        attendanceMap.get(actualAttendanceID).addParticipant(actualParticipantID, firstName, lastName, phoneNumber, birthDate);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("DONE");
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        //printAll();
    }

    public Map<Integer, Attendance> getAttMap() {
        return attendanceMap;
    }

    void printAll() {

        System.out.println("==========Showing data in Attendance Manager============");
        for (Map.Entry<Integer, Attendance> att : attendanceMap.entrySet()
                ) {
            Attendance actualAttendance = att.getValue();
            System.out.println("    Attendance no. " + att.getKey());
            System.out.println("    Data: " + actualAttendance.getDate().toString());
            System.out.println("    Subject: " + actualAttendance.getSubject());

            for (Map.Entry<Integer, Participant> participant : actualAttendance.getMap().entrySet()
                    ) {
                System.out.println("        Participant no. " + participant.getKey());
                System.out.println("             first name: " + participant.getValue().getFirstName());
                System.out.println("             last name: " + participant.getValue().getLastName());
                System.out.println("             phone number: " + participant.getValue().getTelephoneNo());
                System.out.println("             birth date: " + participant.getValue().getDateOfBirth());
            }
        }


    }

//    void mapListToString() {
//
//        printAll();
//        StringBuilder sb = new StringBuilder("");
//        sb.append("<?xml version=1.0?>\n<attendanceRegister>");
//
//        Element element;
//        for (int i = 0; i < attendanceNodeList.getLength(); i++) {
//            sb.append("<attendance id=\"");
//            Element thisAttendance=(Element)attendanceNodeList.item(i);
//            sb.append(thisAttendance.getAttribute("id")+"\">");
//
//            NodeList childNodes = attendanceNodeList.item(i).getChildNodes();
//            for (int j = 0; j < childNodes.getLength(); j++) {
//                Node nodeElement = childNodes.item(i);
//                element = (Element) childNodes.item(i);
//                element.getElementsByTagName("attendance");
//                switch (element.getNodeName()) {
//                    case "date": {
//                        sb.append("<date>" + element.getTextContent() + "</date>");
//                    }
//                    case "subject": {
//                        sb.append("<subject>" + element.getTextContent() + "</subject>");
//                    }
//                    case "participant": {
//                        sb.append("<participant id=\"" + element.getAttribute("id") + "\">");
//
//                        NodeList nodeListP = element.getChildNodes();
//                        for (int k = 0; k < nodeListP.getLength(); k++) {
//                            System.out.println(nodeListP.item(k).getNodeName());
//                        }
//                        Element elementP;
//
//                        for (int k = 0; k < nodeListP.getLength(); k++) {
//                            elementP = (Element) nodeListP.item(k);   /// dont know why xml document matters if it has or has not newlines.... it shouldn't matter
//
//                            switch (elementP.getNodeName()) {
//
//                                case "firstname": {
//                                    sb.append("<firstname>" + element.getTextContent() + "</firstname>");
//                                }
//                                case "lastname": {
//                                    sb.append("<lastname>" + element.getTextContent() + "</lastname>");
//                                }
//                                case "phoneNumber": {
//                                    sb.append("<phoneNumber>" + element.getTextContent() + "</phoneNumber>");
//                                }
//                                case "birthDate": {
//                                    sb.append("<birthDate>" + element.getTextContent() + "</birthDate>");
//                                }
//                            }
//                        }
//                        sb.append("</participant>");
//                    } //TODO add presence
//                }
//                sb.append("</attendance>");
//            }
//        }
//        sb.append("</attendanceRegister>");
//        System.out.println(sb);
////        return sb.toString();
//    }

    String getXMLString(){
        StringBuilder sb = new StringBuilder("");
        sb.append("<?xml version=\"1.0\"?>\n<attendanceRegister>");

        SimpleDateFormat birthDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat attendanceDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        for (Map.Entry<Integer,Attendance> entry: attendanceMap.entrySet()) {
            sb.append("<attendance id=\""+entry.getKey()+"\">"); //id atendance
            String date = attendanceDateFormat.format(entry.getValue().getDate());
            sb.append("<date>"+date+"</date>");//date
            sb.append("<subject>"+entry.getValue().getSubject()+"</subject>");

            Map<Integer,Participant> participantMap = entry.getValue().getMap();

            for (Map.Entry<Integer,Participant> entryP:participantMap.entrySet()) {
                sb.append("<participant id=\"" + entryP.getKey() + "\">");
                sb.append("<firstname>" + entryP.getValue().getFirstName() + "</firstname>");
                sb.append("<lastname>" + entryP.getValue().getLastName() + "</lastname>");
                sb.append("<phoneNumber>" + entryP.getValue().getTelephoneNo() + "</phoneNumber>");
                String dateP= birthDateFormat.format(entryP.getValue().getDateOfBirth());

                sb.append("<birthDate>" +  dateP + "</birthDate>" + "</participant>");
            }
            sb.append("</attendance>");
        }

        sb.append("</attendanceRegister>");
        return sb.toString();

    }

    void saveToFile() {
        try {
            Files.delete(filePath);
            File newFile = new File(String.valueOf(filePath));
            FileWriter fr = new FileWriter(newFile);
            fr.write(getXMLString());
            fr.close();
            JOptionPane.showMessageDialog(null,"File Saved.");
            System.out.println("File saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void addAttendance(String dateString, String subject) {
        maxAttendanceID++;
        Attendance newAttendance = new Attendance(dateString, subject);
        actualKeyMap = maxAttendanceID;
        attendanceMap.put(maxAttendanceID, newAttendance);
    }

    public NodeList getNodeList() {
        return attendanceNodeList;
    }



    public static void setMaxAttendanceID(int maxAttendanceID) {
        AttMngr.maxAttendanceID = maxAttendanceID;
    }

    public static void setMinAttendanceID(int minAttendanceID) {
        AttMngr.minAttendanceID = minAttendanceID;
    }
}

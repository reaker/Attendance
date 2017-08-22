package AttendanceRegister;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Participant {

    private String firstName, lastName;
    private int phoneNumber;
    private Date dateOfBirth;
    private boolean presence=false;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    Participant(String firstName, String lastName, int phoneNumber, String dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            this.dateOfBirth = dateFormat.parse(dateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        if (o instanceof Participant) {
            Participant otherObject = (Participant) o;
            return  phoneNumber==otherObject.phoneNumber && firstName.equals(otherObject.firstName) && lastName.equals(otherObject.lastName) && dateOfBirth.equals(otherObject.dateOfBirth);
        }
        return  false;
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + phoneNumber;
        result = 31 * result + dateOfBirth.hashCode();
        return result;
    }

    String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

     int getTelephoneNo() {
        return phoneNumber;
    }

    public void setTelephoneNo(int telephoneNo) {
        this.phoneNumber = telephoneNo;
    }

    Date getDateOfBirth() {
        return dateOfBirth;
    }

    String getStringDateOfBirth() {
        return dateFormat.format(dateOfBirth);
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean getPresence() {
        return presence;
    }
}

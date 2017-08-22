package AttendanceRegister;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Participant {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private String firstName, lastName;
    private int phoneNumber;
    private Date dateOfBirth;

    Participant(String firstName, String lastName, int phoneNumber, String dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            this.dateOfBirth = dateFormat.parse(dateOfBirth);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Wrong input data!");
        }
    }

    void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        if (o instanceof Participant) {
            Participant otherObject = (Participant) o;
            return phoneNumber == otherObject.phoneNumber && firstName.equals(otherObject.firstName) && lastName.equals(otherObject.lastName) && dateOfBirth.equals(otherObject.dateOfBirth);
        }
        return false;
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

    void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    String getLastName() {
        return lastName;
    }

    void setLastName(String lastName) {
        this.lastName = lastName;
    }

    int getTelephoneNo() {
        return phoneNumber;
    }

    Date getDateOfBirth() {
        return dateOfBirth;
    }

    void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    String getStringDateOfBirth() {
        return dateFormat.format(dateOfBirth);
    }

}

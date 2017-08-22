package AttendanceRegister;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

class GUI extends JPanel {
    private final SimpleDateFormat dateAttFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    private final SimpleDateFormat dateBirthFormat = new SimpleDateFormat("dd-MM-yyyy");
    private AttMngr manager = AttMngr.getInstance();
    private Attendance actualAttendance;
    private DefaultTableModel model;
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Sebastian Wijas
    private JPanel attPanel;
    private JLabel lAttDate;
    private JLabel lAttSubject;
    private JTextField txtAttDate;
    private JTextField txtAttSubject;
    private JLabel label10;
    private JPanel navPanel;
    private JButton bPrev;
    private JButton bNext;
    private JButton bUpdate;
    private JButton bDelete;
    private JButton bNew;
    private JPanel PartiList;
    private JScrollPane scrollPane1;
    private JTable tabParti;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtPhoneNo;
    private JTextField txtBirthDate;
    private JButton bNewParti;
    private JPanel PartPanel;
    private JButton bUpdateParti;
    private JButton bDeleteParti;
    private JButton bShowAbsent;
    private JPanel SavePanel;
    private JButton bExit;
    private JButton bSave;
    private JLabel label6;
    private JLabel label7;
    private JLabel label8;
    private JLabel label9;
    private JLabel label11;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    GUI() {
        initComponents();
        setMinimumSize(new Dimension(750, 600));

        String firstSubject = null;
        Date firstDate = null;
        model = (DefaultTableModel) tabParti.getModel();
        //only to show first data in GUI
        for (Map.Entry<Integer, Attendance> entry : manager.getAttMap().entrySet()) {
            Attendance firstAttendance = entry.getValue();
            Participant participant;
            for (Map.Entry<Participant, Boolean> entryP : firstAttendance.getMap().entrySet()) {
                participant = entryP.getKey();
                model.addRow(new Object[]{participant.getFirstName(), participant.getLastName(), participant.getTelephoneNo(), participant.getStringDateOfBirth(), entryP.getValue()});
            }
            firstSubject = firstAttendance.getSubject();
            firstDate = firstAttendance.getDate();
            break;
        }
        if (manager.getAttMap().isEmpty()) {
            bUpdate.setEnabled(false);
            bDelete.setEnabled(false);
            bDeleteParti.setEnabled(false);
            bUpdateParti.setEnabled(false);
            bSave.setEnabled(false);
            bNewParti.setEnabled(false);
            bShowAbsent.setEnabled(false);
        }
        if (firstDate != null) txtAttDate.setText(dateAttFormat.format(firstDate));
        txtAttSubject.setText(firstSubject);

        setVisible(true);
    }

    //NAVIGATE ATTENDANCE
    private void bPrevActionPerformed() {
        int mapSize = manager.getAttMap().size();
        if (mapSize <= 0) { // jesli zszedles ponizej size to pokaz pustą stronę
            txtAttSubject.setText("");
            txtAttDate.setText("");
            actualAttendance = null;
            AttMngr.setMinAttendanceID(0);
            AttMngr.setMaxAttendanceID(0);

        } else if (mapSize == 1) {
            JOptionPane.showMessageDialog(null, "End of file.");
        } else {
            getActualAttendance();

            int i = 0, previousIDKeyMap;
            int actualID = manager.getActualKeyMap();
            int result = -1;
            for (Map.Entry<Integer, Attendance> entry : manager.getAttMap().entrySet()) {
                i++;
                previousIDKeyMap = entry.getKey();
                if (previousIDKeyMap < actualID) {  //find previous
                    if (previousIDKeyMap > result) {
                        result = previousIDKeyMap;
                    }
                }
                if (previousIDKeyMap == actualID && result == -1) {
                    result = previousIDKeyMap;
                }

            }
            manager.setActualKeyMap(result);

            txtAttSubject.setText(manager.getAttMap().get(manager.getActualKeyMap()).getSubject());
            txtAttDate.setText(manager.getAttMap().get(manager.getActualKeyMap()).getFormattedDate());
        }
        rewriteTable();
    }

    private void bNextActionPerformed() {
        int mapSize = manager.getAttMap().size();
        if (mapSize <= 0) { // jesli zszedles ponizej size to pokaz pustą stronę
            txtAttSubject.setText("");
            txtAttDate.setText("");
            actualAttendance = null;
            AttMngr.setMinAttendanceID(0);
            AttMngr.setMaxAttendanceID(0);

        } else if (mapSize == 1) {
            JOptionPane.showMessageDialog(null, "End of file.");
        } else {
            getActualAttendance();

            int i = 0, nextIDKeyMap;
            int actualID = manager.getActualKeyMap();
            int result = 999999999;
            for (Map.Entry<Integer, Attendance> entry : manager.getAttMap().entrySet()) {
                nextIDKeyMap = entry.getKey();
                i++;
                if (nextIDKeyMap > actualID) {
                    if (nextIDKeyMap < result) {
                        result = nextIDKeyMap;
                    }
                }
                if (nextIDKeyMap == actualID && result == 999999999 && mapSize == i) {
                    result = nextIDKeyMap;
                }

            }
            manager.setActualKeyMap(result);
            txtAttSubject.setText(manager.getAttMap().get(manager.getActualKeyMap()).getSubject());
            txtAttDate.setText(manager.getAttMap().get(manager.getActualKeyMap()).getFormattedDate());
        }
        rewriteTable();
    }


    private void getActualAttendance() {
        actualAttendance = manager.getAttMap().get(manager.getActualKeyMap());
    }

    //CRUD ATTENDANCE
    private void bNewActionPerformed() {
        String date = JOptionPane.showInputDialog("Enter date. /format: 31-12-2001 14:10");
        String subject = JOptionPane.showInputDialog("Enter subject.");
        if (date == null || subject == null || date.equals("") || subject.equals("")) return;
        try {
            dateAttFormat.format(date);
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Wrong input");
            return;
        }

        manager.addAttendance(date, subject);
        if (!manager.getAttMap().isEmpty()) {
            bUpdate.setEnabled(true);
            bDelete.setEnabled(true);
            bDeleteParti.setEnabled(true);
            bUpdateParti.setEnabled(true);
            bSave.setEnabled(true);
            bNewParti.setEnabled(true);
            bShowAbsent.setEnabled(true);
        }
        txtAttDate.setText(date);
        txtAttSubject.setText(subject);
    }

    private void bUpdateActionPerformed() {
        getActualAttendance();
        actualAttendance.setSubject(txtAttSubject.getText());
        actualAttendance.setDate(txtAttDate.getText());
        txtAttDate.setText(txtAttDate.getText());
        txtAttSubject.setText(txtAttSubject.getText());
        System.out.println("UPDATED");
    }

    private void bDeleteActionPerformed() {
        getActualAttendance();

        int temp = manager.getActualKeyMap();
        manager.getAttMap().remove(temp);

        if (manager.getAttMap().isEmpty()) {
            txtAttDate.setText("");
            txtAttSubject.setText("");
            bDelete.setEnabled(false);
            bUpdate.setEnabled(false);
            bShowAbsent.setEnabled(false);
            bNewParti.setEnabled(false);
            bSave.setEnabled(false);
            bDeleteParti.setEnabled(false);
            bUpdateParti.setEnabled(false);
        } else {
            manager.setFirstAvailableKeyMapForNotEmptyMap();
            getActualAttendance();
            txtAttSubject.setText(manager.getAttMap().get(manager.getActualKeyMap()).getSubject());
            txtAttDate.setText(manager.getAttMap().get(manager.getActualKeyMap()).getFormattedDate());
        }
        System.out.println("DELETED.");
    }

    private void scrollPane1PropertyChange() {
    } //useless but JFormDesigner bind it with generated code

    private void tabPartiPropertyChange() {


    } //useless but JFormDesigner bind it with generated code


    //SAVE & EXIT
    private void bSaveActionPerformed() {
        manager.saveToFile();
    }

    private void bExitActionPerformed() {
        Frame[] frames = Frame.getFrames();
        for (Frame frame : frames) {
            frame.dispose();
        }
    }

    //Participant CRUD
    //ADD NEW
    private void bNewPartiActionPerformed(ActionEvent e) {

        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String phoneNumber = txtPhoneNo.getText();
        String dateOfBirth = txtBirthDate.getText();

        getActualAttendance();
        try {
            Participant newParticipant = new Participant(firstName, lastName, Integer.parseInt(phoneNumber), dateOfBirth);
            int mapSize = actualAttendance.getMap().size(); //10
            actualAttendance.addParticipant(newParticipant); //11
            int newMapSize = actualAttendance.getMap().size();
            if (!(newMapSize == mapSize)) { // dont add duplicate content
                model.addRow(new Object[]{newParticipant.getFirstName(), newParticipant.getLastName(), newParticipant.getTelephoneNo(), newParticipant.getStringDateOfBirth(), false});
            }
            txtFirstName.setText("");
            txtBirthDate.setText("");
            txtLastName.setText("");
            txtPhoneNo.setText("");
        } catch (NumberFormatException exc) {
            JOptionPane.showMessageDialog(null, "Wrong input!");
        }

    }

    //UPDATE
    private void bUpdatePartiActionPerformed() {

        getActualAttendance();

        int row = 0;
        for (Map.Entry<Participant, Boolean> entry : actualAttendance.getMap().entrySet()) {

            String firstName = model.getValueAt(row, 0).toString();
            String lastName = model.getValueAt(row, 1).toString();
            int phoneNumber = Integer.parseInt(model.getValueAt(row, 2).toString());
            String birthDate = model.getValueAt(row, 3).toString();
            boolean presence = (boolean) model.getValueAt(row, 4);

            if (!entry.getKey().equals(new Participant(firstName, lastName, phoneNumber, birthDate)) || presence != entry.getValue()) {
                entry.getKey().setFirstName(firstName);
                entry.getKey().setLastName(lastName);
                entry.getKey().setDateOfBirth(dateBirthFormat.parse(birthDate, new ParsePosition(0)));
                entry.getKey().setPhoneNumber(phoneNumber);
                entry.setValue(presence);
            }
            row++;
        }

        rewriteTable();

    }

    //DELETE
    private void bDeletePartiActionPerformed() {
        getActualAttendance();
        String firstName, lastName, dateOfBirth;
        int phoneNumber;
        if (tabParti.getSelectedRow() > 0) {
            firstName = (String) tabParti.getValueAt(tabParti.getSelectedRow(), 0);
            lastName = (String) tabParti.getValueAt(tabParti.getSelectedRow(), 1);
            phoneNumber = (Integer) tabParti.getValueAt(tabParti.getSelectedRow(), 2);
            dateOfBirth = (String) tabParti.getValueAt(tabParti.getSelectedRow(), 3);
            actualAttendance.getMap().remove(new Participant(firstName, lastName, phoneNumber, dateOfBirth));
            model.removeRow(tabParti.getSelectedRow());
        }
    }

    //OTHER
    private void rewriteTable() {

        model.setRowCount(0);
        getActualAttendance();
        if (actualAttendance == null) return;
        int i = 0;
        for (Map.Entry<Participant, Boolean> entry : actualAttendance.getMap().entrySet()) {
            model.insertRow(i, new Object[]{entry.getKey().getFirstName(), entry.getKey().getLastName(), entry.getKey().getTelephoneNo(), entry.getKey().getStringDateOfBirth(), entry.getValue()});
            i++;
        }
    }

    private void button1ActionPerformed() {
        model.setRowCount(0);
        getActualAttendance();
        int i = 0;
        for (Map.Entry<Participant, Boolean> entry : actualAttendance.getMap().entrySet()) {
            if (!entry.getValue()) {
                model.insertRow(i, new Object[]{entry.getKey().getFirstName(), entry.getKey().getLastName(), entry.getKey().getTelephoneNo(), entry.getKey().getStringDateOfBirth(), entry.getValue()});
                i++;
            }
        }
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Sebastian Wijas
        attPanel = new JPanel();
        lAttDate = new JLabel();
        lAttSubject = new JLabel();
        txtAttDate = new JTextField();
        txtAttSubject = new JTextField();
        label10 = new JLabel();
        navPanel = new JPanel();
        bPrev = new JButton();
        bNext = new JButton();
        bUpdate = new JButton();
        bDelete = new JButton();
        bNew = new JButton();
        PartiList = new JPanel();
        scrollPane1 = new JScrollPane();
        tabParti = new JTable();
        txtFirstName = new JTextField();
        txtLastName = new JTextField();
        txtPhoneNo = new JTextField();
        txtBirthDate = new JTextField();
        bNewParti = new JButton();
        PartPanel = new JPanel();
        bUpdateParti = new JButton();
        bDeleteParti = new JButton();
        bShowAbsent = new JButton();
        SavePanel = new JPanel();
        bExit = new JButton();
        bSave = new JButton();
        label6 = new JLabel();
        label7 = new JLabel();
        label8 = new JLabel();
        label9 = new JLabel();
        label11 = new JLabel();

        //======== this ========

        // JFormDesigner evaluation mark
        setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                        "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                        javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                        java.awt.Color.red), getBorder()));
        addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                if ("border".equals(e.getPropertyName())) throw new RuntimeException();
            }
        });


        //======== attPanel ========
        {
            attPanel.setBorder(new TitledBorder("Attendance"));
            attPanel.setBackground(new Color(255, 204, 204));

            //---- lAttDate ----
            lAttDate.setText("Date: ");

            //---- lAttSubject ----
            lAttSubject.setText("Subject: ");

            //---- label10 ----
            label10.setText("[31-12-1999 15:30]");

            GroupLayout attPanelLayout = new GroupLayout(attPanel);
            attPanel.setLayout(attPanelLayout);
            attPanelLayout.setHorizontalGroup(
                    attPanelLayout.createParallelGroup()
                            .addGroup(attPanelLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(attPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(lAttDate)
                                            .addComponent(lAttSubject))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(attPanelLayout.createParallelGroup()
                                            .addGroup(attPanelLayout.createSequentialGroup()
                                                    .addComponent(txtAttDate, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(label10))
                                            .addComponent(txtAttSubject, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE))
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            attPanelLayout.setVerticalGroup(
                    attPanelLayout.createParallelGroup()
                            .addGroup(attPanelLayout.createSequentialGroup()
                                    .addGroup(attPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtAttDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lAttDate)
                                            .addComponent(label10))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(attPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtAttSubject, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lAttSubject))
                                    .addGap(0, 0, Short.MAX_VALUE))
            );
        }

        //======== navPanel ========
        {
            navPanel.setBackground(new Color(204, 204, 255));
            navPanel.setBorder(new TitledBorder("Navigate"));

            //---- bPrev ----
            bPrev.setText("<");
            bPrev.setFont(bPrev.getFont().deriveFont(bPrev.getFont().getStyle() | Font.BOLD));
            bPrev.addActionListener(e -> bPrevActionPerformed());

            //---- bNext ----
            bNext.setText(">");
            bNext.setFont(bNext.getFont().deriveFont(bNext.getFont().getStyle() | Font.BOLD));
            bNext.addActionListener(e -> bNextActionPerformed());

            //---- bUpdate ----
            bUpdate.setText("Update");
            bUpdate.setFont(bUpdate.getFont().deriveFont(bUpdate.getFont().getStyle() | Font.BOLD));
            bUpdate.addActionListener(e -> bUpdateActionPerformed());

            //---- bDelete ----
            bDelete.setText("Delete");
            bDelete.setFont(bDelete.getFont().deriveFont(bDelete.getFont().getStyle() | Font.BOLD));
            bDelete.addActionListener(e -> bDeleteActionPerformed());

            //---- bNew ----
            bNew.setText("New");
            bNew.addActionListener(e -> bNewActionPerformed());

            GroupLayout navPanelLayout = new GroupLayout(navPanel);
            navPanel.setLayout(navPanelLayout);
            navPanelLayout.setHorizontalGroup(
                    navPanelLayout.createParallelGroup()
                            .addGroup(navPanelLayout.createSequentialGroup()
                                    .addGap(6, 6, 6)
                                    .addComponent(bPrev)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(bNext)
                                    .addGap(18, 18, 18)
                                    .addComponent(bNew, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(bUpdate, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(bDelete, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            navPanelLayout.setVerticalGroup(
                    navPanelLayout.createParallelGroup()
                            .addGroup(navPanelLayout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addGroup(navPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(bPrev)
                                            .addComponent(bUpdate)
                                            .addComponent(bDelete)
                                            .addComponent(bNext)
                                            .addComponent(bNew))
                                    .addContainerGap(16, Short.MAX_VALUE))
            );
        }

        //======== PartiList ========
        {
            PartiList.setBorder(new TitledBorder("Participant"));

            //======== scrollPane1 ========
            {
                scrollPane1.addPropertyChangeListener(e -> scrollPane1PropertyChange());

                //---- tabParti ----
                tabParti.setAutoCreateRowSorter(true);
                tabParti.setFillsViewportHeight(true);
                tabParti.setModel(new DefaultTableModel(
                        new Object[][]{
                        },
                        new String[]{
                                "First Name", "Last Name", "Phone No", "Birth Date", "Present?"
                        }
                ) {
                    Class<?>[] columnTypes = new Class<?>[]{
                            String.class, String.class, Integer.class, String.class, Boolean.class
                    };

                    @Override
                    public Class<?> getColumnClass(int columnIndex) {
                        return columnTypes[columnIndex];
                    }
                });
                {
                    TableColumnModel cm = tabParti.getColumnModel();
                    cm.getColumn(4).setMinWidth(55);
                    cm.getColumn(4).setMaxWidth(55);
                    cm.getColumn(4).setPreferredWidth(55);
                }
                tabParti.setCellSelectionEnabled(true);
                tabParti.addPropertyChangeListener(e -> tabPartiPropertyChange());
                scrollPane1.setViewportView(tabParti);
            }

            //---- bNewParti ----
            bNewParti.setText("Add");
            bNewParti.setFont(bNewParti.getFont().deriveFont(bNewParti.getFont().getStyle() | Font.BOLD));
            bNewParti.addActionListener(e -> bNewPartiActionPerformed(e));

            //======== PartPanel ========
            {
                PartPanel.setBorder(new TitledBorder("Edit"));
                PartPanel.setBackground(new Color(204, 255, 204));

                //---- bUpdateParti ----
                bUpdateParti.setText("Update Table");
                bUpdateParti.setFont(bUpdateParti.getFont().deriveFont(bUpdateParti.getFont().getStyle() & ~Font.BOLD));
                bUpdateParti.addActionListener(e -> bUpdatePartiActionPerformed());

                //---- bDeleteParti ----
                bDeleteParti.setText("Delete");
                bDeleteParti.setFont(bDeleteParti.getFont().deriveFont(bDeleteParti.getFont().getStyle() & ~Font.BOLD));
                bDeleteParti.addActionListener(e -> bDeletePartiActionPerformed());

                //---- bShowAbsent ----
                bShowAbsent.setText("Show absent");
                bShowAbsent.addActionListener(e -> button1ActionPerformed());

                GroupLayout PartPanelLayout = new GroupLayout(PartPanel);
                PartPanel.setLayout(PartPanelLayout);
                PartPanelLayout.setHorizontalGroup(
                        PartPanelLayout.createParallelGroup()
                                .addGroup(PartPanelLayout.createSequentialGroup()
                                        .addContainerGap(13, Short.MAX_VALUE)
                                        .addGroup(PartPanelLayout.createParallelGroup()
                                                .addComponent(bUpdateParti, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(bDeleteParti, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(bShowAbsent, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                                        .addContainerGap())
                );
                PartPanelLayout.setVerticalGroup(
                        PartPanelLayout.createParallelGroup()
                                .addGroup(PartPanelLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(bUpdateParti)
                                        .addGap(9, 9, 9)
                                        .addComponent(bDeleteParti)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                                        .addComponent(bShowAbsent)
                                        .addContainerGap())
                );
            }

            //======== SavePanel ========
            {
                SavePanel.setBorder(new TitledBorder(""));

                //---- bExit ----
                bExit.setText("Exit");
                bExit.addActionListener(e -> bExitActionPerformed());

                //---- bSave ----
                bSave.setText("Save");
                bSave.addActionListener(e -> bSaveActionPerformed());

                GroupLayout SavePanelLayout = new GroupLayout(SavePanel);
                SavePanel.setLayout(SavePanelLayout);
                SavePanelLayout.setHorizontalGroup(
                        SavePanelLayout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, SavePanelLayout.createSequentialGroup()
                                        .addContainerGap(27, Short.MAX_VALUE)
                                        .addGroup(SavePanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                .addComponent(bExit, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(bSave, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                                        .addGap(25, 25, 25))
                );
                SavePanelLayout.setVerticalGroup(
                        SavePanelLayout.createParallelGroup()
                                .addGroup(SavePanelLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(bSave)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bExit)
                                        .addContainerGap(19, Short.MAX_VALUE))
                );
            }

            //---- label6 ----
            label6.setText("Phone No.");

            //---- label7 ----
            label7.setText("First Name");

            //---- label8 ----
            label8.setText("Last Name");

            //---- label9 ----
            label9.setText("Birth Date");

            //---- label11 ----
            label11.setText("[31-12-1999]");

            GroupLayout PartiListLayout = new GroupLayout(PartiList);
            PartiList.setLayout(PartiListLayout);
            PartiListLayout.setHorizontalGroup(
                    PartiListLayout.createParallelGroup()
                            .addGroup(PartiListLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(PartiListLayout.createParallelGroup()
                                            .addGroup(PartiListLayout.createSequentialGroup()
                                                    .addGroup(PartiListLayout.createParallelGroup()
                                                            .addComponent(txtFirstName, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(label7))
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                    .addGroup(PartiListLayout.createParallelGroup()
                                                            .addComponent(label8)
                                                            .addComponent(txtLastName, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE))
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                    .addGroup(PartiListLayout.createParallelGroup()
                                                            .addComponent(txtPhoneNo)
                                                            .addGroup(PartiListLayout.createSequentialGroup()
                                                                    .addComponent(label6)
                                                                    .addGap(0, 54, Short.MAX_VALUE)))
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                    .addGroup(PartiListLayout.createParallelGroup()
                                                            .addGroup(PartiListLayout.createSequentialGroup()
                                                                    .addComponent(txtBirthDate, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
                                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                    .addComponent(bNewParti, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                                                            .addComponent(label9)
                                                            .addComponent(label11)))
                                            .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(PartiListLayout.createParallelGroup()
                                            .addComponent(PartPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(PartiListLayout.createSequentialGroup()
                                                    .addComponent(SavePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                    .addGap(0, 1, Short.MAX_VALUE)))
                                    .addGap(2, 2, 2))
            );
            PartiListLayout.setVerticalGroup(
                    PartiListLayout.createParallelGroup()
                            .addGroup(PartiListLayout.createSequentialGroup()
                                    .addGroup(PartiListLayout.createParallelGroup()
                                            .addGroup(PartiListLayout.createParallelGroup()
                                                    .addGroup(PartiListLayout.createParallelGroup()
                                                            .addGroup(GroupLayout.Alignment.TRAILING, PartiListLayout.createSequentialGroup()
                                                                    .addContainerGap()
                                                                    .addComponent(label7))
                                                            .addGroup(PartiListLayout.createSequentialGroup()
                                                                    .addGap(23, 23, 23)
                                                                    .addComponent(label6)))
                                                    .addComponent(label8, GroupLayout.Alignment.TRAILING))
                                            .addGroup(PartiListLayout.createSequentialGroup()
                                                    .addComponent(label9)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(label11)))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(PartiListLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(bNewParti)
                                            .addGroup(PartiListLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                    .addComponent(txtFirstName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtBirthDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtLastName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtPhoneNo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                    .addGap(13, 13, 13)
                                    .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
                            .addGroup(PartiListLayout.createSequentialGroup()
                                    .addGap(16, 16, 16)
                                    .addComponent(PartPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                                    .addComponent(SavePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            );
        }

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(PartiList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(attPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(navPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(42, 42, 42))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(attPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(navPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(17, 17, 17)
                                .addComponent(PartiList, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

}

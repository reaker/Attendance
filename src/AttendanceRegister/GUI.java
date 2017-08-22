package AttendanceRegister;

import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.mail.Part;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

class GUI extends JPanel {
    private AttMngr manager = AttMngr.getInstance();
    private Attendance actualAttendance;
    private DefaultTableModel model;

    public final SimpleDateFormat dateAttFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    public final SimpleDateFormat dateBirthFormat = new SimpleDateFormat("dd-MM-yyyy");

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Sebastian Wijas
    private JPanel attPanel;
    private JLabel lAttDate;
    private JLabel lAttSubject;
    private JTextField txtAttDate;
    private JTextField txtAttSubject;
    private JPanel navPanel;
    private JButton bPrev;
    private JButton bNext;
    private JButton bNew;
    private JButton bUpdate;
    private JButton bDelete;
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
    private JButton bDeleteParti2;
    private JPanel SavePanel;
    private JButton bExit;
    private JButton bSave;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    GUI() {
        initComponents();
        setMinimumSize(new Dimension(920, 700));

        String firstSubject=null;
        Date firstDate=null;
        model = (DefaultTableModel)tabParti.getModel();
        //only to show first data in GUI
        for (Map.Entry<Integer,Attendance> entry  :manager.getAttMap().entrySet()) {
            Attendance firstAttendance = entry.getValue();
            Participant participant;
            for (Map.Entry<Participant, Boolean> entryP:  firstAttendance.getMap().entrySet()) {
                participant= entryP.getKey();
                model.addRow(new Object[]{participant.getFirstName(), participant.getLastName(), participant.getTelephoneNo(),participant.getStringDateOfBirth(),participant.getPresence()});
            }
            firstSubject = firstAttendance.getSubject();
            firstDate = firstAttendance.getDate();
            break;
        }
        txtAttDate.setText(dateAttFormat.format(firstDate));
        txtAttSubject.setText(firstSubject);

        setVisible(true);
    }

    //NAVIGATE ATTENDANCE
    private void bPrevActionPerformed() {
        int mapSize = manager.getAttMap().size();
        if (mapSize<= 0) { // jesli zszedles ponizej size to pokaz pustą stronę
            txtAttSubject.setText("");
            txtAttDate.setText("");
            actualAttendance=null;
            AttMngr.setMinAttendanceID(0);
            AttMngr.setMaxAttendanceID(0);

        } else if (mapSize==1){
            JOptionPane.showMessageDialog(null, "End of file.");
        }
        else {
            getActualAttendance();

            int i=0,previousIDKeyMap;
            int actualID= manager.getActualKeyMap();
            int result=-1;
            for (Map.Entry<Integer, Attendance> entry : manager.getAttMap().entrySet()) {
                i++;
                previousIDKeyMap=entry.getKey();
                if (previousIDKeyMap<actualID){  //find previous
                    if (previousIDKeyMap>result){result = previousIDKeyMap;}
                }
                if (previousIDKeyMap==actualID && result==-1){result=previousIDKeyMap;}

            }
            manager.setActualKeyMap(result);

            txtAttSubject.setText(manager.getAttMap().get(manager.getActualKeyMap()).getSubject());
            txtAttDate.setText(manager.getAttMap().get(manager.getActualKeyMap()).getFormattedDate());
        }
        rewriteTable();
    }

    private void bNextActionPerformed() {
        int mapSize = manager.getAttMap().size();
        if (mapSize<= 0) { // jesli zszedles ponizej size to pokaz pustą stronę
            txtAttSubject.setText("");
            txtAttDate.setText("");
            actualAttendance=null;
            AttMngr.setMinAttendanceID(0);
            AttMngr.setMaxAttendanceID(0);

        } else if (mapSize==1){
            JOptionPane.showMessageDialog(null, "End of file.");
        }
        else {
            getActualAttendance();

            int i=0, nextIDKeyMap;
            int actualID= manager.getActualKeyMap();
            int result=999999999;
            for (Map.Entry<Integer, Attendance> entry : manager.getAttMap().entrySet()) {
                nextIDKeyMap=entry.getKey();
                i++;
                if (nextIDKeyMap>actualID){
                    if (nextIDKeyMap<result){result = nextIDKeyMap;}
                }
                if (nextIDKeyMap==actualID && result==999999999 && mapSize==i){result=nextIDKeyMap;}

            }
            manager.setActualKeyMap(result);
            txtAttSubject.setText(manager.getAttMap().get(manager.getActualKeyMap()).getSubject());
            txtAttDate.setText(manager.getAttMap().get(manager.getActualKeyMap()).getFormattedDate());
        }
        rewriteTable();
    }


    //CRUD ATTENDANCE
    private void bNewActionPerformed(ActionEvent e) {
        String date = JOptionPane.showInputDialog("Enter date. /format: 31-12-2001 14:10");
        String subject = JOptionPane.showInputDialog("Enter subject.");
        bUpdate.setEnabled(true);
        bDelete.setEnabled(true);

        manager.addAttendance(date, subject);
        txtAttDate.setText(date);
        txtAttSubject.setText(subject);
    }

    private void getActualAttendance() {
        actualAttendance = manager.getAttMap().get(manager.getActualKeyMap());
    }

    private void bUpdateActionPerformed() {
        getActualAttendance();
        System.out.println(actualAttendance);
        actualAttendance.setSubject(txtAttSubject.getText());
        actualAttendance.setDate(txtAttDate.getText());
        System.out.println("UPDATED");
    }

    private void bDeleteActionPerformed() {
        getActualAttendance();

        int temp = manager.getActualKeyMap();
        manager.getAttMap().remove(temp);

        if (manager.getAttMap().isEmpty()){
            txtAttDate.setText("");
            txtAttSubject.setText("");
            bDelete.setEnabled(false);
            bUpdate.setEnabled(false);
        }
        else{
            manager.setFirstAvailableKeyMapForNotEmptyMap();
            getActualAttendance();
            txtAttSubject.setText(manager.getAttMap().get(manager.getActualKeyMap()).getSubject());
            txtAttDate.setText(manager.getAttMap().get(manager.getActualKeyMap()).getFormattedDate());
        }
        System.out.println("DELETED.");
    }

    private void scrollPane1PropertyChange() {
        // TODO add your code here
    }

    private void tabPartiPropertyChange() {
        // TODO add your code here


    }


    //SAVE & EXIT
    private void bSaveActionPerformed() {
        manager.saveToFile();

        // TODO add your code here
    }

    private void bExitActionPerformed() {
        Frame[] frames=Frame.getFrames();
        for (Frame frame: frames) {
            frame.dispose();
        }
    }



    //Participant Actions
    //UPDATE
    private void bUpdatePartiActionPerformed() {
        //updates selected row

        getActualAttendance();
        actualAttendance.getMap();

//        for (Map.Entry<Participant, Boolean> entry : actualAttendance.getMap().entrySet()) {
//            if (!entry.getKey().equals(new Participant()))
//
//        }
        int row=tabParti.getSelectedRow();
        String firstName = model.getValueAt(row, 0).toString();
        String lastName= model.getValueAt(row, 1).toString();
        String phoneNumber= model.getValueAt(row, 2).toString();
        String birthDate= model.getValueAt(row, 3).toString();






                //rewrite table()


        model.setValueAt(model.getValueAt(tabParti.getSelectedRow(), tabParti.getSelectedColumn()), tabParti.getSelectedRow(), tabParti.getSelectedColumn()); // 4 razy

    }

    //DELETE
    private void bDeletePartiActionPerformed() {
        getActualAttendance();
        String firstName, lastName, dateOfBirth;
        int phoneNumber;
        firstName = (String)tabParti.getValueAt(tabParti.getSelectedRow(), 0);
        lastName = (String)tabParti.getValueAt(tabParti.getSelectedRow(), 1);
        phoneNumber = (Integer)tabParti.getValueAt(tabParti.getSelectedRow(), 2);
        dateOfBirth = (String)tabParti.getValueAt(tabParti.getSelectedRow(), 3);
        actualAttendance.getMap().remove(new Participant(firstName,lastName,phoneNumber,dateOfBirth));
        model.removeRow(tabParti.getSelectedRow());
    }

    void  rewriteTable(){
        model.setRowCount(0);
        getActualAttendance();
        int i=0;
        for (Map.Entry<Participant, Boolean> entry : actualAttendance.getMap().entrySet()) {
            model.insertRow(i,new Object[]{entry.getKey().getFirstName(),entry.getKey().getLastName(), entry.getKey().getTelephoneNo(), entry.getKey().getStringDateOfBirth()});
            i++;
        }
    }


    //ADD NEW
    private void bNewPartiActionPerformed(ActionEvent e) {

        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String phoneNumber = txtPhoneNo.getText();
        String dateOfBirth = txtBirthDate.getText();

        getActualAttendance();
        Participant newParticipant= new Participant(firstName, lastName, Integer.parseInt(phoneNumber), dateOfBirth);
        int mapSize = actualAttendance.getMap().size(); //10
        actualAttendance.addParticipant(newParticipant); //11
        int newMapSize = actualAttendance.getMap().size();
        if (!(newMapSize==mapSize)) { // dont add duplicate content
            model.addRow(new Object[]{newParticipant.getFirstName(), newParticipant.getLastName(), newParticipant.getTelephoneNo(), newParticipant.getStringDateOfBirth(), newParticipant.getPresence()});
        }
        txtFirstName.setText("");
        txtBirthDate.setText("");
        txtLastName.setText("");
        txtPhoneNo.setText("");

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Sebastian Wijas
        attPanel = new JPanel();
        lAttDate = new JLabel();
        lAttSubject = new JLabel();
        txtAttDate = new JTextField();
        txtAttSubject = new JTextField();
        navPanel = new JPanel();
        bPrev = new JButton();
        bNext = new JButton();
        bNew = new JButton();
        bUpdate = new JButton();
        bDelete = new JButton();
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
        bDeleteParti2 = new JButton();
        SavePanel = new JPanel();
        bExit = new JButton();
        bSave = new JButton();
        label4 = new JLabel();
        label5 = new JLabel();
        label6 = new JLabel();
        label7 = new JLabel();

        //======== this ========

        // JFormDesigner evaluation mark
        setBorder(new javax.swing.border.CompoundBorder(
            new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});


        //======== attPanel ========
        {
            attPanel.setBorder(new TitledBorder("Attendance"));
            attPanel.setBackground(new Color(255, 204, 204));

            //---- lAttDate ----
            lAttDate.setText("Date: ");

            //---- lAttSubject ----
            lAttSubject.setText("Subject: ");

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
                            .addComponent(txtAttDate, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAttSubject, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            attPanelLayout.setVerticalGroup(
                attPanelLayout.createParallelGroup()
                    .addGroup(attPanelLayout.createSequentialGroup()
                        .addGroup(attPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(txtAttDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(lAttDate))
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
            bPrev.setForeground(Color.blue);
            bPrev.addActionListener(e -> bPrevActionPerformed());

            //---- bNext ----
            bNext.setText(">");
            bNext.setFont(bNext.getFont().deriveFont(bNext.getFont().getStyle() | Font.BOLD));
            bNext.setForeground(Color.blue);
            bNext.addActionListener(e -> bNextActionPerformed());

            //---- bNew ----
            bNew.setText("New");
            bNew.setFont(bNew.getFont().deriveFont(bNew.getFont().getStyle() | Font.BOLD));
            bNew.setForeground(Color.blue);
            bNew.addActionListener(e -> {
			bNewActionPerformed(e);
			bNewActionPerformed(e);
			bNewActionPerformed(e);
			bNewActionPerformed(e);
			bNewActionPerformed(e);
		});

            //---- bUpdate ----
            bUpdate.setText("Update");
            bUpdate.setFont(bUpdate.getFont().deriveFont(bUpdate.getFont().getStyle() | Font.BOLD));
            bUpdate.setForeground(Color.blue);
            bUpdate.addActionListener(e -> bUpdateActionPerformed());

            //---- bDelete ----
            bDelete.setText("Delete");
            bDelete.setFont(bDelete.getFont().deriveFont(bDelete.getFont().getStyle() | Font.BOLD));
            bDelete.setForeground(Color.blue);
            bDelete.addActionListener(e -> bDeleteActionPerformed());

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
                            .addComponent(bNew)
                            .addComponent(bUpdate)
                            .addComponent(bDelete)
                            .addComponent(bNext))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    new Object[][] {
                    },
                    new String[] {
                        "First Name", "Last Name", "Phone No", "Birth Date", "Present?"
                    }
                ) {
                    Class<?>[] columnTypes = new Class<?>[] {
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
            bNewParti.setForeground(Color.blue);
            bNewParti.addActionListener(e -> bNewPartiActionPerformed(e));

            //======== PartPanel ========
            {
                PartPanel.setBorder(new TitledBorder("Navigate"));
                PartPanel.setBackground(new Color(204, 255, 204));

                //---- bUpdateParti ----
                bUpdateParti.setText("Update");
                bUpdateParti.setFont(bUpdateParti.getFont().deriveFont(bUpdateParti.getFont().getStyle() | Font.BOLD));
                bUpdateParti.addActionListener(e -> bUpdatePartiActionPerformed());

                //---- bDeleteParti ----
                bDeleteParti.setText("Delete");
                bDeleteParti.setFont(bDeleteParti.getFont().deriveFont(bDeleteParti.getFont().getStyle() | Font.BOLD));
                bDeleteParti.setForeground(Color.blue);
                bDeleteParti.addActionListener(e -> bDeletePartiActionPerformed());

                //---- bDeleteParti2 ----
                bDeleteParti2.setText("Show Table");
                bDeleteParti2.setFont(bDeleteParti2.getFont().deriveFont(bDeleteParti2.getFont().getStyle() | Font.BOLD));

                GroupLayout PartPanelLayout = new GroupLayout(PartPanel);
                PartPanel.setLayout(PartPanelLayout);
                PartPanelLayout.setHorizontalGroup(
                    PartPanelLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, PartPanelLayout.createSequentialGroup()
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(PartPanelLayout.createParallelGroup()
                                .addComponent(bUpdateParti, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                .addComponent(bDeleteParti, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                .addComponent(bDeleteParti2, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                            .addContainerGap())
                );
                PartPanelLayout.setVerticalGroup(
                    PartPanelLayout.createParallelGroup()
                        .addGroup(PartPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(bUpdateParti)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(bDeleteParti)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(bDeleteParti2)
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
            }

            //======== SavePanel ========
            {
                SavePanel.setBorder(new TitledBorder(""));

                //---- bExit ----
                bExit.setText("Exit");
                bExit.setForeground(Color.blue);
                bExit.addActionListener(e -> bExitActionPerformed());

                //---- bSave ----
                bSave.setText("Save");
                bSave.setForeground(Color.blue);
                bSave.addActionListener(e -> bSaveActionPerformed());

                GroupLayout SavePanelLayout = new GroupLayout(SavePanel);
                SavePanel.setLayout(SavePanelLayout);
                SavePanelLayout.setHorizontalGroup(
                    SavePanelLayout.createParallelGroup()
                        .addGroup(SavePanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(SavePanelLayout.createParallelGroup()
                                .addComponent(bSave, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                .addComponent(bExit, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                SavePanelLayout.setVerticalGroup(
                    SavePanelLayout.createParallelGroup()
                        .addGroup(SavePanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(bSave)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(bExit)
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
            }

            //---- label4 ----
            label4.setText("First Name");

            //---- label5 ----
            label5.setText("Last Name");

            //---- label6 ----
            label6.setText("Phone No.");

            //---- label7 ----
            label7.setText("Birth Date");

            GroupLayout PartiListLayout = new GroupLayout(PartiList);
            PartiList.setLayout(PartiListLayout);
            PartiListLayout.setHorizontalGroup(
                PartiListLayout.createParallelGroup()
                    .addGroup(PartiListLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(PartiListLayout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, PartiListLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 530, GroupLayout.PREFERRED_SIZE))
                            .addGroup(PartiListLayout.createSequentialGroup()
                                .addGroup(PartiListLayout.createParallelGroup()
                                    .addComponent(txtFirstName, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label4, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PartiListLayout.createParallelGroup()
                                    .addComponent(txtLastName, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label5))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PartiListLayout.createParallelGroup()
                                    .addComponent(txtPhoneNo)
                                    .addGroup(PartiListLayout.createSequentialGroup()
                                        .addComponent(label6)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PartiListLayout.createParallelGroup()
                                    .addGroup(PartiListLayout.createSequentialGroup()
                                        .addComponent(txtBirthDate, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bNewParti, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                                    .addComponent(label7))))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PartiListLayout.createParallelGroup()
                            .addComponent(PartPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(SavePanel, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)))
            );
            PartiListLayout.setVerticalGroup(
                PartiListLayout.createParallelGroup()
                    .addGroup(PartiListLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(PartiListLayout.createParallelGroup()
                            .addGroup(PartiListLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label4)
                                .addComponent(label5))
                            .addGroup(GroupLayout.Alignment.TRAILING, PartiListLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label6)
                                .addComponent(label7)))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PartiListLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(bNewParti)
                            .addGroup(PartiListLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(txtFirstName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtBirthDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtLastName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtPhoneNo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(PartiListLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(PartPanel, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
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
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(attPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(navPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(PartiList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(19, 19, 19)
                    .addGroup(layout.createParallelGroup()
                        .addComponent(attPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(navPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(PartiList, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

}

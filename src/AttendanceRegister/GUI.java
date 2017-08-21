package AttendanceRegister;

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
//    private final String[] columnNames = {"First Name",
//            "Last Name",
//            "Phone No",
//            "Birth Date"};
    String[] columnNames = {"First Name",
            "Last Name",
            "Sport",
            "# of Years",
            "Vegetarian"};

    Object[][] data = {
            {"Kathy", "Smith",
                    "Snowboarding", new Integer(5), new Boolean(false)},
            {"John", "Doe",
                    "Rowing", new Integer(3), new Boolean(true)},
            {"Sue", "Black",
                    "Knitting", new Integer(2), new Boolean(false)},
            {"Jane", "White",
                    "Speed reading", new Integer(20), new Boolean(true)},
            {"Joe", "Brown",
                    "Pool", new Integer(10), new Boolean(false)}
    };

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
    private JPanel PartPanel;
    private JButton bNewParti;
    private JButton bUpdateParti;
    private JButton bDeleteParti;
    private JPanel SavePanel;
    private JButton bExit;
    private JButton bSave;
    private JTextArea textArea1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    GUI() {
        initComponents();
        setMinimumSize(new Dimension(920, 700));

        initTable();
        String firstSubject=null;
        Date firstDate=null;
        //only to shouw first data in GUI
        for (Map.Entry<Integer,Attendance> entry  :manager.getAttMap().entrySet()) {
            firstSubject = entry.getValue().getSubject();
            firstDate = entry.getValue().getDate();
            break;
        }

        txtAttDate.setText(dateAttFormat.format(firstDate));
        txtAttSubject.setText(firstSubject);
        setVisible(true);
    }

    private void initTable(){
        tabParti = new JTable(data, columnNames);
        scrollPane1= new JScrollPane(tabParti);
        this.add(scrollPane1);
        tabParti.setFillsViewportHeight(true);
    }


    private void bNewActionPerformed(ActionEvent e) {
        String date = JOptionPane.showInputDialog("Enter date. /format: 31-12-2001 14:10");
        String subject = JOptionPane.showInputDialog("Enter subject.");
        bUpdate.setEnabled(true);
        bDelete.setEnabled(true);

        manager.addAttendance(date, subject);
        txtAttDate.setText(date);
        txtAttSubject.setText(subject);
    }

    private void bNewPartiActionPerformed(ActionEvent e) {


        //wspiasane dane w tabeli nalezy dodac do Att - > participant
        // i wyswietlic uaktualnione dane
        String firstName = JOptionPane.showInputDialog("Enter first name:");
        String lastName = JOptionPane.showInputDialog("Enter last name:");
        String phoneNumber = JOptionPane.showInputDialog("Enter phone number:");
        String dateOfBirth = JOptionPane.showInputDialog("Enter date of birth: \nformat: [28-02-2000]");

        getActualAttendance();
        actualAttendance.addParticipant(firstName, lastName, phoneNumber, dateOfBirth);


    }

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
    }

    private void getActualAttendance() {
        actualAttendance = manager.getAttMap().get(manager.getActualKeyMap());
    }

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
        PartPanel = new JPanel();
        bNewParti = new JButton();
        bUpdateParti = new JButton();
        bDeleteParti = new JButton();
        SavePanel = new JPanel();
        bExit = new JButton();
        bSave = new JButton();
        textArea1 = new JTextArea();

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
                    .addGroup(GroupLayout.Alignment.TRAILING, attPanelLayout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addGroup(attPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(lAttDate)
                            .addComponent(lAttSubject))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(attPanelLayout.createParallelGroup()
                            .addComponent(txtAttDate, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAttSubject, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE))
                        .addGap(61, 61, 61))
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
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addContainerGap(59, Short.MAX_VALUE))
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
            PartiList.setBorder(new TitledBorder("Participant List"));

            //======== scrollPane1 ========
            {
                scrollPane1.addPropertyChangeListener(e -> scrollPane1PropertyChange());

                //---- tabParti ----
                tabParti.setAutoCreateRowSorter(true);
                tabParti.setCellSelectionEnabled(true);
                tabParti.setFillsViewportHeight(true);
                tabParti.setModel(new DefaultTableModel(
                    new Object[][] {
                        {null, null, null, null, null},
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
                tabParti.addPropertyChangeListener(e -> tabPartiPropertyChange());
                scrollPane1.setViewportView(tabParti);
            }

            GroupLayout PartiListLayout = new GroupLayout(PartiList);
            PartiList.setLayout(PartiListLayout);
            PartiListLayout.setHorizontalGroup(
                PartiListLayout.createParallelGroup()
                    .addGroup(PartiListLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE)
                        .addContainerGap())
            );
            PartiListLayout.setVerticalGroup(
                PartiListLayout.createParallelGroup()
                    .addGroup(PartiListLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE))
            );
        }

        //======== PartPanel ========
        {
            PartPanel.setBorder(new TitledBorder("Navigate"));
            PartPanel.setBackground(new Color(204, 255, 204));

            //---- bNewParti ----
            bNewParti.setText("Add");
            bNewParti.setFont(bNewParti.getFont().deriveFont(bNewParti.getFont().getStyle() | Font.BOLD));
            bNewParti.addActionListener(e -> bNewPartiActionPerformed(e));

            //---- bUpdateParti ----
            bUpdateParti.setText("Update");
            bUpdateParti.setFont(bUpdateParti.getFont().deriveFont(bUpdateParti.getFont().getStyle() | Font.BOLD));

            //---- bDeleteParti ----
            bDeleteParti.setText("Delete");
            bDeleteParti.setFont(bDeleteParti.getFont().deriveFont(bDeleteParti.getFont().getStyle() | Font.BOLD));

            GroupLayout PartPanelLayout = new GroupLayout(PartPanel);
            PartPanel.setLayout(PartPanelLayout);
            PartPanelLayout.setHorizontalGroup(
                PartPanelLayout.createParallelGroup()
                    .addGroup(PartPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(PartPanelLayout.createParallelGroup()
                            .addComponent(bNewParti, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                            .addComponent(bUpdateParti, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                            .addComponent(bDeleteParti, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            PartPanelLayout.setVerticalGroup(
                PartPanelLayout.createParallelGroup()
                    .addGroup(PartPanelLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(bNewParti)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bUpdateParti)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bDeleteParti)
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

        //---- textArea1 ----
        textArea1.setText("Blue - completed\nTODO - black ones \nand table");
        textArea1.setEnabled(false);
        textArea1.setEditable(false);

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(PartiList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(PartPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(SavePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(textArea1)))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(attPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(navPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addGap(184, 184, 184))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(19, 19, 19)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(attPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(navPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(PartPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(textArea1, GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(SavePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addComponent(PartiList, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

}

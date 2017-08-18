/*
 * Created by JFormDesigner on Tue Aug 15 16:51:23 CEST 2017
 */

package AttendanceRegister;

import com.sun.istack.internal.NotNull;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

class GUI extends JPanel {
    final JFileChooser fc = new JFileChooser();
    private AttMngr manager = AttMngr.getInstance();
    private Attendance actualAttendance;

    public final SimpleDateFormat dateAttFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    public final SimpleDateFormat dateBirthFormat = new SimpleDateFormat("dd-MM-yyyy");

    private static boolean isChanged=false;

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
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    GUI() {
        initComponents();
        setMinimumSize(new Dimension(920, 700));

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

    private void bNewActionPerformed(ActionEvent e) {
        String date = JOptionPane.showInputDialog("Enter date. /format: 31-12-2001 14:10");
        String subject = JOptionPane.showInputDialog("Enter subject.");

        manager.addAttendance(date, subject);
        txtAttDate.setText(date);
        txtAttSubject.setText(subject);
    }


    private void bNewPartiActionPerformed(ActionEvent e) {
        String firstName = JOptionPane.showInputDialog("Enter first name:");
        String lastName = JOptionPane.showInputDialog("Enter last name:");
        String phoneNumber = JOptionPane.showInputDialog("Enter phone number:");
        String dateOfBirth = JOptionPane.showInputDialog("Enter date of birth: \nformat: [28-02-2000]");


        getActualAttendance();
        actualAttendance.addParticipant(firstName, lastName, phoneNumber, dateOfBirth);


    }

    private void bPrevActionPerformed() {
        if ((manager.getActualKeyMap() - 1) < 0) {

        } else {
            int i = 1;
            while (i > 0) {
                try {
                    manager.setActualKeyMap(manager.getActualKeyMap() - i);
                    break;
                } catch (Exception e) {
                    i++;
                }
            }
//            String temp= manager.getAttList().get(manager.getActualKeyMap()).toString();//getSubject();

            System.out.println(manager.getAttMap().get(0));
            System.out.println(manager.getAttMap().get(1));
            System.out.println(manager.getAttMap().get(2));


//            txtAttSubject.setText(temp);
            txtAttDate.setText(manager.getAttMap().get(manager.getActualKeyMap()).getDate().toString());
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
                        .addContainerGap(15, Short.MAX_VALUE))
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

            //---- bDelete ----
            bDelete.setText("Delete");
            bDelete.setFont(bDelete.getFont().deriveFont(bDelete.getFont().getStyle() | Font.BOLD));

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
                        .addContainerGap(30, Short.MAX_VALUE))
            );
        }

        //======== PartiList ========
        {
            PartiList.setBorder(new TitledBorder("Participant List"));

            //======== scrollPane1 ========
            {
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
                        .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 263, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
        }

        //======== PartPanel ========
        {
            PartPanel.setBorder(new TitledBorder("Navigate"));
            PartPanel.setBackground(new Color(204, 255, 204));

            //---- bNewParti ----
            bNewParti.setText("New");
            bNewParti.setFont(bNewParti.getFont().deriveFont(bNewParti.getFont().getStyle() | Font.BOLD));
            bNewParti.setForeground(Color.blue);
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
                        .addContainerGap(17, Short.MAX_VALUE))
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

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(PartiList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup()
                                .addComponent(PartPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(SavePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
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
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                            .addComponent(SavePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addComponent(PartiList, GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE))
                    .addContainerGap())
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

}

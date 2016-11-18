/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buzzworks;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author Simon Junga (simonthechipmunk)
 */
public class mainWindow extends javax.swing.JFrame {
    
    private Serial serial;
    private BuzzWatch buzzwatch;
    private ArrayList<Integer> teamlist;
    private ArrayList<TeamPanel> teampanels;
    private TeamWindowPoints teamWindowPoints1;
    private ArrayList<Boolean> checkboxes;
    private MediaPlayer mediaPlayer;
    private int buzzedteamindex = -1;
    private boolean gametab_present = false;

    /**
     * Creates new form mainWindow
     */
    public mainWindow(Serial serial, ArrayList<Integer> teamlist, ArrayList checkboxes) {
        this.teampanels = new ArrayList();
        this.serial = serial;
        this.teamlist = teamlist;
        this.checkboxes = checkboxes;
        
        //set window title
        this.setTitle("Buzzworks - Controls");
        
        //init window components
        initComponents();
        jLabel_TeamBuzzed.setVisible(false);
        
        //create gametab if possible
        try{
            jTabbedPane4.addTab("Game", new GamePanelAll(this));
            gametab_present = true;
        }
        catch(Exception e){
            
        }
        
        
        //create teamtabs
        for (int i = 0; i < teamlist.size(); i++) {
            teampanels.add(new TeamPanel(teamlist.get(i), this.serial, this.jTabbedPane4));
            jTabbedPane4.addTab(teampanels.get(i).team.getName(), teampanels.get(i));
            teampanels.get(i).team.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        //action
                        updatePointList();
                    }
            });
        }
        
        //create external points window
        teamWindowPoints1 = new TeamWindowPoints(teampanels);
        teamWindowPoints1.setVisible(this.checkboxes.get(0));
        
        //initialize the pointslist
        updatePointList();
        
        //start serial buzz watcher
        buzzwatch = new BuzzWatch(this);
        new Thread(buzzwatch).start();
        
        //setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/home/simon/.NetBeansProjects/BuzzWorks/Icons/icon.png")));
        
        //reset buzzers
        serial.Send("all:mode.0\n");
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane4 = new javax.swing.JTabbedPane();
        teamPanelAll1 = new buzzworks.TeamPanelAll(serial, teampanels);
        teamPanelPoints1 = new buzzworks.TeamPanelPoints(teampanels);
        jPanel3 = new javax.swing.JPanel();
        jButton_ResetAll = new javax.swing.JButton();
        jLabel_TeamBuzzed = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1024, 768));

        jTabbedPane4.addTab("All Teams", teamPanelAll1);
        jTabbedPane4.addTab("Points", teamPanelPoints1);

        jButton_ResetAll.setText("Reset Buzzers");
        jButton_ResetAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ResetAllActionPerformed(evt);
            }
        });

        jLabel_TeamBuzzed.setFont(new java.awt.Font("Cantarell", 0, 80)); // NOI18N
        jLabel_TeamBuzzed.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_TeamBuzzedMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_TeamBuzzed, javax.swing.GroupLayout.PREFERRED_SIZE, 781, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_ResetAll, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel_TeamBuzzed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton_ResetAll, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1040, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 590, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_ResetAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ResetAllActionPerformed
        // TODO add your handling code here:
        serial.Send("all:buzz.reset\n");
        
        //switch back to game tab
        if(buzzedteamindex >= 0){
            switchGame();
        }
        
        buzzedteamindex = -1;
        
        //reset global tag
        jLabel_TeamBuzzed.setVisible(false);
        teamWindowPoints1.unselectTeam();
    }//GEN-LAST:event_jButton_ResetAllActionPerformed

    private void jLabel_TeamBuzzedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_TeamBuzzedMouseClicked
        // TODO add your handling code here:
        
        //toggle between Game and Buzzed Team
        if(gametab_present && ("→ " + jTabbedPane4.getTitleAt(jTabbedPane4.getSelectedIndex())).equals(jLabel_TeamBuzzed.getText())){
            switchGame();
        }
        else{
            switchToTeam();
        }
    }//GEN-LAST:event_jLabel_TeamBuzzedMouseClicked
    
    //switch to "Buzzed Team" tab
    public void switchToTeam(){
        if(buzzedteamindex >= 0){
            jTabbedPane4.setSelectedIndex(buzzedteamindex);
        }
    }
    
    //switch to "Game" tab (if present)
    public void switchGame(){
        if(gametab_present){
            for (int i = 0; i < jTabbedPane4.getTabCount(); i++) {
                    if (jTabbedPane4.getTitleAt(i).equals("Game")) {
                        jTabbedPane4.setSelectedIndex(i);
                        break;
                    }
            }
        }
    }
    
    //play sound
    public void playSound(URL url){
        try{
            // cl is the ClassLoader for the current class, ie. CurrentClass.class.getClassLoader();
            URL file = url;
            final Media media = new Media(file.toString());
            if(mediaPlayer != null) mediaPlayer.dispose();
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void stopSound(){
        if(mediaPlayer != null) mediaPlayer.dispose();
    }
    
    //update lists for displaying points
    private void updatePointList(){
        teampanels.sort(TeampanelPointsComparator);
        teamPanelPoints1.updateList();
        teamWindowPoints1.updateList();
    }
    
    private Comparator<TeamPanel> TeampanelPointsComparator
                          = new Comparator<TeamPanel>() {

            @Override
	    public int compare(TeamPanel t1, TeamPanel t2){
              
	      //ascending order
	      //return new Integer(t1.team.getPoints()).compareTo(t2.team.getPoints());
	      
	      //descending order
	      return new Integer(t2.team.getPoints()).compareTo(t1.team.getPoints());
	    }

	};
    
    //handle "buzzer pressed" event
    private void buzzhandler(String address) {

        //play sound
        playSound(mainWindow.class.getClassLoader().getResource("resources/sounds/positive_beeps1.wav"));
        //playSound(mainWindow.class.getClassLoader().getResource("resources/sounds_nonfree/correct_answer1_fast.wav"));
        
        //get "buzzed" team
        for (int e = 0; e < teampanels.size(); e++) {
            
            if (teampanels.get(e).team.getAddress() == Integer.parseInt(address)) {
                for (int i = 0; i < jTabbedPane4.getTabCount(); i++) {
                    if (jTabbedPane4.getTitleAt(i).equals(teampanels.get(e).team.getName())) {
                        
                        //select teamtab
                        if(!gametab_present)jTabbedPane4.setSelectedIndex(i);
                        buzzedteamindex = i;
                        
                        //set global tag
                        jLabel_TeamBuzzed.setText("→ " + teampanels.get(e).team.getName());
                        jLabel_TeamBuzzed.setForeground(teampanels.get(e).team.getColor());
                        jLabel_TeamBuzzed.setVisible(true);
                        
                        //select team on external points list
                        teamWindowPoints1.selectTeam(Integer.parseInt(address));
                        break;
                    }
                }
                break;
            }
        }
    }
    
    //thread for listening on serial for "buzzer pressed"
    private class BuzzWatch implements Runnable {
        
        mainWindow parent;
        
        public BuzzWatch(mainWindow parent) {
            this.parent = parent;
        }
        
        @Override
        public void run() {
            //background

            Scanner scanner = new Scanner(serial.in);
            try {
                
                while (true) {
                    //scan                    
                    String inStr = scanner.nextLine();
                    
                    if (inStr.indexOf(':') != -1) {
                        if (inStr.substring(inStr.indexOf(':') + 1).equals("is.buzzed")) {
                            parent.buzzhandler(inStr.substring(0, inStr.indexOf(':')));
                        }
                    }
                }
                
            } catch (Exception e) {
                
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_ResetAll;
    private javax.swing.JLabel jLabel_TeamBuzzed;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private buzzworks.TeamPanelAll teamPanelAll1;
    private buzzworks.TeamPanelPoints teamPanelPoints1;
    // End of variables declaration//GEN-END:variables
}

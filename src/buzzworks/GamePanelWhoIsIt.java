/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buzzworks;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Simon Junga (simonthechipmunk)
 */
public class GamePanelWhoIsIt extends javax.swing.JPanel {
    
    private final Font defaultLabelFont = new Font("Cantarell", 0, 36);
    private mainWindow mainwindow;
    private ArrayList<Image> images;
    DefaultListModel model;
    public GameWindowPanelWhoIsIt gamewindowpanel;
    private int playcount;
    
    private final String configfile = "./WhoIsIt.conf";

    /**
     * Creates new form GamePanelWhoIsIt. Files must be stored in current directory subfolder "WhoIsIt" 
     * and following the namescheme: NAME.png NAME.jpg (500x500)
     */
    public GamePanelWhoIsIt() {
        initComponents();
    }
    
    public GamePanelWhoIsIt(mainWindow mainwindow, String name) throws Exception{
        this.setName(name);
        this.mainwindow = mainwindow;
        this.images = new ArrayList();
        this.gamewindowpanel = new GameWindowPanelWhoIsIt();
        
        initComponents();
        
        //read Tracklist
        try{
            getImages();
        }
        catch (Exception e ){
            throw e;
        }
        
        //fill list and init
        model = new DefaultListModel();
        for(int i=0; i<images.size(); i++){
            if(images.get(i).isSelected()){
                model.addElement("●  " + images.get(i).getName());
            }
            else{
                model.addElement(images.get(i).getName());
            }
        }   
        jList_Images.setModel(model);
        jList_Images.setSelectedIndex(0);

        jList_Images.setPreferredSize(new Dimension(350, images.size()*40));
        
        //handler list
        jList_Images.addListSelectionListener(new ListSelectionListener() {
            
            @Override
            public void valueChanged(ListSelectionEvent e) {
                jButton_Show.setEnabled(true);
                jButton_Show.setText("Show Image");
                jLabel_Name.setText(images.get(jList_Images.getSelectedIndex()).getName());
                
                autoFontsize.calcFontsize(jLabel_Name, defaultLabelFont);
                
                jLabel_Image.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir") + "/WhoIsIt/" + 
                    images.get(jList_Images.getSelectedIndex()).getImagefile()));
                gamewindowpanel.setReset();
                
                jList_Images.ensureIndexIsVisible(jList_Images.getSelectedIndex());
            }
        });
        
        //register hook for saving config on exit
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

            public void run() {
                storeConfig();
            }
        }));
        
        //init game
        selectRandomNext();
        jButton_Show.setText("Show Image");
        gamewindowpanel.setReset();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel_Header = new javax.swing.JLabel();
        jLabel_Name = new javax.swing.JLabel();
        jButton_Show = new javax.swing.JButton();
        jButton_Next = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList_Images = new javax.swing.JList<>();
        jLabel_Image = new javax.swing.JLabel();

        jLabel_Header.setFont(new java.awt.Font("Cantarell", 0, 60)); // NOI18N
        jLabel_Header.setText("Who is it?");

        jLabel_Name.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        jLabel_Name.setForeground(new java.awt.Color(3, 53, 103));
        jLabel_Name.setText("Name");
        jLabel_Name.setMaximumSize(new java.awt.Dimension(0, 0));
        jLabel_Name.setPreferredSize(new java.awt.Dimension(536, 40));

        jButton_Show.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/image-x-generic-symbolic.symbolic.png"))); // NOI18N
        jButton_Show.setText("Show");
        jButton_Show.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton_Show.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton_Show.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ShowActionPerformed(evt);
            }
        });

        jButton_Next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/edit-undo-symbolic-rtl.png"))); // NOI18N
        jButton_Next.setText("Next (Random)");
        jButton_Next.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton_Next.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton_Next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_NextActionPerformed(evt);
            }
        });

        jList_Images.setMinimumSize(new java.awt.Dimension(200, 200));
        jList_Images.setPreferredSize(new java.awt.Dimension(350, 1000));
        jScrollPane1.setViewportView(jList_Images);

        jLabel_Image.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_Image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/who_is_it.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_Header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel_Image, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel_Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton_Show, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_Next, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton_Show, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_Next, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel_Header, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel_Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel_Image, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(26, 26, 26))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_ShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ShowActionPerformed
        // TODO add your handling code here:
        if(jButton_Show.getText().equals("Show Image")){
            gamewindowpanel.setImage(new javax.swing.ImageIcon(System.getProperty("user.dir") + "/WhoIsIt/" + 
                    images.get(jList_Images.getSelectedIndex()).getImagefile()));
            jButton_Show.setText("Show");
            jButton_Show.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/dialog-warning-symbolic.symbolic.png")));
        }
        else{
            jButton_Show.setEnabled(false);
            gamewindowpanel.setName(images.get(jList_Images.getSelectedIndex()).getName());
            mainwindow.switchToTeam();
            
            //mark Track as played
            model.setElementAt("●  " + images.get(jList_Images.getSelectedIndex()).getName(), jList_Images.getSelectedIndex());
        
            //count number of played tracks
            if(images.get(jList_Images.getSelectedIndex()).setSelected()){
                playcount++;
            }
        }
        
    }//GEN-LAST:event_jButton_ShowActionPerformed

    private void jButton_NextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_NextActionPerformed
        // TODO add your handling code here:
        selectRandomNext();
        gamewindowpanel.setReset();
        jButton_Show.setEnabled(true);
        jButton_Show.setText("Show Image");
        jButton_Show.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/image-x-generic-symbolic.symbolic.png")));
              
    }//GEN-LAST:event_jButton_NextActionPerformed
    
    //select random
    private void selectRandomNext(){
        
        //notify "all played"
        if(playcount == images.size()){
            jLabel_Name.setForeground(Color.ORANGE);
        }
        
        //select next random track
        if(playcount < images.size()){
            do{
                Random rand = new Random();
                int rnd = rand.nextInt(images.size());
                jList_Images.setSelectedIndex(rnd);
                jLabel_Name.setText(images.get(rnd).getName());
                jLabel_Image.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir") + "/WhoIsIt/" + 
                        images.get(jList_Images.getSelectedIndex()).getImagefile()));
            }while(images.get(jList_Images.getSelectedIndex()).isSelected());
        }
    }
    
    //read files
    private void getImages() throws Exception{
        
        //get current directory
        final String directory = System.getProperty("user.dir");
        
        try{
            File folder = new File(directory + "/WhoIsIt/");
            File[] listOfFiles = folder.listFiles();

            //sort alphabetically to ensure correct order
            Arrays.sort(listOfFiles);
            
            //restore config (if any)
            ArrayList<String> config = new ArrayList();
            if (!mainwindow.clearconfig) {
                try{
                 config = ConfigFile.read(configfile);
                }
                catch (Exception e){
                    //nothing to do
                }
            }
            
            for (int i = 0; i < listOfFiles.length; i++) {
                
                //parse name              
                String name = listOfFiles[i].getName();
                name = name.substring(0, name.indexOf('.'));
                
                //parse filename
                String file = listOfFiles[i].getName();
                
                //append tracklist
                images.add(new Image(file, name));
                
                //restore config
                if (!config.isEmpty()) {
                    for (int e = 0; e < config.size(); e++) {
                        if (config.get(e).equals(images.get(images.size() - 1).getName())) {
                            images.get(images.size() - 1).setSelected();
                            playcount++;
                            break;
                        }
                    }
                }
            }
            
            if(images.isEmpty()){
                throw new Exception("No Tracks found!");
            }
        }
        catch (Exception e){
            throw e;
        }
    }

    //save to config file
    private void storeConfig() {

        ArrayList<String> config = new ArrayList();
        for (int i = 0; i < images.size(); i++) {
            if (images.get(i).isSelected()) {
                config.add(images.get(i).getName());
            }
        }
        try {
            ConfigFile.write(configfile, config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //Class for single Image
    private class Image{
        
        private String imagefile;
        private String name;
        private boolean selected;

        public Image(String imagefile, String name) {
            this.imagefile = imagefile;
            this.name = name;
        }

        /**
         * @return the imagefile
         */
        public String getImagefile() {
            return imagefile;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @return the selected
         */
        public boolean isSelected() {
            return selected;
        }

        /**
         * @param selected the selected to set
         */
        public boolean setSelected() {
            if(this.selected){
                return false;
            }
            else{
                this.selected = true;
                return true;
            }
        }
        
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Next;
    private javax.swing.JButton jButton_Show;
    private javax.swing.JLabel jLabel_Header;
    private javax.swing.JLabel jLabel_Image;
    private javax.swing.JLabel jLabel_Name;
    private javax.swing.JList<String> jList_Images;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}

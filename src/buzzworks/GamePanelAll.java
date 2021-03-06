/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buzzworks;


/**
 *
 * @author Simon Junga (simonthechipmunk)
 */
public class GamePanelAll extends javax.swing.JPanel {

    private mainWindow mainwindow;
    private GameWindow gamewindow;
    
    /**
     * Creates new form GamePanelAll
     */
    public GamePanelAll() {
        //init gamepanel
        initComponents();        
    }

    /**
     * Creates new form TeamPanel
     * @param address the Buzzer Address
     * @param 
     */
    public GamePanelAll(mainWindow mainwindow) throws Exception{
        this.mainwindow = mainwindow;    
        this.gamewindow = new GameWindow();
        
        //init gamepanel
        initComponents();
        
        //try to create gametabs
        boolean successful = false;
        try{
            jTabbedPane1.addTab("Reverse Music", new GamePanelReverseMusic(mainwindow, "Reverse Music"));
            GamePanelReverseMusic panel = (GamePanelReverseMusic)jTabbedPane1.getComponentAt(jTabbedPane1.getTabCount()-1);
            gamewindow.addTab(panel.getName(), panel.gamewindowpanel);
            successful = true;
        }
        catch (Exception e){

        }
        
        try{
            jTabbedPane1.addTab("Who Is It", new GamePanelWhoIsIt(mainwindow, "Who Is It"));
            GamePanelWhoIsIt panel = (GamePanelWhoIsIt)jTabbedPane1.getComponentAt(jTabbedPane1.getTabCount()-1);
            gamewindow.addTab(panel.getName(), panel.gamewindowpanel);
            successful = true;
        }
        catch (Exception e){

        }
        
        if(!successful){
            throw new Exception("Failed to create Games");
        }
        
        gamewindow.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();

        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        // TODO add your handling code here:
        
        try{
            GamePanelReverseMusic panel = (GamePanelReverseMusic)jTabbedPane1.getSelectedComponent();
            gamewindow.setSelectedTabByName(panel.getName());
        }
        catch(Exception e){
            
        }
        
        try{
            GamePanelWhoIsIt panel = (GamePanelWhoIsIt)jTabbedPane1.getSelectedComponent();
            gamewindow.setSelectedTabByName(panel.getName());
        }
        catch(Exception e){
            
        }

    }//GEN-LAST:event_jTabbedPane1StateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}

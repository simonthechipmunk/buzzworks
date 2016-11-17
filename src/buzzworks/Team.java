/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buzzworks;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Basic Team Class
 * @author Simon Junga (simonthechipmunk)
 */
public class Team implements ActionListener{
    
    private static int id = 1;
    private ArrayList<ActionListener> listeners;

    private int address;
    private int teamid;
    private int points;
    private int mode;
    private String name;
    private Color color;

    public Team() {
        teamid = id++;
        listeners = new ArrayList();
        
        address = -1;
        points = 0;
        mode = 0;
        name = "Team_" + teamid;
        color = Color.BLUE;
    }
    
    /**
     * The points to add to the Teams points.
     * @param points points to add (positive only)
     */
    public void addPoints(int points){
        if(points > 0){
            this.points += points;
            actionPerformed(new ActionEvent(this, id, "addPoints"));
        }
    }
    
    /**
     * The points to subtract from the Teams points. points are capped at 0
     * @param points points to subtract (positive only)
     */
    public void subPoints(int points){
        if(points > 0 && this.points > 0){
            this.points -= points;
            if(this.points < 0) this.points = 0;
            actionPerformed(new ActionEvent(this, id, "subPoints"));
        }
        
    }

    /**
     * @return the id
     */
    public int getId() {
        return teamid;
    }
    
    /**
     * @return the address
     */
    public int getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(int address) {
        this.address = address;
    }

    /**
     * @return the points
     */
    public int getPoints() {
        return points;
    }

    /**
     * @param points the points to set
     */
    public void setPoints(int points) {
        this.points = points;
        actionPerformed(new ActionEvent(this, id, "setPoints"));
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
        actionPerformed(new ActionEvent(this, id, "setName"));
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
        actionPerformed(new ActionEvent(this, id, "setColor"));
    }

    /**
     * @return the mode
     */
    public int getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(int mode) {
        this.mode = mode;
    }
    
    
    
    //action listeners for event "teamChanged"
    public void addActionListener(ActionListener listener){
        listeners.add(listener);
    }
    
    public void removeActionListener(ActionListener listener){
        listeners.remove(listener);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        for(int i=0; i<listeners.size(); i++){
            listeners.get(i).actionPerformed(e);
        }
    }

    
    
}

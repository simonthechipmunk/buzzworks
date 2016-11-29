/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buzzworks;

import java.awt.Font;
import javax.swing.JLabel;

/**
 *
 * @author Simon Junga (simonthechipmunk)
 */
public class autoFontsize {
    
    //configure Fontsize of label
    public static void calcFontsize(JLabel label, Font defaultfont) {

        //get dimensions
        int stringWidth = label.getFontMetrics(defaultfont).stringWidth(label.getText());
        int componentWidth = label.getWidth();
        if (componentWidth < label.getPreferredSize().width) {
            componentWidth = label.getPreferredSize().width;
        }
        int componentHeight = label.getHeight();
        if (componentHeight < label.getPreferredSize().height) {
            componentHeight = label.getPreferredSize().height;
        }

        if (componentWidth < stringWidth) {
            // adjust the fontsize
            double widthRatio = (double) componentWidth / (double) stringWidth;
            int newFontSize = (int) (defaultfont.getSize() * widthRatio);

            // Pick a new font size so it will not be larger than the height of label.
            int fontSizeToUse = Math.min(newFontSize, componentHeight) - 1;

            // Set the label's font size to the newly determined size.
            label.setFont(new Font(defaultfont.getName(), Font.PLAIN, fontSizeToUse));
        } else {
            label.setFont(defaultfont);
        }
    }
}

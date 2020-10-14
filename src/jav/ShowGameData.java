/*
 * Created by JFormDesigner on Fri Jun 19 18:50:49 CST 2020
 */

package jav;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import info.clearthought.layout.*;
import org.jdesktop.swingx.*;

/**
 * @author sdf
 */
public class ShowGameData extends JPanel {


    public ShowGameData(int r, int c, List<String> str) {

        initComponents();
        frame1.setSize(460, 350);
        frame1.setVisible(true);
        addlable(str);
    }

    private void addlable(List<String> str) {

        for (String o : str) {
            JLabel jLabel = new JLabel("    " + o);
            jLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panel2.add(jLabel);
        }

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        frame1 = new JFrame();
        panel2 = new JPanel();
        pane = new JScrollPane(panel2);
        //======== frame1 ========
        {
            frame1.setResizable(false);
            frame1.setTitle("\u5386\u53f2\u6218\u7ee9");
            Container frame1ContentPane = frame1.getContentPane();
            frame1ContentPane.setLayout(new VerticalLayout());

            //======== panel2 ========
            {
                panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
            }
            frame1ContentPane.add(pane);
            frame1.setLocationRelativeTo(frame1.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JFrame frame1;
    private JPanel panel2;
    private JScrollPane pane;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

package kpi.fire.gui;

import java.awt.*;
import javax.swing.*;

public class App {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run()
            {
                JFrame frame = new FireFrame();
                frame.setTitle("Методи розрахунку температурного режиму пожежі в приміщеннях будівель різного призначення");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

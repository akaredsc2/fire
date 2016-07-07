package kpi.fire.gui;

import kpi.fire.domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class FireFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 640;
    private static final int DEFAULT_HEIGHT = 480;
    private JTextField textFieldForVolume;
    private JTextField textFieldForHeight;
    private final JTextArea textArea;
    private LinkedList<MaterialCheckboxContainer> materialCheckboxContainers;

    public FireFrame() {
        materialCheckboxContainers = new LinkedList<>();
        // TODO: 07-Jul-16 fill with real data
        materialCheckboxContainers.add(new MaterialCheckboxContainer(new Material("ДСП", 0.0, 0.0, 0.0, 0.0)));
        materialCheckboxContainers.add(new MaterialCheckboxContainer(new Material("Вагонка", 0.0, 0.0, 0.0, 0.0)));
        materialCheckboxContainers.add(new MaterialCheckboxContainer(new Material("Пластмаса", 0.0, 0.0, 0.0, 0.0)));
        materialCheckboxContainers.add(new MaterialCheckboxContainer(new Material("Дерево", 0.0, 0.0, 0.0, 0.0)));

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        createOuterPanel();

        textArea = new JTextArea(50, 50);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        createMenuPanel();
    }

    private class TaskAction implements ActionListener {

        private String task;

        public TaskAction(String task) {
            this.task = task;
        }

        public void actionPerformed(ActionEvent event) {
            double initialTemperature = 293.0; // check it later
            double initialTemperature2 = -1.0; // for Tw0

            FireInspectionData data = FireInspectionData.create()
                    .setVolume(Double.parseDouble(textFieldForVolume.getText()))
                    .setApertureData(new ApertureData(new Aperture[]{new Aperture(167.0, 2.89)}))
                    .setHeight(Double.parseDouble(textFieldForHeight.getText()))
                    .setMaterialData(new MaterialData(new Material[]{new Material("", 46800.0, 4.2, 13.8, 2.4)}));

            FireStats stat = FireStats.computeFireStats(data);

            ReportableTask reportableTask;
            if (task.equals("task2")) {
                reportableTask = new Task2(data, stat, initialTemperature);
            } else if (task.equals("task3")) {
                reportableTask = new Task3(data, stat, initialTemperature2);
            } else if (task.equals("task4")) {
                reportableTask = new Task4(stat, data);
            } else if (task.equals("task5")) {
                reportableTask = new Task5(stat, data);
            } else {
                reportableTask = new Task6(data);
            }

            textArea.append(reportableTask.reportTask(task));
            textArea.append(new String(new char[120]).replace("\0", "-") + '\n');
        }
    }

    private void createOuterPanel() {
        JPanel panelOuter = new JPanel();
        panelOuter.setLayout(new GridLayout(3, 1));
        JPanel panelData = new JPanel();
        panelData.setLayout(new GridLayout(2, 2));

        textFieldForVolume = new JTextField(5);
        addTextFieldToPanel(textFieldForVolume, "V: ", panelData);
        textFieldForHeight = new JTextField(5);
        addTextFieldToPanel(textFieldForHeight, "h: ", panelData);

        JPanel panelOuterForData = new JPanel();
        panelOuterForData.add(panelData);
        panelOuter.add(panelOuterForData);

        // TODO: 07-Jul-16 compact or change format
        JPanel panelOuterForMaterial = new JPanel();
        panelOuterForMaterial.add(new JLabel("Горючі тверді матеріали"));
        for (MaterialCheckboxContainer container : materialCheckboxContainers) {
            panelOuterForMaterial.add(container.getCheckBox());
        }
        panelOuter.add(panelOuterForMaterial);

        JPanel panelOuterForHeaver = new JPanel();
        panelOuterForHeaver.add(new JLabel("Пожежна нагрузка в кг"));
        for (MaterialCheckboxContainer container : materialCheckboxContainers) {
            panelOuterForHeaver.add(container.getTextField());
        }
        panelOuter.add(panelOuterForHeaver);
        add(panelOuter, BorderLayout.NORTH);
    }

    private void addTextFieldToPanel(JTextField textField, String text, JPanel panel) {
        panel.add(new JLabel(text, SwingConstants.RIGHT));
        panel.add(textField);
    }

    private void addButtonToPanel(JPanel panel, String labelText, String buttonText, ActionListener listener) {
        panel.add(new JLabel(labelText, SwingConstants.RIGHT));
        JButton buttonComputeTask1 = new JButton(buttonText);
        panel.add(buttonComputeTask1);
        buttonComputeTask1.addActionListener(listener);
    }

    private void createMenuPanel() {
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new GridLayout(5, 2));

        addButtonToPanel(panelMenu, "Задача №2 ", "Обчислити", new TaskAction("task2"));
        addButtonToPanel(panelMenu, "Задача №3 ", "Обчислити", new TaskAction("task3"));
        addButtonToPanel(panelMenu, "Задача №4 ", "Обчислити", new TaskAction("task4"));
        addButtonToPanel(panelMenu, "Задача №5 ", "Обчислити", new TaskAction("task5"));
        addButtonToPanel(panelMenu, "Задача №6 ", "Обчислити", new TaskAction("task6"));

        JPanel panelOuterForMenu = new JPanel();
        panelOuterForMenu.add(panelMenu);
        add(panelOuterForMenu, BorderLayout.SOUTH);
    }

}


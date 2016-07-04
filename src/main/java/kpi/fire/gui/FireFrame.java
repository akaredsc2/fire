package kpi.fire.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import kpi.fire.domain.*;

public class FireFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 640;
    private static final int DEFAULT_HEIGHT = 480;
    final JTextField textFieldForVolume;
    final JTextField textFieldForHeight;
    final JTextArea textArea;

    public FireFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        textFieldForVolume = new JTextField(5);
        textFieldForHeight = new JTextField(5);

        JPanel panelOuterForData = new JPanel();
        JPanel panelData = new JPanel();
        panelData.setLayout(new GridLayout(2, 2));
        panelData.add(new JLabel("V: ", SwingConstants.RIGHT));
        panelData.add(textFieldForVolume);
        panelData.add(new JLabel("h: ", SwingConstants.RIGHT));
        panelData.add(textFieldForHeight);
        panelOuterForData.add(panelData);
        add(panelOuterForData, BorderLayout.NORTH);

        // JList
        /*
        String[] data = {"one", "two", "three", "four"};
        JList dataList = new JList(data);
        JScrollPane scrollPane = new JScrollPane(dataList);

        JPanel panel1 = new JPanel();
        panel1.add(scrollPane);

        add(panel1, BorderLayout.CENTER);
        */

        textArea = new JTextArea(50, 50);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelOuterForMenu = new JPanel();

        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new GridLayout(5, 2));

        panelMenu.add(new JLabel("Задача №1 ", SwingConstants.RIGHT));
        JButton buttonComputeTask1 = new JButton("Обчислити");
        panelMenu.add(buttonComputeTask1);
        TaskAction taskAction1 = new TaskAction("task1");
        buttonComputeTask1.addActionListener(taskAction1);

        panelMenu.add(new JLabel("Задача №2 ", SwingConstants.RIGHT));
        JButton buttonComputeTask2 = new JButton("Обчислити");
        panelMenu.add(buttonComputeTask2);
        TaskAction taskAction2 = new TaskAction("task2");
        buttonComputeTask2.addActionListener(taskAction2);

        panelMenu.add(new JLabel("Задача №3 ", SwingConstants.RIGHT));
        JButton buttonComputeTask3 = new JButton("Обчислити");
        panelMenu.add(buttonComputeTask3);
        TaskAction taskAction3 = new TaskAction("task3");
        buttonComputeTask3.addActionListener(taskAction3);

        panelMenu.add(new JLabel("Задача №4 ", SwingConstants.RIGHT));
        JButton buttonComputeTask4 = new JButton("Обчислити");
        panelMenu.add(buttonComputeTask4);
        TaskAction taskAction4 = new TaskAction("task4");
        buttonComputeTask4.addActionListener(taskAction4);


        panelMenu.add(new JLabel("Задача №5 ", SwingConstants.RIGHT));
        JButton buttonComputeTask5 = new JButton("Обчислити");
        panelMenu.add(buttonComputeTask5);
        buttonComputeTask5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
                FireInspectionData data = FireInspectionData.create()
                        .setVolume(Double.parseDouble(textFieldForVolume.getText()))
                        .setApertureData(new ApertureData(new Aperture[] {new Aperture(167.0, 2.89)}))
                        .setHeight(Double.parseDouble(textFieldForHeight.getText()))
                        .setMaterialData(new MaterialData(new Material[]{new Material("", 46800.0, 4.2, 13.8, 2.4)}));

                FireStats stat = FireStats.computeFireStats(data);

                Task6 task6 = new Task6(data);

                textArea.append("Задача №5" + '\n');

                if (stat.getFireKind() == FireKind.VENTILATION_REGULATED) {
                    textArea.append("Пожежа, що регулюється вентиляцією" + '\n');
                    textArea.append("Максимальна щільність теплового потоку з продуктами горіння, які йдуть через пройоми: " +
                        task6.computeMaxDensityOfHeatFlow() + '\n'
                    );
                } else {
                    textArea.append("Пожежа, що регулюється навантаженням" + '\n' + "Немає формул." + '\n');
                }

                textArea.append(new String(new char[120]).replace("\0", "-") + '\n');
            }
        });

        panelOuterForMenu.add(panelMenu);
        add(panelOuterForMenu, BorderLayout.SOUTH);
    }

    private class TaskAction implements ActionListener {

        private String task;

        public TaskAction(String task) {
            this.task = task;
        }

        public void actionPerformed(ActionEvent event) {
            double initialTemperature = 293.0; // check it later
            double initialTemperature2 = -1.0; // for Tw0

            String taskDescription = "";
            double result1 = 0.0;
            double result2 = 0.0;
            String commentToResult1 = "";
            String commentToResult2 = "";

            double result1Auxiliary = 0.0;
            double result2Auxiliary = 0.0;
            String commentToResult1Auxiliary = "";
            String commentToResult2Auxiliary = "";

            FireInspectionData data = FireInspectionData.create()
                    .setVolume(Double.parseDouble(textFieldForVolume.getText()))
                    .setApertureData(new ApertureData(new Aperture[] {new Aperture(167.0, 2.89)}))
                    .setHeight(Double.parseDouble(textFieldForHeight.getText()))
                    .setMaterialData(new MaterialData(new Material[]{new Material("", 46800.0, 4.2, 13.8, 2.4)}));

            FireStats stat = FireStats.computeFireStats(data);

            if (task.equals("task1")) {
                Task2 task2 = new Task2(data, stat);
                taskDescription = "Задача №1";

                result1 = task2.computeMaxVolumeAverageTemperature(initialTemperature);
                commentToResult1 = "Максимальна середньооб'ємна температура: ";

                result2 = task2.computeMaxTemperatureTime();
                commentToResult2 = "Час досягнення максимального значення середньооб'ємної температури: ";
            } else if (task.equals("task2")) {
                Task3 task3 = new Task3(data, stat);
                taskDescription = "Задача №2";

                result1 = task3.computeMaxAverageOverlappingAreaTemperature(initialTemperature2);
                commentToResult1 = "Максимальна усереднина температура поверхні перекриття: ";

                result2 = task3.computeMaxAverageTemperatureTime();
                commentToResult2 = "Час досягнення максильного значення усередньої температури поверхні перекриття: ";
            } else if (task.equals("task3")) {
                Task4 task4 = new Task4(stat, data);
                taskDescription = "Задача №3";

                result1 = task4.computeMaxTemperature();
                commentToResult1 = "Максимальна усереднина температура поверхні стін: ";

                result2 = task4.computeTimeAchievementMaxTemperature();
                commentToResult2 = "Час досягнення максильного значення усередненої температури поверхні стін: ";
            } else {   // task == "task4"
                Task5 task5 = new Task5(stat, data);
                taskDescription = "Задача №4";

                result1 = task5.computeMaxDensityForWallConstruction();
                commentToResult1 = "Максимальна усереднина щільність ефективного потоку в конструкції стін: ";
                result1Auxiliary = task5.computeMaxDensityForCoverageConstruction();
                commentToResult1Auxiliary = "Максимальна усереднина щільність ефективного потоку в конструкції покриття: ";

                result2 = task5.computeTimeAchievementMaxDensityForWallConstruction();
                commentToResult2 = "Час досягнення максимальної усередненої щільності ефективного потоку в конструкції стін: ";
                result2Auxiliary = task5.computeTimeAchievementMaxDensityForCoverageConstruction();
                commentToResult2Auxiliary = "Час досягнення максимальної усередненої щільності ефективного потоку в конструкції покриття: ";
            }

            textArea.append(taskDescription + '\n');
            textArea.append((stat.getFireKind() == FireKind.LOAD_REGULATED) ?
                    "Пожежа, що регулюється навантаженням." :
                    "Пожежа, що регулюється вентиляцією" + '\n');
            textArea.append(commentToResult1 + result1 + '\n'
                    + commentToResult2 + result2 + "\n");
            textArea.append((task.equals("task4")) ?
                    commentToResult1Auxiliary  + result1Auxiliary + '\n' + commentToResult2Auxiliary +  result2Auxiliary + '\n' : "");
            textArea.append(new String(new char[120]).replace("\0", "-") + '\n');
        }
    }
}


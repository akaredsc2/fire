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
                    .setApertureData(new ApertureData(new Aperture[]{new Aperture(167.0, 2.89)}))
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
                    commentToResult1Auxiliary + result1Auxiliary + '\n' + commentToResult2Auxiliary + result2Auxiliary + '\n' : "");
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

        addButtonToPanel(panelMenu, "Задача №1 ", "Обчислити", new TaskAction("task1"));
        addButtonToPanel(panelMenu, "Задача №2 ", "Обчислити", new TaskAction("task2"));
        addButtonToPanel(panelMenu, "Задача №3 ", "Обчислити", new TaskAction("task3"));
        addButtonToPanel(panelMenu, "Задача №4 ", "Обчислити", new TaskAction("task4"));
        addButtonToPanel(panelMenu, "Задача №5 ", "Обчислити", this::task5Lambda);

        JPanel panelOuterForMenu = new JPanel();
        panelOuterForMenu.add(panelMenu);
        add(panelOuterForMenu, BorderLayout.SOUTH);
    }
    // TODO: 07-Jul-16 replace
    private void task5Lambda(ActionEvent event) {
        FireInspectionData data = FireInspectionData.create()
                .setVolume(Double.parseDouble(textFieldForVolume.getText()))
                .setApertureData(new ApertureData(new Aperture[]{new Aperture(167.0, 2.89)}))
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

}


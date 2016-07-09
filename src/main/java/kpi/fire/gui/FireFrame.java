package kpi.fire.gui;

import kpi.fire.domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class FireFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 640;
    private static final int DEFAULT_HEIGHT = 640;
    private final JTextArea textArea;
    private List<MaterialCheckboxContainer> materialCheckboxContainers;
    private Map<String, JTextField> textFieldMap;
    private List<ApertureComponent> apertureComponentList;
    private String task2 = "Розрахунок средньооб'ємної температури";
    private String task3 = "Розрахунок середньої температури поверхності перекриття";
    private String task4 = "Розрахунок середньої температури поверхності стін";
    private String task5 = "Розрахунок щільності ефективного теплового потоку в конструкції стін та перекриття";
    private String task6 = "Розрахунок максимальної щільності теплового потоку з продуктами горіння, що йдуть через пройоми";


    public FireFrame() {
        materialCheckboxContainers = new LinkedList<>();
        materialCheckboxContainers.add(new MaterialCheckboxContainer(new Material("ДСП", 0.0, 1.0, 18.0, 1.0)));
        materialCheckboxContainers.add(new MaterialCheckboxContainer(new Material("Вагонка", 0.0, 1.0, 1.0, 1.0)));
        materialCheckboxContainers.add(new MaterialCheckboxContainer(new Material("Пластмаса", 0.0, 1.0, 41.87, 1.0)));
        materialCheckboxContainers.add(new MaterialCheckboxContainer(new Material("Дерево", 0.0, 4.2, 13.8, 2.4)));

        textFieldMap = new TreeMap<>();
        textFieldMap.put("temperatureTw", new JTextField(5));
        textFieldMap.put("temperatureT", new JTextField(5));
        textFieldMap.put("height", new JTextField(5));
        textFieldMap.put("volume", new JTextField(5));

        apertureComponentList = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            apertureComponentList.add(new ApertureComponent((i+1) + ". Площа (м2)", "Висота (м)"));
        }

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        createOuterPanel();

        textArea = new JTextArea(50, 50);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        createMenuPanel();
    }

    private class TaskAction implements ActionListener {

        private String taskName;

        public TaskAction(String taskName) {
            this.taskName = taskName;
        }

        public void actionPerformed(ActionEvent event) {
            if (!isCorrectInput(textFieldMap.get("volume").getText()) ||
                    !isCorrectInput(textFieldMap.get("height").getText()) ||
                    !isCorrectInput(textFieldMap.get("temperatureT").getText()) ||
                    !isCorrectInput(textFieldMap.get("temperatureTw").getText())) {
                messageIncorrectData(textArea);
                return;
            }

            Material[] materials = materialCheckboxContainers.stream()
                    .filter(x -> x.getCheckBox().isSelected())
                    .map(FireFrame.this::processInput)
                    .toArray(Material[]::new);

            for (int i = 0; i < materials.length; i++) {
                if (materials[i].getFireLoad() == -1.0) {
                    messageIncorrectData(textArea);
                    return;
                }
            }

            apertureComponentList.stream().forEach(ApertureComponent::update);
            Aperture[] apertures = apertureComponentList.stream()
                    .filter(x -> x.getAperture().getHeight() != -1.0 || x.getAperture().getArea() != -1.0)
                    .map(ApertureComponent::getAperture)
                    .toArray(Aperture[]::new);

            for (int i = 0; i < apertures.length; i++) {
                if (apertures[i].getArea() != -1.0 && apertures[i].getHeight() == -1.0 ||
                        apertures[i].getArea() == -1.0 && apertures[i].getHeight() != -1.0) {
                    messageIncorrectData(textArea);
                    return;
                }
            }

            if (materials.length != 0 && apertures.length != 0) {

                FireInspectionData data = FireInspectionData.create()
                        .setVolume(Double.parseDouble(textFieldMap.get("volume").getText()))
                        .setApertureData(new ApertureData(apertures))
                        .setHeight(Double.parseDouble(textFieldMap.get("height").getText()))
                        .setMaterialData(new MaterialData(materials))
                        .setLowestWoodBurnHeat(13.8)
                        .setInitialVolumeAverageTemperature(Double.parseDouble(textFieldMap.get("temperatureT").getText()))
                        .setInitialAverageOverlappingAreaTemperature(
                                Double.parseDouble(textFieldMap.get("temperatureTw").getText()));

                FireStats stat = FireStats.computeFireStats(data);

                ReportableTask reportableTask;
                if (taskName.equals(task2)) {
                    reportableTask = new Task2(data, stat);
                } else if (taskName.equals(task3)) {
                    reportableTask = new Task3(data, stat);
                } else if (taskName.equals(task4)) {
                    reportableTask = new Task4(data, stat);
                } else if (taskName.equals(task5)) {
                    reportableTask = new Task5(data, stat);
                } else {
                    reportableTask = new Task6(data);
                }

                textArea.append(reportableTask.reportTask(taskName));
                textArea.append(new String(new char[120]).replace("\0", "-") + '\n');
            } else {
                messageNotEnoughData(textArea);
            }
        }
    }

    private void createOuterPanel() {
        JPanel panelOuter = new JPanel();
        panelOuter.setLayout(new BoxLayout(panelOuter, BoxLayout.PAGE_AXIS));

        JPanel panelData = new JPanel();
        panelData.setLayout(new GridLayout(3, 1));

        JPanel panelForVolumeAndHeight = new JPanel();
        panelForVolumeAndHeight.add(new JLabel("Об'єм (м3): ", SwingConstants.RIGHT));
        panelForVolumeAndHeight.add(textFieldMap.get("volume"));
        panelForVolumeAndHeight.add(new JLabel("Висота (м): ", SwingConstants.RIGHT));
        panelForVolumeAndHeight.add(textFieldMap.get("height"));
        panelData.add(panelForVolumeAndHeight);

        JPanel panelForTemperatureT = new JPanel();
        panelForTemperatureT.add(new JLabel("Початкова середньооб'ємна температура (K): ", SwingConstants.RIGHT));
        panelForTemperatureT.add(textFieldMap.get("temperatureT"));
        panelData.add(panelForTemperatureT);

        JPanel panelForTemperatureTw = new JPanel();
        panelForTemperatureTw.add(new JLabel("Початкова середня температура поверхності перекриття (K): ", SwingConstants.RIGHT));
        panelForTemperatureTw.add(textFieldMap.get("temperatureTw"));
        panelData.add(panelForTemperatureTw);

        JPanel panelOuterForData = new JPanel();
        panelOuterForData.add(panelData);
        panelOuter.add(panelOuterForData);

        // TODO: 08-Jul-16 extract method
        JPanel panelOuterForMaterial = new JPanel();
        panelOuterForMaterial.add(new JLabel("Горючі тверді матеріали:"));
        for (MaterialCheckboxContainer container : materialCheckboxContainers) {
            panelOuterForMaterial.add(container.getCheckBox());
        }
        panelOuter.add(panelOuterForMaterial);

        JPanel panelOuterForHeaver = new JPanel();
        panelOuterForHeaver.add(new JLabel("Пожежна нагрузка (кг):"));
        for (MaterialCheckboxContainer container : materialCheckboxContainers) {
            panelOuterForHeaver.add(container.getTextField());
        }
        panelOuter.add(panelOuterForHeaver);

        JPanel aperturePanel = new JPanel(new GridLayout(6, 1));
        JPanel panelForLabel = new JPanel();
        panelForLabel.add(new JLabel("Характеристики пройомів приміщення (площа та висота):"));
        aperturePanel.add(panelForLabel);
        for (ApertureComponent component : apertureComponentList) {
            component.addToPanel(aperturePanel);
        }
        panelOuter.add(aperturePanel);

        add(panelOuter, BorderLayout.NORTH);
    }

    private void addButtonToPanel(JPanel panel, String labelText, ActionListener listener) {
        JPanel p = new JPanel();
        p.add(new JLabel(labelText, SwingConstants.RIGHT));
        JButton buttonComputeTask = new JButton("Обчислити");
        p.add(buttonComputeTask);
        buttonComputeTask.addActionListener(listener);
        panel.add(p);
    }

    private void createMenuPanel() {
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new GridLayout(5, 1));

        addButtonToPanel(panelMenu, "1. " + task2 + ": ", new TaskAction(task2));
        addButtonToPanel(panelMenu, "2. " + task3 + ": ", new TaskAction(task3));
        addButtonToPanel(panelMenu, "3. " + task4 + ": ", new TaskAction(task4));
        addButtonToPanel(panelMenu, "4. " + task5 + ": ", new TaskAction(task5));
        addButtonToPanel(panelMenu, "5. " + task6 + ": ", new TaskAction(task6));

        JPanel panelOuterForMenu = new JPanel();
        panelOuterForMenu.add(panelMenu);

        add(panelOuterForMenu, BorderLayout.SOUTH);
    }

    private Material processInput(MaterialCheckboxContainer container) {
        Material material = container.getMaterial();
        String input = container.getTextField().getText();
        if (isCorrectInput(input)) {
            material.setFireLoad(Double.parseDouble(input));
        } else {
            material.setFireLoad(-1.0);
        }
        return material;
    }

    public static boolean isCorrectInput(String input) {
        return input.matches("[0-9]+(\\.[0-9][0-9]?)?") && (Double.parseDouble(input) > 0);
    }

    private void messageNotEnoughData(JTextArea textArea) {
        textArea.append("Введених данних не достатньо для обчислень." + System.lineSeparator());
        textArea.append(new String(new char[120]).replace("\0", "-") + '\n');
    }

    private void messageIncorrectData(JTextArea textArea) {
        textArea.append("Перевірте на правильність вхідні дані." + System.lineSeparator());
        textArea.append(new String(new char[120]).replace("\0", "-") + '\n');
    }

}
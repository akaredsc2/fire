package kpi.fire.gui;

import kpi.fire.domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class FireFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 1280;
    private static final int DEFAULT_HEIGHT = 800;
    private final JTextArea textArea;
    private List<MaterialCheckboxContainer> materialCheckboxContainers;
    private Map<String, JTextField> textFieldMap;
    private List<ApertureComponent> apertureComponentList =  new LinkedList<>();
    private String task2 = "Розрахунок средньооб'ємної температури";
    private String task3 = "Розрахунок середньої температури поверхності перекриття";
    private String task4 = "Розрахунок середньої температури поверхності стін";
    private String task5 = "Розрахунок щільності ефективного теплового потоку в конструкції стін та перекриття";
    private String task6 = "Розрахунок максимальної щільності теплового потоку з продуктами горіння, що йдуть через пройоми";
    private JPanel aperturePanel = new JPanel();
    private JPanel textFieldsForAreaAndHeight = new JPanel();

    public FireFrame() {
        materialCheckboxContainers = new LinkedList<>();
        materialCheckboxContainers.add(new MaterialCheckboxContainer(new Material("ДСП", 0.0, 4.4, 18.0, 14.0)));
        materialCheckboxContainers.add(new MaterialCheckboxContainer(new Material("Вагонка", 0.0, 4.2, 13.8, 2.4)));
        materialCheckboxContainers.add(new MaterialCheckboxContainer(new Material("Линолеум", 0.0, 5.0, 14.0, 13.7)));
        materialCheckboxContainers.add(new MaterialCheckboxContainer(new Material("Карболитові вироби", 0.0, 7.1, 26.9, 9.5)));
        materialCheckboxContainers.add(new MaterialCheckboxContainer(new Material("Поліпропілен", 0.0, 6.5, 45.67, 14.5)));

        textFieldMap = new TreeMap<>();
        textFieldMap.put("temperatureTw", new JTextField(5));
        textFieldMap.put("temperatureT", new JTextField(5));
        textFieldMap.put("height", new JTextField(5));
        textFieldMap.put("volume", new JTextField(5));
        textFieldMap.put("numberAperture", new JTextField(5));

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        createOuterPanel();

        textArea = new JTextArea(50, 50);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
        textArea.setFont(new Font("Arial", Font.PLAIN, 18));

        createMenuPanel();
    }

    private class TaskAction implements ActionListener {

        private String taskName;

        public TaskAction(String taskName) {
            this.taskName = taskName;
        }

        public void actionPerformed(ActionEvent event) {
            List<String> listKey = new ArrayList<>(textFieldMap.keySet());
            for (String key: listKey) {
                if (!isCorrectInput(textFieldMap.get(key).getText())) {
                    messageIncorrectData(textArea);
                    return;
                }
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
                        .setInitialVolumeAverageTemperature(Double.parseDouble(textFieldMap.get("temperatureT").getText() + 273))
                        .setInitialAverageOverlappingAreaTemperature(
                                Double.parseDouble(textFieldMap.get("temperatureTw").getText() + 273));

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

        panelForVolumeAndHeight.add(createLabelAndSetFont("Об'єм (м3): ", true));
        panelForVolumeAndHeight.add(textFieldMap.get("volume"));
        panelForVolumeAndHeight.add(createLabelAndSetFont("Висота (м): ", true));
        panelForVolumeAndHeight.add(textFieldMap.get("height"));
        panelData.add(panelForVolumeAndHeight);

        JPanel panelForTemperatureT = new JPanel();
        panelForTemperatureT.add(createLabelAndSetFont("Початкова середньооб'ємна температура (C): ", true));
        panelForTemperatureT.add(textFieldMap.get("temperatureT"));
        panelData.add(panelForTemperatureT);

        JPanel panelForTemperatureTw = new JPanel();
        panelForTemperatureTw.add(createLabelAndSetFont("Початкова середня температура поверхності перекриття (C): ", true));
        panelForTemperatureTw.add(textFieldMap.get("temperatureTw"));
        panelData.add(panelForTemperatureTw);

        JPanel panelOuterForData = new JPanel();
        panelOuterForData.add(panelData);
        panelOuter.add(panelOuterForData);

        // TODO: 08-Jul-16 extract method
        JPanel panelOuterForMaterial = new JPanel();
        panelOuterForMaterial.add(createLabelAndSetFont("Горючі тверді матеріали:", false));
        for (MaterialCheckboxContainer container : materialCheckboxContainers) {
            panelOuterForMaterial.add(container.getCheckBox());
        }
        panelOuter.add(panelOuterForMaterial);

        JPanel panelOuterForHeaver = new JPanel();
        panelOuterForHeaver.add(createLabelAndSetFont("Пожежна нагрузка (кг):", false));
        for (MaterialCheckboxContainer container : materialCheckboxContainers) {
            panelOuterForHeaver.add(container.getTextField());
        }
        panelOuter.add(panelOuterForHeaver);

        JPanel panelForLabel = new JPanel();
        panelForLabel.add(createLabelAndSetFont("Характеристики пройомів приміщення (площа та висота):", false));
        panelOuter.add(panelForLabel);

        aperturePanel.add(createLabelAndSetFont("Кількість пройомів:", false));
        aperturePanel.add(textFieldMap.get("numberAperture"));
        addButtonToPanel(aperturePanel, "", (event) -> {
            if (!isCorrectInput(textFieldMap.get("numberAperture").getText())) {
                messageIncorrectData(textArea);
                return;
            }

            JPanel textFieldsForAreaAndHeightNew = new JPanel();

            int number = Integer.parseInt(textFieldMap.get("numberAperture").getText());
            apertureComponentList = new LinkedList<>();
            for (int i = 0; i < number; i++) {
                apertureComponentList.add(new ApertureComponent((i + 1) + ". Площа (м2)", "Висота (м)"));
            }

            textFieldsForAreaAndHeightNew.setLayout(new GridLayout(number, 1));
            for (ApertureComponent component : apertureComponentList) {
                component.addToPanel(textFieldsForAreaAndHeightNew);
            }

            aperturePanel.add(textFieldsForAreaAndHeightNew);

            aperturePanel.remove(textFieldsForAreaAndHeight);
            aperturePanel.revalidate();
            aperturePanel.repaint();

            textFieldsForAreaAndHeight = textFieldsForAreaAndHeightNew;
        }, "+");

        panelOuter.add(aperturePanel);

        add(panelOuter, BorderLayout.NORTH);
    }

    private void addButtonToPanel(JPanel panel, String labelText, ActionListener listener, String buttonText) {
        JPanel p = new JPanel();
        p.add(createLabelAndSetFont(labelText, true));
        JButton buttonComputeTask = new JButton(buttonText);
        buttonComputeTask.setFont(new Font("Arial", Font.PLAIN, 18));
        p.add(buttonComputeTask);
        buttonComputeTask.addActionListener(listener);
        panel.add(p);
    }

    private void createMenuPanel() {
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new GridLayout(5, 1));

        addButtonToPanel(panelMenu, "1. " + task2 + ": ", new TaskAction(task2), "Обчислити");
        addButtonToPanel(panelMenu, "2. " + task3 + ": ", new TaskAction(task3), "Обчислити");
        addButtonToPanel(panelMenu, "3. " + task4 + ": ", new TaskAction(task4), "Обчислити");
        addButtonToPanel(panelMenu, "4. " + task5 + ": ", new TaskAction(task5), "Обчислити");
        addButtonToPanel(panelMenu, "5. " + task6 + ": ", new TaskAction(task6), "Обчислити");

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

    private JLabel createLabelAndSetFont(String nameLabel, boolean isBias) {
        JLabel label;
        label = (isBias) ? new JLabel(nameLabel, SwingConstants.RIGHT) : new JLabel(nameLabel);
        label.setFont(new Font("Serif", Font.BOLD, 20));
        return label;
    }
}
package kpi.fire.gui;

import kpi.fire.domain.Aperture;

import javax.swing.*;

public class ApertureComponent implements GuiComponent {

    private JTextField areaTextField;
    private JLabel areaLabel;
    private JTextField heightTextField;
    private JLabel heightLabel;

    private Aperture aperture;

    public ApertureComponent() {
        areaTextField = new JTextField(5);
        areaLabel = new JLabel("Area");
        heightTextField = new JTextField(5);
        heightLabel = new JLabel("Height");

        aperture = new Aperture(0.0, 0.0);
    }

    public Aperture getAperture() {
        return aperture;
    }

    @Override
    public void update() {
        String area = areaTextField.getText();
        String height = heightTextField.getText();
        aperture = new Aperture("".equals(area) ? 0.0 : Double.parseDouble(area),
                "".equals(height) ? 0.0 : Double.parseDouble(height));
    }

    @Override
    public void addToPanel(JPanel panel) {
        JPanel inner = new JPanel();
        inner.add(areaLabel);
        inner.add(areaTextField);
        inner.add(heightLabel);
        inner.add(heightTextField);
        panel.add(inner);
    }

}

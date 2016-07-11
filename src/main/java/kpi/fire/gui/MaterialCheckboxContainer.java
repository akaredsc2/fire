package kpi.fire.gui;

import kpi.fire.domain.Material;

import javax.swing.*;
import java.awt.*;

public class MaterialCheckboxContainer {

    private Material material;
    private JTextField textField;
    private JCheckBox checkBox;

    public MaterialCheckboxContainer(Material material) {
        this.material = material;

        checkBox = new JCheckBox(material.getName());
        checkBox.setFont(new Font("Arial", Font.BOLD, 16));
        checkBox.addActionListener(event -> textField.setEnabled(checkBox.isSelected()));

        textField = new JTextField(5);
        textField.setEnabled(false);
    }

    public Material getMaterial() {
        return material;
    }

    public JTextField getTextField() {
        return textField;
    }

    public JCheckBox getCheckBox() {
        return checkBox;
    }

}

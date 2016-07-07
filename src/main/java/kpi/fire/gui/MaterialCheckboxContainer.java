package kpi.fire.gui;

import kpi.fire.domain.Material;

import javax.swing.*;

public class MaterialCheckboxContainer {

    private Material material;
    private JTextField textField;
    private JCheckBox checkBox;

    public MaterialCheckboxContainer(Material material) {
        this.material = material;

        checkBox = new JCheckBox(material.getName());
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

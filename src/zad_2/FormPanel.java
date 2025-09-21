package SOLUTIONS.src.zad_2;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class FormPanel extends JPanel {

    public interface FormSubmitListener {
        void onSubmit(Programmer programmer);
    }

    private final JTextField nameField = new JTextField(16);
    private final JTextField emailField = new JTextField(16);
    private final JComboBox<Language> languageCombo = new JComboBox<>(Language.values());
    private final JRadioButton rbFull = new JRadioButton(EmploymentType.FULL_TIME.getDisplayName(), true);
    private final JRadioButton rbPart = new JRadioButton(EmploymentType.PART_TIME.getDisplayName());
    private final JButton submitBtn = new JButton("Submit");

    private FormSubmitListener listener;

    public FormPanel() {
        setBorder(BorderFactory.createTitledBorder("Unos programera"));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        ButtonGroup group = new ButtonGroup();
        group.add(rbFull);
        group.add(rbPart);

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Ime:"), gbc);
        gbc.gridx = 1;
        add(nameField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        add(emailField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Jezik:"), gbc);
        gbc.gridx = 1;
        add(languageCombo, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Zaposlenje:"), gbc);
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        radioPanel.add(rbFull);
        radioPanel.add(Box.createHorizontalStrut(8));
        radioPanel.add(rbPart);
        gbc.gridx = 1;
        add(radioPanel, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(submitBtn, gbc);

        submitBtn.addActionListener(e -> handleSubmit());
    }

    public void setFormSubmitListener(FormSubmitListener listener) {
        this.listener = listener;
    }

    private void handleSubmit() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        Language lang = (Language) languageCombo.getSelectedItem();
        EmploymentType emp = rbFull.isSelected() ? EmploymentType.FULL_TIME : EmploymentType.PART_TIME;

        System.out.println("[FORM] Submit kliknut. Ime=" + name + ", Email=" + email + ", Jezik=" + lang + ", Zaposlenje=" + emp);

        try {
            if (name.isBlank()) {
                System.out.println("[FORM] Greška: Ime je prazno.");
                throw new IllegalArgumentException("Ime je obavezno.");
            }
            if (!isValidEmail(email)) {
                System.out.println("[FORM] Greška: Neispravan email format.");
                throw new IllegalArgumentException("Neispravan email.");
            }

            Programmer p = new Programmer(name, email, lang, emp);
            System.out.println("[FORM] Kreiran objekt: " + p);

            if (listener != null) {
                listener.onSubmit(p);
                clearForm();
                System.out.println("[FORM] Slanje listeneru i čišćenje forme završeno.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isValidEmail(String email) {
        String regex = "^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$";
        return Pattern.compile(regex).matcher(email).matches();
    }

    private void clearForm() {
        nameField.setText("");
        emailField.setText("");
        languageCombo.setSelectedIndex(0);
        rbFull.setSelected(true);
        nameField.requestFocusInWindow();
    }
}
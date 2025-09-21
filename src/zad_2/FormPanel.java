package SOLUTIONS.src.zad_2; // Definišemo paket u kojem se nalazi ova klasa

import javax.swing.*;       // Uvozimo Swing komponente za GUI
import java.awt.*;          // Uvozimo AWT komponente za layout
import java.util.regex.Pattern; // Uvozimo Pattern za validaciju email adresa

// Panel klasa za unos podataka o novom programeru
public class FormPanel extends JPanel {

    // Interface za slušanje događaja slanja forme
    public interface FormSubmitListener {
        void onSubmit(Programmer programmer); // Metoda koja se poziva kada se forma pošalje
    }

    // GUI komponente forme
    private final JTextField nameField = new JTextField(16);        // Polje za unos imena
    private final JTextField emailField = new JTextField(16);       // Polje za unos email adrese
    private final JComboBox<Language> languageCombo = new JComboBox<>(Language.values()); // Dropdown za izbor jezika
    private final JRadioButton rbFull = new JRadioButton(EmploymentType.FULL_TIME.getDisplayName(), true); // Radio gumb za puno radno vrijeme
    private final JRadioButton rbPart = new JRadioButton(EmploymentType.PART_TIME.getDisplayName());        // Radio gumb za pola radnog vremena
    private final JButton submitBtn = new JButton("Submit"); // Gumb za slanje forme

    private FormSubmitListener listener; // Listener za rukovanje slanjem forme

    // Konstruktor koji postavlja GUI komponente forme
    public FormPanel() {
        setBorder(BorderFactory.createTitledBorder("Unos programera")); // Postavlja okvir s naslovom
        setLayout(new GridBagLayout()); // Postavlja GridBagLayout za fleksibilan raspored
        GridBagConstraints gbc = new GridBagConstraints(); // Kreira constraints za layout
        gbc.insets = new Insets(4, 4, 4, 4); // Postavlja razmake između komponenti
        gbc.fill = GridBagConstraints.HORIZONTAL; // Komponente se protežu horizontalno

        ButtonGroup group = new ButtonGroup(); // Kreira grupu za radio gumbove
        group.add(rbFull); // Dodaje radio gumb za puno radno vrijeme u grupu
        group.add(rbPart); // Dodaje radio gumb za pola radnog vremena u grupu

        int row = 0; // Brojač redova u formi

        // Dodavanje polja za ime
        gbc.gridx = 0; gbc.gridy = row; // Postavlja poziciju labela
        add(new JLabel("Ime:"), gbc);   // Dodaje label za ime
        gbc.gridx = 1;                  // Pomiče na sljedeći stupac
        add(nameField, gbc);            // Dodaje polje za unos imena
        row++;                          // Prelazi na sljedeći red

        // Dodavanje polja za email
        gbc.gridx = 0; gbc.gridy = row; // Postavlja poziciju labela
        add(new JLabel("Email:"), gbc); // Dodaje label za email
        gbc.gridx = 1;                  // Pomiče na sljedeći stupac
        add(emailField, gbc);           // Dodaje polje za unos email adrese
        row++;                          // Prelazi na sljedeći red

        // Dodavanje dropdown-a za programski jezik
        gbc.gridx = 0; gbc.gridy = row;  // Postavlja poziciju labela
        add(new JLabel("Jezik:"), gbc); // Dodaje label za jezik
        gbc.gridx = 1;                   // Pomiče na sljedeći stupac
        add(languageCombo, gbc);         // Dodaje dropdown za izbor jezika
        row++;                           // Prelazi na sljedeći red

        // Dodavanje radio gumbova za tip zaposlenja
        gbc.gridx = 0; gbc.gridy = row;          // Postavlja poziciju labela
        add(new JLabel("Zaposlenje:"), gbc);     // Dodaje label za zaposlenje
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); // Kreira panel za radio gumbove
        radioPanel.add(rbFull);                  // Dodaje radio gumb za puno radno vrijeme
        radioPanel.add(Box.createHorizontalStrut(8)); // Dodaje horizontalni razmak
        radioPanel.add(rbPart);                  // Dodaje radio gumb za pola radnog vremena
        gbc.gridx = 1;                           // Pomiče na sljedeći stupac
        add(radioPanel, gbc);                    // Dodaje panel s radio gumbovima
        row++;                                   // Prelazi na sljedeći red

        // Dodavanje submit gumba
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2; // Postavlja poziciju preko dva stupca
        gbc.fill = GridBagConstraints.NONE;                 // Gumb se ne proteže
        gbc.anchor = GridBagConstraints.CENTER;             // Centrira gumb
        add(submitBtn, gbc);                                // Dodaje submit gumb

        submitBtn.addActionListener(e -> handleSubmit()); // Dodaje action listener za submit gumb
    }

    // Metoda za postavljanje listener-a za slanje forme
    public void setFormSubmitListener(FormSubmitListener listener) {
        this.listener = listener; // Postavlja listener
    }

    // Privatna metoda za rukovanje slanjem forme
    private void handleSubmit() {
        String name = nameField.getText().trim();     // Dohvaća ime iz polja i uklanja razmake
        String email = emailField.getText().trim();   // Dohvaća email iz polja i uklanja razmake
        Language lang = (Language) languageCombo.getSelectedItem(); // Dohvaća odabrani jezik
        // Određuje tip zaposlenja na osnovu odabranog radio gumba
        EmploymentType emp = rbFull.isSelected() ? EmploymentType.FULL_TIME : EmploymentType.PART_TIME;

        // Ispisuje podatke forme za debugging
        System.out.println("[FORM] Submit kliknut. Ime=" + name + ", Email=" + email + ", Jezik=" + lang + ", Zaposlenje=" + emp);

        try {
            // Validira da ime nije prazno
            if (name.isBlank()) {
                System.out.println("[FORM] Greška: Ime je prazno."); // Ispisuje grešku u konzolu
                throw new IllegalArgumentException("Ime je obavezno."); // Baca iznimku
            }
            // Validira format email adrese
            if (!isValidEmail(email)) {
                System.out.println("[FORM] Greška: Neispravan email format."); // Ispisuje grešku u konzolu
                throw new IllegalArgumentException("Neispravan email."); // Baca iznimku
            }

            Programmer p = new Programmer(name, email, lang, emp); // Kreira novi objekt programera
            System.out.println("[FORM] Kreiran objekt: " + p); // Ispisuje kreiran objekt

            // Prosljeđuje programera listener-u ako postoji
            if (listener != null) {
                listener.onSubmit(p); // Poziva listener metodu
                clearForm();          // Briše sadržaj forme
                System.out.println("[FORM] Slanje listeneru i čišćenje forme završeno."); // Ispisuje poruku
            }
        } catch (Exception ex) {
            // Prikazuje grešku korisniku u dijaloškom okviru
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Privatna metoda za validaciju email adrese pomoću regex-a
    private boolean isValidEmail(String email) {
        String regex = "^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$"; // Regex pattern za email validaciju
        return Pattern.compile(regex).matcher(email).matches(); // Provjerava odgovara li email pattern-u
    }

    // Privatna metoda za brisanje sadržaja forme
    private void clearForm() {
        nameField.setText("");              // Briše sadržaj polja za ime
        emailField.setText("");             // Briše sadržaj polja za email
        languageCombo.setSelectedIndex(0);  // Postavlja prvi jezik kao odabrani
        rbFull.setSelected(true);           // Odabira radio gumb za puno radno vrijeme
        nameField.requestFocusInWindow();   // Postavlja fokus na polje za ime
    }
}
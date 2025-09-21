package SOLUTIONS.src.zad_2;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("[APP] Pokretanje aplikacije...");
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                System.out.println("[APP] Postavljen Look&Feel sustava.");
            } catch (Exception ex) {
                System.out.println("[APP] Nije moguće postaviti Look&Feel: " + ex.getMessage());
            }
            new MainFrame().setVisible(true);
            System.out.println("[APP] Aplikacija je vidljiva.");
        });
    }
}

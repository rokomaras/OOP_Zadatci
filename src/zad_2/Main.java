package SOLUTIONS.src.zad_2; // Definišemo paket u kojem se nalazi ova klasa

import javax.swing.*; // Uvozimo Swing komponente za GUI

// Glavna klasa aplikacije
public class Main {
    // Glavna metoda koja se izvršava pri pokretanju aplikacije
    public static void main(String[] args) {
        System.out.println("[APP] Pokretanje aplikacije..."); // Ispisuje poruku o pokretanju
        // Izvršava GUI kod u Event Dispatch Thread-u
        SwingUtilities.invokeLater(() -> {
            try {
                // Postavlja sistemski Look&Feel za bolji izgled aplikacije
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                System.out.println("[APP] Postavljen Look&Feel sustava."); // Ispisuje poruku o uspješnom postavljanju
            } catch (Exception ex) {
                // Hvata iznimke ako postavljanje Look&Feel-a ne uspije
                System.out.println("[APP] Nije moguće postaviti Look&Feel: " + ex.getMessage());
            }
            // Kreira i prikazuje glavni prozor aplikacije
            new MainFrame().setVisible(true);
            System.out.println("[APP] Aplikacija je vidljiva."); // Ispisuje poruku da je aplikacija prikazana
        });
    }
}

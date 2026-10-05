import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.util.List;

public class TodoList {

    // Une tâche : un texte et un état (faite ou non)
    static class Tache {
        String texte;
        boolean faite;

        Tache(String texte, boolean faite) {
            this.texte = texte;
            this.faite = faite;
        }

        // Ce que la JList affiche pour chaque tâche
        @Override
        public String toString() {
            return (faite ? "[x] " : "[ ] ") + texte;
        }
    }

    private static final Path FICHIER = Paths.get("taches.txt");

    public static void main(String[] args) {
        // Bonne pratique Swing : créer l'interface sur le thread graphique
        SwingUtilities.invokeLater(TodoList::creerFenetre);
    }

    private static void creerFenetre() {
        JFrame frame = new JFrame("Ma To-Do List");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(5, 5));

        // Le modèle contient les données, la JList se contente de les afficher
        DefaultListModel<Tache> modele = new DefaultListModel<>();
        JList<Tache> liste = new JList<>(modele);
        charger(modele);

        // Haut : champ de saisie + bouton Ajouter
        JTextField champ = new JTextField();
        JButton boutonAjouter = new JButton("Ajouter");
        JPanel haut = new JPanel(new BorderLayout(5, 5));
        haut.add(champ, BorderLayout.CENTER);
        haut.add(boutonAjouter, BorderLayout.EAST);

        // Bas : boutons d'action
        JButton boutonFaite = new JButton("Fait / Pas fait");
        JButton boutonSupprimer = new JButton("Supprimer");
        JPanel bas = new JPanel();
        bas.add(boutonFaite);
        bas.add(boutonSupprimer);

        frame.add(haut, BorderLayout.NORTH);
        frame.add(new JScrollPane(liste), BorderLayout.CENTER);
        frame.add(bas, BorderLayout.SOUTH);

        // Ajouter une tâche (bouton ou touche Entrée)
        Runnable ajouter = () -> {
            String texte = champ.getText().trim();
            if (!texte.isEmpty()) {
                modele.addElement(new Tache(texte, false));
                champ.setText("");
                sauvegarder(modele);
            }
        };
        boutonAjouter.addActionListener(e -> ajouter.run());
        champ.addActionListener(e -> ajouter.run());

        // Basculer l'état de la tâche sélectionnée
        boutonFaite.addActionListener(e -> {
            Tache t = liste.getSelectedValue();
            if (t != null) {
                t.faite = !t.faite;
                liste.repaint();
                sauvegarder(modele);
            }
        });

        // Supprimer la tâche sélectionnée
        boutonSupprimer.addActionListener(e -> {
            int i = liste.getSelectedIndex();
            if (i != -1) {
                modele.remove(i);
                sauvegarder(modele);
            }
        });

        frame.setVisible(true);
    }

    // Format du fichier : une tâche par ligne, "1;texte" (faite) ou "0;texte"
    private static void sauvegarder(DefaultListModel<Tache> modele) {
        try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(FICHIER))) {
            for (int i = 0; i < modele.size(); i++) {
                Tache t = modele.get(i);
                out.println((t.faite ? "1" : "0") + ";" + t.texte);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erreur de sauvegarde : " + ex.getMessage());
        }
    }

    private static void charger(DefaultListModel<Tache> modele) {
        if (!Files.exists(FICHIER)) return;
        try {
            List<String> lignes = Files.readAllLines(FICHIER);
            for (String ligne : lignes) {
                String[] parts = ligne.split(";", 2); // 2 : le texte peut contenir des ";"
                if (parts.length == 2) {
                    modele.addElement(new Tache(parts[1], parts[0].equals("1")));
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erreur de chargement : " + ex.getMessage());
        }
    }
}
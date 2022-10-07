package edu.fbansept.applicationdesktop;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class Fenetre extends JFrame {

    protected boolean themeSombre = false;

    public Fenetre() {

        setSize(500,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panneau = new JPanel();

        setContentPane(panneau);

        //------------ BOUTON -------------------

        JButton bouton = new JButton("Fermer l'application");

        panneau.add(bouton);

        bouton.addActionListener(e ->  {

            //------ DIALOG ---------

            Object[] choix = {"Oui","Nope :("};
            int reponse = JOptionPane.showOptionDialog(
                    this,
                    "Voulez vous fermer l'application ?",
                    "Confirmer",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    choix,
                    choix[0]);

            if(reponse == JOptionPane.YES_OPTION){
                System.exit(0);
            }
        });


        JButton boutonChangeTheme = new JButton("Change theme");
        panneau.add(boutonChangeTheme);
        boutonChangeTheme.addActionListener(e ->  {
            try {
                if(themeSombre) {
                    themeSombre = false;
                    UIManager.setLookAndFeel( new FlatLightLaf());
                } else {
                    themeSombre = true;
                    UIManager.setLookAndFeel( new FlatDarculaLaf());
                }
                SwingUtilities.updateComponentTreeUI(this);
            } catch( Exception ex ) {
                System.err.println( "Failed to initialize LaF" );
            }
        });

        //-------- COMBOBOX --------------

        String[] listeCivilite = {"M.","Me.","Mlle.", "Non précisé"};
        JComboBox<Object> selectCivilite = new JComboBox<>(listeCivilite);
        panneau.add(selectCivilite);

        selectCivilite.addActionListener(e -> {
            JComboBox comboBox = (JComboBox) e.getSource();
            System.out.println(comboBox.getSelectedItem());
        });

        //---- autre exemple -----

        Utilisateur[] utilisateurs = {
                null,
                new Utilisateur("BANSEPT", "Franck"),
                new Utilisateur("SNOW", "Jon"),
                new Utilisateur("SMITH", "Steeve")
        };
        JComboBox<Utilisateur> selectUtilisateur = new JComboBox<>(utilisateurs);

        //---------- customiser le rendu de la liste déroulante -------------
        selectUtilisateur.setRenderer(
                new DefaultListCellRenderer(){

                    @Override
                    public Component getListCellRendererComponent(
                            final JList<?> list,
                            final Object value,
                            final int index,
                            final boolean isSelected,
                            final boolean cellHasFocus) {
                        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                        Utilisateur utilisateur = (Utilisateur) value;
                        if(utilisateur != null) {
                            setText(utilisateur.getPrenom() + " " + utilisateur.getNom());
                        } else {
                            setText("Aucun");
                        }
//                        if(isSelected) {
//                            setBackground(Color.GREEN);
//                        } else {
//                            setBackground(Color.RED);
//                        }
                        return this;
                    }
                }
        );

        panneau.add(selectUtilisateur);

        //------ bouton du formulaire -----

        JButton boutonFormulaire = new JButton("Envoyer");
        boutonFormulaire.addActionListener(e -> {

            if(selectUtilisateur.getSelectedItem() != null) {
                Utilisateur utilisateur =
                        (Utilisateur)selectUtilisateur.getSelectedItem();

                System.out.println(
                        selectCivilite.getSelectedItem() + utilisateur.getNom()
                );
            }

        });

        panneau.add(boutonFormulaire);

        setVisible(true);
    }

    public static void main(String[] args) {
        FlatLightLaf.setup();
        //FlatDarculaLaf.setup();
        new Fenetre();
    }
}

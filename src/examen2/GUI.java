/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author royum
 */
public class GUI {

    private JavaMail javaMail;

    public GUI(JavaMail javaMail) {
        this.javaMail = javaMail;
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Sistema de Correos Electronicos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 1));

        JButton createEmailButton = new JButton("Crear Email");
        JButton inboxButton = new JButton("Bandeja de Entrada");
        JButton logoutButton = new JButton("Cerrar Sesion");

        createEmailButton.addActionListener(e -> showCreateEmailDialog());
        inboxButton.addActionListener(e -> showInboxPanel(frame));

        logoutButton.addActionListener(e -> logout(frame));

        frame.add(createEmailButton);
        frame.add(inboxButton);
        frame.add(logoutButton);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showCreateEmailDialog() {
        JTextField toField = new JTextField(); // Destinatario
        JTextField subjectField = new JTextField(); // Asunto
        JTextArea contentArea = new JTextArea(5, 20); // Contenido
        Object[] message = {
            "Para (Destinatario):", toField,
            "Asunto:", subjectField,
            "Contenido:", new JScrollPane(contentArea)
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Crear Email", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String to = toField.getText();
            String subject = subjectField.getText();
            String content = contentArea.getText();

            if (to.isBlank() || subject.isBlank() || content.isBlank()) {
                JOptionPane.showMessageDialog(null, "Rellene todos los campos", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                javaMail.CreateEmail(to, subject, content);
            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void showInboxPanel(JFrame mainFrame) {
        JFrame inboxFrame = new JFrame("Bandeja de Entrada");
        inboxFrame.setSize(500, 400);
        inboxFrame.setLayout(new BorderLayout());

        DefaultListModel<String> emailListModel = new DefaultListModel<>();
        ArrayList<EmailNodo> emails = new ArrayList<>();

        EmailNodo current = javaMail.getCurrent().primerEmail;
        int index = 1;
        while (current != null) {
            emails.add(current);
            emailListModel.addElement(index + " - " + current.emisor + " - " + current.asunto + " - " + (current.leido ? "Leido" : "No leido"));
            current = current.siguiente;
            index++;
        }

        JList<String> emailList = new JList<>(emailListModel);
        inboxFrame.add(new JScrollPane(emailList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton readButton = new JButton("Leer Email");
        JButton backButton = new JButton("Regresar");

        readButton.addActionListener(e -> {
            int selectedIndex = emailList.getSelectedIndex();
            if (selectedIndex != -1) {
                try {
                    javaMail.Read(selectedIndex + 1);
                    EmailNodo updated = emails.get(selectedIndex);
                    emailListModel.set(selectedIndex, (selectedIndex + 1) + " - " + updated.emisor + " - " + updated.asunto + " - " + (updated.leido ? "Leido" : "No leido"));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un correo para leer.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            inboxFrame.dispose();
            mainFrame.setVisible(true);
        });

        buttonPanel.add(readButton);
        buttonPanel.add(backButton);
        inboxFrame.add(buttonPanel, BorderLayout.SOUTH);

        mainFrame.setVisible(false);
        inboxFrame.setLocationRelativeTo(null);
        inboxFrame.setVisible(true);
    }


    private void logout(JFrame mainFrame) {
        javaMail.logout();
        mainFrame.dispose();
       
        
    }

}

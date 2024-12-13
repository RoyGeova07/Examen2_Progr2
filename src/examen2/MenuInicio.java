/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

/**
 *
 * @author royum
 */
public class MenuInicio {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MenuInicio().createAndShowMenu();
        });
    }

    private JavaMail javaMail;

    public MenuInicio() {
        try {
            javaMail = new JavaMail();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error inicializando el sistema: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createAndShowMenu() {
        JFrame frame = new JFrame("Inicio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(2, 1));

        JButton crearUsuarioButton = new JButton("Crear Usuario");
        JButton loginButton = new JButton("Iniciar Sesion");

        crearUsuarioButton.addActionListener(e -> new CrearUsuario(javaMail).mostrar());
        loginButton.addActionListener(e -> new Login(javaMail).mostrar());

        frame.add(crearUsuarioButton);
        frame.add(loginButton);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
    }
}

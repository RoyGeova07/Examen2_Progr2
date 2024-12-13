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
public class Login {
    
    private JavaMail javaMail;

    public Login(JavaMail javaMail) {
        
        this.javaMail = javaMail;
        
    }

   public void mostrar() {
    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();

    Object[] message = {
            "Username:", usernameField,
            "Password:", passwordField
    };

    int option = JOptionPane.showConfirmDialog(null, message, "Iniciar Sesión", JOptionPane.OK_CANCEL_OPTION);
    if (option == JOptionPane.OK_OPTION) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        try {
            if (javaMail.login(username, password)) {
                JOptionPane.showMessageDialog(null, "Login exitoso", "Información", JOptionPane.INFORMATION_MESSAGE);
                GUI m = new GUI(javaMail);
                m.createAndShowGUI();
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al iniciar sesión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
   }
}

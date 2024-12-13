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
public class CrearUsuario {
    
    private JavaMail javaMail;

    public CrearUsuario(JavaMail javaMail) {
        this.javaMail = javaMail;
    }

    public void mostrar() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        

        Object[] message = {
                "Username:", usernameField,
                "Password:", passwordField
        };
        
        

        int option = JOptionPane.showConfirmDialog(null, message, "Crear Usuario", JOptionPane.OK_CANCEL_OPTION);
        
        
        
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            
            
            
            if(password.isEmpty()){
                
                JOptionPane.showMessageDialog(null, "ERROR INGRESE LA CONTRASEÑA");
                return;
                
            }
            
            try {
                javaMail.CrearAccount(username, password);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al crear usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

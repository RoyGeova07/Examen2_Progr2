/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2;

import java.io.*;
import java.util.NoSuchElementException;
import javax.swing.*;

/**
 *
 * @author royum
 */
public class JavaMail {

    private RandomAccessFile UsuariosArchi;
    private CurrentUser current;
    private File Archivitos;

    public JavaMail() throws FileNotFoundException {

        this.Archivitos = new File("Registrados");
        this.UsuariosArchi = new RandomAccessFile("usuarios.eml", "rw");
        this.current = null;

        if (!Archivitos.exists()) {
            Archivitos.mkdir();
        }
    }

    public boolean login(String Username, String password) throws IOException {
        UsuariosArchi.seek(0);
        boolean found = false;

        while (UsuariosArchi.getFilePointer() < UsuariosArchi.length()) {
            String nombre = UsuariosArchi.readUTF();
            String contrasena = UsuariosArchi.readUTF();

            if (nombre.equals(Username)) {
                found = true;
                if (contrasena.equals(password)) {
                    File userFile = new File("Registrados/" + Username + ".eml");

                    if (!userFile.exists()) {
                        userFile.createNewFile();
                    }

                    current = new CurrentUser(nombre);
                    current.loadFromFile();
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta.");
                    return false;
                }
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado. Se creara automaticamente.");
            CrearAccount(Username, password);
            return login(Username, password);
        }

        return false;
    }

    public void logout() {
        if (current != null) {
            current = null;

        } else {

        }
    }

    public void CrearAccount(String Username, String password) throws IOException {

        UsuariosArchi.seek(0);
        while (UsuariosArchi.getFilePointer() < UsuariosArchi.length()) {
            String nombre = UsuariosArchi.readUTF();
            UsuariosArchi.readUTF();

            if (nombre.equals(Username)) { // Comparar el nombre de usuario
                JOptionPane.showMessageDialog(null, "El nombre de usuario ya existe. Por favor, elija otro.");
                return;
            }
        }

        UsuariosArchi.seek(UsuariosArchi.length());
        UsuariosArchi.writeUTF(Username);
        UsuariosArchi.writeUTF(password);

        File userFile = new File("Registrados/" + Username + ".eml");
        if (!userFile.exists()) { // Crear el archivo de usuario si no existe
            userFile.createNewFile();
        }

        JOptionPane.showMessageDialog(null, "Cuenta creada exitosamente.");
    }

    public boolean userExist(String user) throws IOException {
        UsuariosArchi.seek(0);
        while (UsuariosArchi.getFilePointer() < UsuariosArchi.length()) {
            String nombre = UsuariosArchi.readUTF();
            UsuariosArchi.readUTF();
            if (nombre.equals(user)) {
                return true;
            }
        }
        return false;
    }

    public void CreateEmail(String From, String Subject, String Content) throws IOException {
        if (userExist(From)) {

            try {
                if (current == null) {
                    throw new NullPointerException();
                }
                // Guardar el email en el archivo del destinatario
                current.gotEmail(current.username, Subject, Content, From);
                JOptionPane.showMessageDialog(null, "Correo enviado exitosamente a " + From);
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Debe iniciar sesión primero.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "El usuario no existe");
        }
    }

    public void Inbox() {

        try {
            if (current == null) {
                throw new NullPointerException();
            }
            JOptionPane.showMessageDialog(null, "Correo: " + current.username + "@javamail.org");
            current.inbox();
        } catch (NullPointerException e) {

        }
    }

    public void Read(int pos) {

        try {
            if (current == null) {
                throw new NullPointerException();
            }
            current.readEmail(pos);
        } catch (NullPointerException e) {
            
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CurrentUser getCurrent() {
        return current;
    }
}

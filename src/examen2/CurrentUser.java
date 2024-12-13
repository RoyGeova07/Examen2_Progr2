/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2;

import java.io.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import javax.swing.JOptionPane;

/**
 *
 * @author royum
 */
public class CurrentUser {

    public String username;
    public RandomAccessFile emailFile;
    public EmailNodo primerEmail;

    public CurrentUser(String username) throws IOException {
        
        this.username = username;
        this.emailFile = new RandomAccessFile("Registrados/" + username + ".eml", "rw");
        this.primerEmail = null;

        
    }

    public void add(EmailNodo email) {
        
        if (primerEmail == null) {
            primerEmail = email;
        } else {
            EmailNodo apoyo = primerEmail;
            while (apoyo.siguiente != null) {
                apoyo = apoyo.siguiente;
            }
            apoyo.siguiente = email;
        }//else fin
        
    }

   public void loadFromFile() throws IOException {
        emailFile.seek(0);

        if (emailFile.length() == 0) {
            
            return;
        }

        primerEmail = null;
        while (emailFile.getFilePointer() < emailFile.length()) {
            try {
                long posicion = emailFile.getFilePointer();
                String encabezado = emailFile.readUTF(); 
                long fecha = emailFile.readLong();
                String quienEnvia = emailFile.readUTF();
                String asunto = emailFile.readUTF();
                String contenido = emailFile.readUTF();
                boolean leido = emailFile.readBoolean();

                EmailNodo email = new EmailNodo(posicion, quienEnvia, asunto, leido);
                add(email);
            } catch (EOFException e) {
                break; 
            }
        }
}
    public void inbox() {
        
        if (primerEmail == null) {
            JOptionPane.showMessageDialog(null, "SIN EMAILS");
           
            return;
        }
        EmailNodo apoyo = primerEmail;
        int pos = 1;
        int contador = 0;

        while (apoyo != null) {
            System.out.printf("%d - %s - %s - %s\n", pos, apoyo.emisor, apoyo.asunto, apoyo.leido ? "Leido" : "No leido");
            apoyo = apoyo.siguiente;
            pos++;
            contador++;
            
            
        }

        System.out.printf("Total de emails recibidos: %d\n", contador);
        JOptionPane.showMessageDialog(null, "Total de emails recibidos: %d\n" + contador);
        
    }

    public long gotEmail(String emisor, String asunto, String contenido, String destinatario) throws IOException {
        // Abrir el archivo del destinatario
        RandomAccessFile destinatarioFile = new RandomAccessFile("Registrados/" + destinatario + ".eml", "rw");
        destinatarioFile.seek(destinatarioFile.length());
        long pos = destinatarioFile.getFilePointer();

        destinatarioFile.writeUTF(emisor);
        destinatarioFile.writeUTF(asunto);
        destinatarioFile.writeUTF(contenido);
        destinatarioFile.writeUTF(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); // Fecha
        destinatarioFile.writeBoolean(false);

        destinatarioFile.close();

        // Crear nodo de correo para el destinatario en la bandeja
        EmailNodo nuevoEmail = new EmailNodo(pos, emisor, asunto, false);
        add(nuevoEmail);

        return pos;
    }

    public void readEmail(int pos) throws IOException {
        EmailNodo apoyo = primerEmail;
        int apoyoPos = 1;

        while (apoyo != null && apoyoPos < pos) {
            apoyo = apoyo.siguiente;
            apoyoPos++;
        }

        if (apoyo == null) {
            throw new NoSuchElementException("Email en la posición " + pos + " está vacío");
        }

        // Moverse a la posición inicial del email
        emailFile.seek(apoyo.posicion);

        // Leer los datos
        String emisor = emailFile.readUTF();
        String asunto = emailFile.readUTF();
        String contenido = emailFile.readUTF();
        String fecha = emailFile.readUTF();
        boolean leido = emailFile.readBoolean();

        // Mostrar datos del email
        System.out.printf("DE: %s\nAsunto: %s\nContenido: %s\nFecha: %s\n", emisor, asunto, contenido, fecha);

        // Actualizar a "leído" si no lo está
        if (!leido) {
            long posicionLeido = emailFile.getFilePointer() - 1; // Retrocede al booleano
            emailFile.seek(posicionLeido);
            emailFile.writeBoolean(true);
            apoyo.leido = true; // Actualizar el nodo
        }
    }
}

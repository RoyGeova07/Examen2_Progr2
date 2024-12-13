/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2;

/**
 *
 * @author royum
 */
public class EmailNodo {
    
   public long posicion;
    public String emisor;
    public String asunto;
    public boolean leido;
    public EmailNodo siguiente;
    
    
    public EmailNodo(long posicion, String emisor, String asunto, boolean leido){
        this.posicion=posicion;
        this.emisor=emisor;
        this.asunto=asunto;
        this.leido=leido;
        this.siguiente=null;
    }

    public long getPosicion() {
        return posicion;
    }

    public String getEmisor() {
        return emisor;
    }

    public String getAsunto() {
        return asunto;
    }

    public boolean isLeido() {
        return leido;
    }

    public EmailNodo getSiguiente() {
        return siguiente;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    public void setSiguiente(EmailNodo siguiente) {
        this.siguiente = siguiente;
    }

}
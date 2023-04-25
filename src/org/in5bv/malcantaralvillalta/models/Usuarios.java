

package org.in5bv.malcantaralvillalta.models;

/**
 *
 * @author Lionar Gerardy Villalta Barrientos.
 * @date 15 jul. 2022
 * @time 22:05:58
 * Codigo: IN5BV
 */

public class Usuarios {
    private String user;
    private String pass;
    private String nombre;
    private int rol_id;
    
    
    public Usuarios() {
        
    }
    
    public Usuarios(String user) {
        this.user = user;
    }
    
    public Usuarios(String user, String pass, String nombre, int rol_id) {
        this.user = user;
        this.pass = pass;
        this.nombre = nombre;
        this.rol_id = rol_id;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getNombre() {
        return nombre;
    }

    public int getRol_id() {
        return rol_id;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRol_id(int rol_id) {
        this.rol_id = rol_id;
    }

    @Override
    public String toString() {
        return "Usuarios{" + "user=" + user + ", pass=" + pass + ", nombre=" + nombre + ", rol_id=" + rol_id + '}';
    }
    
    
}

/***********************************************************
 //INGENIERO: Juan Camilo Peña Vahos
 //DESCRIPCIÓN: Clase que representa los datos del tatuador en firebase
 //FECHA INICIAL: 03/10/2016;
 //ULTIMA ACTUALIZACION: 03/10/2016;
 */

package com.raykgeneer.evilgeniuses.blueartstudio;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class NuevoTatuador {

    public String Nombre;
    public String Apodo;
    public String Origen;
    public String Estilos;
    public String Descripcion;
    public int Likes;


    public NuevoTatuador() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public NuevoTatuador(String Nombre, String Apodo, String Origen, String Estilos, String Descripcion, int Likes) {
        this.Nombre = Nombre;
        this.Apodo = Apodo;
        this.Origen = Origen;
        this.Estilos = Estilos;
        this.Descripcion = Descripcion;
        this.Likes = Likes;
    }

}

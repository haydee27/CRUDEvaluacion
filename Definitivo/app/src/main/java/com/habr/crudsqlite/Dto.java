package com.habr.crudsqlite;

import java.io.Serializable;

public class Dto implements Serializable {
    private int Codigo;
    private String Descripcion;
    private double precio;
    private int idcategoria;
    private String nombrecategoria;
    private int estadocategoria;

    public Dto() {
    }

    public Dto(int codigo, String descripcion, double precio, int idcategoria, String nombrecategoria, int estadocategoria) {
        Codigo = codigo;
        Descripcion = descripcion;
        this.precio = precio;
        this.idcategoria = idcategoria;
        this.nombrecategoria = nombrecategoria;
        this.estadocategoria = estadocategoria;
    }

    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int codigo) {
        Codigo = codigo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(int idcategoria) {
        this.idcategoria = idcategoria;
    }

    public String getNombrecategoria() {
        return nombrecategoria;
    }

    public void setNombrecategoria(String nombrecategoria) {
        this.nombrecategoria = nombrecategoria;
    }

    public int getEstadocategoria() {
        return estadocategoria;
    }

    public void setEstadocategoria(int estadocategoria) {
        this.estadocategoria = estadocategoria;
    }
}

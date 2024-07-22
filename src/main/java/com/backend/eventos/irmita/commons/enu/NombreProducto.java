package com.backend.eventos.irmita.commons.enu;

public enum NombreProducto {
    SILLASGRANDES("SillasGrandes","sg001",0,60),
    SILLASINFANTILES("SillasInfantiles","sg002",0,60),
    TABLONESGRANDES("TablonesGrandes","sg003",0,60),
    TABLONESPEQUENOS("TablonesPequeno","sg004",0,60);

private String nombre;
private String code;
private int status;
private int stock;
private

 NombreProducto(String nombre, String code, int status, int stock){
    this.nombre=nombre;
    this.code=code;
    this.stock=stock;
    this.status= status;
}

public static int getStockByNombre(String nombre){
    for(NombreProducto producto : NombreProducto.values()){
        if (producto.getNombre().equalsIgnoreCase(nombre)){
            return producto.getStock();
        }
    }
    return 0;

}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}

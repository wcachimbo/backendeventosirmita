package com.backend.eventos.irmita.commons.ENUM;

public enum Estado {
    CREADO("001"),
    PEDIENTE("002"),
    ENTREGADO("003"),
    CERRADO("004");


private String status;

private Estado(String status){
    this.status= status;
}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

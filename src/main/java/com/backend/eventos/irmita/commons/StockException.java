package com.backend.eventos.irmita.commons;

import java.util.Map;

public class StockException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public StockException(String message) {
        super(message);
    }
}
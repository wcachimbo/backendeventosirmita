package com.backend.eventos.irmita.service;

import com.backend.eventos.irmita.models.PedidoDAO;

import java.text.ParseException;

public interface PedidoService {
    boolean creaPedido(PedidoDAO pedido) throws ParseException;
}

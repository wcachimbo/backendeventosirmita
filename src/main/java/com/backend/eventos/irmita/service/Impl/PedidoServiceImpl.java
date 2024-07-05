package com.backend.eventos.irmita.service.Impl;

import com.backend.eventos.irmita.models.PedidoDAO;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class PedidoServiceImpl implements com.backend.eventos.irmita.service.PedidoService {

    @Override
    public boolean creaPedido(PedidoDAO pedido) throws ParseException {

        if (pedido.getNombreCliente().isEmpty()) {
            return false;
        } else if (pedido.getFecha().isEmpty()) {
            return false;
        } else if (pedido.getCelularCliente().isEmpty()) {
            return false;
        } else if (pedido.getDireccionCliente().isEmpty()) {
            return false;

        } else {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date dataFormateada = format.parse(pedido.getFecha());
            return true;

        }

    }

}

package com.backend.eventos.irmita.service.Impl;

import com.backend.eventos.irmita.commons.ENUM.Estado;
import com.backend.eventos.irmita.models.PedidoDAO;
import com.backend.eventos.irmita.models.ProductoDAO;
import com.backend.eventos.irmita.repository.PedidoRepo;
import com.backend.eventos.irmita.repository.ProductoRepo;
import com.backend.eventos.irmita.repository.StockRepo;
import com.backend.eventos.irmita.service.PedidoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class PedidoServiceImpl implements PedidoService {
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @Autowired
    PedidoRepo pedidoRepo;

    @Autowired
    ProductoRepo productoRepo;

    @Autowired
    StockRepo stockRepo;


    @Override
    @Transactional
    public boolean creaPedido(PedidoDAO pedido) {

      //  if (pedido.getNombreCliente().isEmpty()) {
      //      return false;
      //  } else if (pedido.getFecha().isEmpty()) {
      //      return false;
      //  } else if (pedido.getCelularCliente().isEmpty()) {
      //      return false;
      //  } else if (pedido.getDireccionCliente().isEmpty()) {
      //      return false;

      //  } else {
            LocalDate localDate = LocalDate.parse(pedido.getFecha(), formatter);
            int idpedido = pedidoRepo.pedidoID() + 1;
            String idfactura = String.format("FAC-" + "%0" + 5 + "d", Integer.valueOf(idpedido));

            if (validatePedido(localDate, pedido.getProductop())) {
                final String status = Estado.CREADO.getStatus();
                final int result = pedidoRepo.insertPedido(pedido.getCelularCliente(),
                        pedido.getDescripcion(), pedido.getDireccionCliente(), status, idfactura,
                        localDate, pedido.getNombreCliente());
                if (result > 0) {
                    for (ProductoDAO product : pedido.getProductop()) {
                        productoRepo.insertProducto(product.getCantidadprodcuto(), idfactura,
                                product.getNombreProducto(), product.getPrecio(), idpedido, localDate);
                        stockRepo.updateStock(product.getCantidadprodcuto(), product.getNombreProducto());
                    }
                    return true;
                }
            }

        return false;
    }

    private boolean validatePedido(LocalDate fecha, List<ProductoDAO> producto) {
        try {
            List<PedidoDAO> listpedido = pedidoRepo.pedidoActivo("001", fecha);
            if (!listpedido.isEmpty()) {
                for (ProductoDAO product : producto) {
                    final int sumInventaro = stockRepo.stockProduct(product.getNombreProducto());
                    final int sumProduct = productoRepo.disponibilidadProducto(product.getNombreProducto(), fecha);
                    if (sumProduct > sumInventaro) {
                        return false;
                    }
                }
            }

        } catch (Exception e) {
            log.info("Erro validando el metodo: validatePedido ", e.getMessage());
            return false;
        }

        return true;
    }
}

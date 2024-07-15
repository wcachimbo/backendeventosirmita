package com.backend.eventos.irmita.service.Impl;

import com.backend.eventos.irmita.commons.ENUM.Estado;
import com.backend.eventos.irmita.models.PedidoDAO;
import com.backend.eventos.irmita.models.ProductoDAO;
import com.backend.eventos.irmita.repository.ClienteRepo;
import com.backend.eventos.irmita.repository.PedidoRepo;
import com.backend.eventos.irmita.repository.ProductoRepo;
import com.backend.eventos.irmita.repository.StockRepo;
import com.backend.eventos.irmita.service.PedidoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @Autowired
    ClienteRepo clienteRepo;

    @Autowired
    WhatsAppServiceImpl whatsAppService;

    private final UUID key= UUID.randomUUID();


    @Override
    @Transactional
    public boolean creaPedido(PedidoDAO pedido) {

            LocalDate localDate = LocalDate.parse(pedido.getFecha(), formatter);
            String idfactura = "FAC-" + key;

            if (validatePedido(localDate, pedido.getProductop())) {
                final String status = Estado.CREADO.getStatus();
                final int result = pedidoRepo.insertPedido(key,pedido.getCelularCliente(),
                        pedido.getDescripcion(), pedido.getDireccionCliente(), status, idfactura,
                        localDate, pedido.getNombreCliente(), pedido.getTotal());
                if (result > 0) {
                    for (ProductoDAO product : pedido.getProductop()) {
                        productoRepo.insertProducto(key,product.getCantidadprodcuto(), idfactura,
                                product.getNombreProducto(), product.getPrecio(), key, localDate,
                                Double.valueOf(product.getCantidadprodcuto()* product.getPrecio())
                        );
                        stockRepo.updateStock(product.getCantidadprodcuto(), product.getNombreProducto());
                    }

                }if (clienteRepo.getClientes(pedido.getCelularCliente()).isEmpty()){
                    clienteRepo.insertClienteo(key, pedido.getCelularCliente(),pedido.getDireccionCliente(),pedido.getNombreCliente());

                }
                whatsAppService.sendWhatsAppMessage(pedido.getCelularCliente(), pedido.getDescripcion());
                return true;
            }

        return false;
    }

    private boolean validatePedido(LocalDate fecha, Set<ProductoDAO> producto) {
        try {
            Map<String,Object> response = new HashMap<>();
            List<PedidoDAO> listpedido = pedidoRepo.pedidoActivo("001", fecha);
            if (!listpedido.isEmpty()) {
                for (ProductoDAO product : producto) {
                    final int sumInventaro = stockRepo.stockProduct(product.getNombreProducto());
                    final int sumProduct = productoRepo.disponibilidadProducto(product.getNombreProducto(), fecha);
                    if (sumProduct > sumInventaro) {
                        response.put(product.getNombreProducto()+" Solo hay disponible", sumInventaro);
                         new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
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

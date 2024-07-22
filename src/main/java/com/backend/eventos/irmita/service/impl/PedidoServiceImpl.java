package com.backend.eventos.irmita.service.impl;

import com.backend.eventos.irmita.commons.BadRequestException;
import com.backend.eventos.irmita.commons.enu.Estado;
import com.backend.eventos.irmita.commons.enu.NombreProducto;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class PedidoServiceImpl implements PedidoService {
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    final Map<String, Object> response = new HashMap<>();
    @Autowired
    PedidoRepo pedidoRepo;

    @Autowired
    ProductoRepo productoRepo;

    @Autowired
    StockRepo stockRepo;

    @Autowired
    ClienteRepo clienteRepo;


    @Override
    @Transactional
    public boolean creaPedido(PedidoDAO pedido) {

        LocalDate localDate = LocalDate.parse(pedido.getFecha(), formatter);
        validatePedido(localDate, pedido.getProductop());

        UUID key = UUID.randomUUID();
        String idfactura = "FAC-" + key;
        final String status = Estado.CREADO.getStatus();
        if (clienteRepo.getClientes(pedido.getCelularCliente()).isEmpty()) {
            clienteRepo.insertClienteo(key, pedido.getCelularCliente(), pedido.getDireccionCliente(), pedido.getNombreCliente());
        }
        final int result = pedidoRepo.insertPedido(key, pedido.getCelularCliente(), pedido.getDescripcion(), pedido.getDireccionCliente(), status, idfactura, localDate, pedido.getNombreCliente(), pedido.getTotal());
        if (result > 0) {
            for (ProductoDAO product : pedido.getProductop()) {
                productoRepo.insertProducto(UUID.randomUUID(), product.getCantidadprodcuto(), idfactura, product.getNombreProducto(), product.getPrecio(), key, localDate, Double.valueOf(product.getCantidadprodcuto() * product.getPrecio()));
            }

        }
        if (clienteRepo.getClientes(pedido.getCelularCliente()).isEmpty()) {
            clienteRepo.insertClienteo(key, pedido.getCelularCliente(), pedido.getDireccionCliente(), pedido.getNombreCliente());

        }
        return true;
    }

    private void validatePedido(LocalDate fecha, Set<ProductoDAO> producto) {
        try {

            List<PedidoDAO> listpedido = pedidoRepo.pedidoActivo("001", fecha);
            if (!listpedido.isEmpty()) {
                for (ProductoDAO product : producto) {
                    final int sumInventaro = NombreProducto.getStockByNombre(product.getNombreProducto());
                    final int sumProduct = productoRepo.disponibilidadProducto(product.getNombreProducto(), fecha);
                    final int sumPedido = product.getCantidadprodcuto();
                    if ((sumProduct + sumPedido) > sumInventaro) {
                        log.warn("No hay suficiente producto disponible de sillas ".concat(product.getNombreProducto()));
                        String errorMessage = product.getNombreProducto().concat(" Solo hay disponible: ").concat(String.valueOf(sumInventaro - sumProduct));
                        throw new BadRequestException(HttpStatus.BAD_REQUEST, errorMessage);
                    }
                }
            }

        } catch (Exception e) {
            log.error("Erro validando el metodo: validatePedido ", e.getMessage());
            throw new BadRequestException(HttpStatus.INTERNAL_SERVER_ERROR, "Error en el metodo: validatePedido".concat(e.getMessage()));
        }

    }
}

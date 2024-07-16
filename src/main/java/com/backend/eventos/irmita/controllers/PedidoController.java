package com.backend.eventos.irmita.controllers;


import com.backend.eventos.irmita.commons.BadRequestException;
import com.backend.eventos.irmita.models.PedidoDAO;
import com.backend.eventos.irmita.service.Impl.WhatsAppServiceImpl;
import com.backend.eventos.irmita.service.PedidoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"},methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT } )
@RestController
@Slf4j
public class PedidoController {

    private final PedidoService pedidoService;
    private final WhatsAppServiceImpl whatsAppService;

    public PedidoController(PedidoService pedidoService, WhatsAppServiceImpl whatsAppService) {
        this.pedidoService = pedidoService;
        this.whatsAppService = whatsAppService;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder){
        binder.addValidators();
    }


    @PostMapping("/pedido")
    public ResponseEntity<?> pedido(@Valid @RequestBody PedidoDAO pedido, BindingResult result) throws ParseException {
        Map<String, Object> response = new HashMap<>();
        this.validate(pedido);


        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        } else {
            pedidoService.creaPedido(pedido);
            whatsAppService.sendWhatsAppMessage("+3185124103", String.valueOf(pedido));
            return new ResponseEntity<PedidoDAO>(pedido, HttpStatus.OK);
        }

    }

    private void validate(PedidoDAO valid)  {
        LocalDate dateTime = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if (!(Date.parse(valid.getFecha()) >= Date.parse(dateTime.format(formatter)))) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Fecha invalidad, La fecha debe no puede esta vencida");

        }
    }
}

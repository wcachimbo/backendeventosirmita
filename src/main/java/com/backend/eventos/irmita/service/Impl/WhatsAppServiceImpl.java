package com.backend.eventos.irmita.service.Impl;




import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
    public class WhatsAppServiceImpl {
        private final String TWILIO_ACCOUNT_SID = "your_twilio_account_sid";
        private final String TWILIO_AUTH_TOKEN = "your_twilio_auth_token";
        private final String TWILIO_PHONE_NUMBER = "your_twilio_phone_number"; // Debe ser un número de WhatsApp sandbox

        public WhatsAppServiceImpl() {
            Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
        }

        public void sendWhatsAppMessage(String to, String message) {
            Message.creator(
                    new PhoneNumber("whatsapp:" + to),
                    new PhoneNumber("whatsapp:" + TWILIO_PHONE_NUMBER),
                    message
            ).create();
        }
    }
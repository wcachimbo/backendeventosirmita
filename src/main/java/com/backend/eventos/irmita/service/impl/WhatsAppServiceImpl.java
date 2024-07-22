package com.backend.eventos.irmita.service.impl;




import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
    public class WhatsAppServiceImpl {
    private final String TWILIO_ACCOUNT_SID = "ACb07e47d4cc83a36ba2e78467d47f9314";
    private final String TWILIO_AUTH_TOKEN = "8bae035907b1b6a95d96c14ff34d7f0e";
    private final String TWILIO_PHONE_NUMBER = "whatsapp:+14155238886"; // Debe ser un número de WhatsApp sandbox con el prefijo "whatsapp:"

    public WhatsAppServiceImpl() {
        Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
    }

    public void sendWhatsAppMessage(String to, String message) {
        try {
            Message.creator(
                    new PhoneNumber("whatsapp:+573185124103"),
                    new PhoneNumber(TWILIO_PHONE_NUMBER),
                    message
            ).create();
            System.out.println("Message sent successfully to " + to);
        } catch (Exception e) {
            System.err.println("Failed to send message: " + e.getMessage());
        }
    }
}
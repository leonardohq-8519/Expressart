package org.project.expressart.TicketSoporte.listeners;

import org.project.expressart.TicketSoporte.events.TicketCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TicketSupportListener {
    @Async
    @EventListener
    public void onTicketCreated(TicketCreatedEvent event) {
        System.out.println("ASYNCHRONOUS LOG: Alert sent to moderators for ticket ID: " + event.id());
    }
}

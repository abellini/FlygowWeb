package br.com.flygonow.websocket.service.impl;

import br.com.flygonow.entities.AttendantAlert;
import br.com.flygonow.enums.WebsocketClientEndpointsEnum;
import br.com.flygonow.websocket.service.NotifyWebClientsService;
import br.com.flygonow.websocket.util.JSONWebsocketView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by tiago on 10/07/15.
 */
@Service
public class NotifyWebClientsServiceImpl implements NotifyWebClientsService {

    @Autowired
    private SimpMessagingTemplate template;

    @Override
    public void sendWebAlertToAttendant(AttendantAlert alert){
        this.template.convertAndSend(
                WebsocketClientEndpointsEnum.ATTENDANT_ALERTS.getPath(),
                JSONWebsocketView.fromAttendantAlert(alert)
        );
    }

    @Override
    public void sendWebAlertsToNewOrders(){
        this.template.convertAndSend(
                WebsocketClientEndpointsEnum.NEW_ORDER.getPath(), ""
        );
    }

    @Override
    public void sendWebAlertToAttendantTest(){
        String text = "Novo alerta para você!";
        this.template.convertAndSend(WebsocketClientEndpointsEnum.ATTENDANT_ALERTS.getPath(), text);
    }
}

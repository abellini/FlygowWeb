package br.com.flygonow.websocket.service;

import br.com.flygonow.entities.AttendantAlert;

/**
 * Created by tiago on 10/07/15.
 */
public interface NotifyWebClientsService {
    void sendWebAlertToAttendant(AttendantAlert alert);

    void sendWebAlertsToNewOrders();

    void sendWebAlertToAttendantTest();
}

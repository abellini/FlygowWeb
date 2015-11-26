package br.com.flygonow.websocket.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by tiago on 08/07/15.
 */
@Controller
public class WebsocketControllerExample {

    @Autowired
    private SimpMessagingTemplate template;

    @RequestMapping(value="/send-to-client", method = RequestMethod.GET)
    @ResponseBody
    public void sendToClient() throws Exception {
        String text = "MESSAGE FROM SERVER -->> [" + new Date().getTime() + "]";
        this.template.convertAndSend("/topic/showResult", text);
    }
}

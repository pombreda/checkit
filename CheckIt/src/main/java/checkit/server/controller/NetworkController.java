/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.controller;

import checkit.server.domain.Result;
import checkit.server.service.ResultService;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Dodo
 */
@Controller
public class NetworkController {
    @Autowired
    private ResultService resultService;
    
    @RequestMapping(value = "/postResult", method = RequestMethod.POST)
    @ResponseBody
    public void post(HttpServletRequest request) throws IOException {
        final String userIpAddress = request.getRemoteAddr();
        //TODO - control against DB later. This solution is only because of localhost (I cant get anything else but 127.0.0.1 - imposible to send anything)
        if (userIpAddress.equals("127.0.0.1")) {
            
            try (ObjectInputStream in = new ObjectInputStream(request.getInputStream())) {
                try {
                    Result result = (Result) in.readObject();
                    result.setAgentId(1); //TODO - out of localhost, set according to DB
                    resultService.createResult(result);
                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
                }
            }    
        }
    } 

}

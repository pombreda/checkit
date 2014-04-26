/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.agent.controller;

import checkit.agent.service.ServerService;
import checkit.agent.service.TestService;
import checkit.server.domain.Test;
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
public class CreatingController {
    @Autowired
    private ServerService serverService;
    
    @Autowired
    private TestService testService;
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public void post(HttpServletRequest request) throws IOException {
        final String userIpAddress = request.getRemoteAddr();
        if (serverService.getServerByIp(userIpAddress) != null) {

            try (ObjectInputStream in = new ObjectInputStream(request.getInputStream())) {
                try {
                    Test test = (Test) in.readObject();
                    testService.createTest(test);
                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
                }
            }    
        }
    } 
}

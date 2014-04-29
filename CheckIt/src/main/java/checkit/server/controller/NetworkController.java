/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.controller;

import checkit.plugin.service.PluginReportService;
import checkit.server.domain.Agent;
import checkit.server.domain.ContactDetail;
import checkit.server.domain.Result;
import checkit.server.domain.Test;
import checkit.server.service.AgentService;
import checkit.server.service.ContactDetailService;
import checkit.server.service.ResultService;
import checkit.server.service.TestService;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
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
    
    @Autowired
    private AgentService agentService;
    
    @Autowired
    private TestService testService;
    
    @Autowired
    private PluginReportService pluginService;
    
    @Autowired
    private ContactDetailService contactDetailService;
    
    @RequestMapping(value = "/postResult", method = RequestMethod.POST)
    @ResponseBody
    public void post(HttpServletRequest request) throws IOException {
        final String userIpAddress = request.getRemoteAddr();
        Agent agent = agentService.getAgentByIp(userIpAddress);
        
        if (agent != null) {
            try (ObjectInputStream in = new ObjectInputStream(request.getInputStream())) {
                try {
                    Result result = (Result) in.readObject();
                    result.setAgentId(agent.getAgentId());
                    Test test = testService.getTestById(result.getTestId());

                    if ((result.getStatus().equals("U") != test.isOk() || !test.isChecked()) && test.isEnabled()) {
                        resultService.createResult(result);

                        List<ContactDetail> contactDetailList;
                        if (!result.getStatus().equals("U")) {
//                            contactDetailList = contactDetailService.getContactDetailListByTestIdWhereDownIsActive(test.getTestId());
//                            pluginService.reportDown(contactDetailList, test.getTitle());
                            System.out.println("Sending down...");
                        } else if (test.isChecked()) {
//                            contactDetailList = contactDetailService.getContactDetailListByTestIdWhereUpIsActive(test.getTestId());
//                            pluginService.reportUp(contactDetailList, test.getTitle());
                            System.out.println("Sending up...");
                        }

                        test.setOk(result.getStatus().equals("U"));
                        test.setChecked(true);
                        testService.updateTest(test);
                    }
                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
                }
            }    
        }
    } 

}

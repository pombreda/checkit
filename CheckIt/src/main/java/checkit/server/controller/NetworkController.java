package checkit.server.controller;

import checkit.plugin.service.PluginReportService;
import checkit.server.domain.Agent;
import checkit.server.domain.ContactDetail;
import checkit.server.domain.Result;
import checkit.server.domain.Check;
import checkit.server.service.AgentService;
import checkit.server.service.ContactDetailService;
import checkit.server.service.ResultService;
import checkit.server.service.CheckService;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NetworkController {
    @Autowired
    private ResultService resultService;
    
    @Autowired
    private AgentService agentService;
    
    @Autowired
    private CheckService checkService;
    
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
                    Check check = checkService.getCheckById(result.getCheckId());
                    
                    if (check == null) {
                        return;
                    }

                    if ((result.getStatus().equals("U") != check.isOk() || !check.isChecked()) && check.isEnabled()) {
                        resultService.createResult(result);

                        List<ContactDetail> contactDetailList;
                        if (!result.getStatus().equals("U")) {
//                            contactDetailList = contactDetailService.getContactDetailListByCheckIdWhereDownIsActive(check.getCheckId());
//                            pluginService.reportDown(contactDetailList, check.getTitle());
                            System.out.println("Sending down...");
                        } else if (check.isChecked()) {
//                            contactDetailList = contactDetailService.getContactDetailListByCheckIdWhereUpIsActive(check.getCheckId());
//                            pluginService.reportUp(contactDetailList, check.getTitle());
                            System.out.println("Sending up...");
                        }

                        check.setOk(result.getStatus().equals("U"));
                        check.setChecked(true);
                        checkService.updateCheck(check);
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(NetworkController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }    
        }
    } 

}

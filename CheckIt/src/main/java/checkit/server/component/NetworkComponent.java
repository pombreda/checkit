/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * Component for receiving agents data.
 * Although it is annotated as Controller, it is Component. It has Controller annotation only because of @RequestMapping, which can be only in @Controller
 */

package checkit.server.component;

import checkit.plugin.component.PluginReportComponent;
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
public class NetworkComponent {
    @Autowired
    private ResultService resultService;
    
    @Autowired
    private AgentService agentService;
    
    @Autowired
    private CheckService checkService;
    
    @Autowired
    private PluginReportComponent pluginService;
    
    @Autowired
    private ContactDetailService contactDetailService;
    
    /**
     * Controller for posting agents data
     * Receive result data from agents, check agents ip and save result if everything is ok
     *
     * @param request Agents request connection
     * @throws java.io.IOException
     */
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
                            contactDetailList = contactDetailService.getContactDetailListByCheckIdWhereDownIsActive(check.getCheckId());
                            pluginService.reportDown(contactDetailList, check.getTitle());
                        } else if (check.isChecked()) {
                            contactDetailList = contactDetailService.getContactDetailListByCheckIdWhereUpIsActive(check.getCheckId());
                            pluginService.reportUp(contactDetailList, check.getTitle());
                        }

                        check.setOk(result.getStatus().equals("U"));
                        check.setChecked(true);
                        checkService.updateCheck(check);
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(NetworkComponent.class.getName()).log(Level.SEVERE, null, ex);
                }
            }    
        }
    } 

}

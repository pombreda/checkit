/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * Controller for updating task.
 */

package checkit.agent.controller;

import checkit.agent.service.ServerService;
import checkit.agent.service.CheckService;
import checkit.server.domain.Check;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UpdateController {
    @Autowired
    private ServerService serverService;
    
    @Autowired
    private CheckService checkService;
    
    /**
     * Controller for updating task.
     * Receive data from servers, check servers ip and update task if everything is ok
     *
     * @param request Servers request connection
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void post(HttpServletRequest request) throws IOException {
        final String userIpAddress = request.getRemoteAddr();
        if (serverService.getServerByIp(userIpAddress) != null) {

            try (ObjectInputStream in = new ObjectInputStream(request.getInputStream())) {
                try {
                    Check check = (Check) in.readObject();
                    checkService.updateCheck(check);
                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
                }
            }    
        }
    } 
}

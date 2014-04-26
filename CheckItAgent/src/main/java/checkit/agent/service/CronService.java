/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.agent.service;

import checkit.server.domain.Test;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dodo
 */
@Service
public class CronService {
    @Autowired
    private TestService testService;
    
    private int timeCounter = 15;
    
    private void setTimeCounter() {
        timeCounter += 15;
        if (timeCounter > 3600) {
            timeCounter = 15;
        }
    }
    
    @Scheduled(fixedDelay = 15000)
    public void Cron() {
        List<Test> testList = testService.getTestList();
        
        for (Test test : testList) {
            if (timeCounter % test.getInterval() == 0) {
                //run "test" - zavolani filename, ziskani atributu a poslani do funkce run, ktera vraci object[] - responseCode a elapsedTime
            }
        }

        setTimeCounter();
    }
}

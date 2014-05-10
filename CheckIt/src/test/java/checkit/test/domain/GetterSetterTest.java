/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * Testing getters and setters of domain objects.
 */

package checkit.test.domain;

import checkit.server.domain.Agent;
import checkit.server.domain.AgentQueue;
import checkit.server.domain.Check;
import checkit.server.domain.Checking;
import checkit.server.domain.Contact;
import checkit.server.domain.ContactDetail;
import checkit.server.domain.Reporting;
import checkit.server.domain.Result;
import checkit.server.domain.User;
import checkit.server.domain.UserActivation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class GetterSetterTest {
    
    private final Agent agent = new Agent();
    private final AgentQueue agentQueue = new AgentQueue();
    private final Check check = new Check();
    private final Checking checking = new Checking();
    private final Contact contact = new Contact();
    private final ContactDetail contactDetail = new ContactDetail();
    private final Reporting reporting = new Reporting();
    private final Result result = new Result();
    private final User user = new User();
    private final UserActivation userActivation = new UserActivation();
    private final Date date = new Date();
        

    
    public GetterSetterTest() {
    }
    
    @Before
    public void setUp() {
        agent.setAgentId(1);
        agent.setEnabled(true);
        agent.setIp("127.0.0.1");
        agent.setLocation("Location");
        agent.setPostAddress("http://address/");
        
        agentQueue.setAgentId(1);
        agentQueue.setAgentQueueId(2);
        agentQueue.setCheckId(3);
        agentQueue.setQuery("create");
        
        check.setCheckId(1);
        check.setChecked(true);
        check.setData("{\"data\": \"data\"}");
        check.setEnabled(false);
        check.setFilename("HTTP");
        check.setInterval(15);
        check.setOk(true);
        check.setTitle("Test1");
        check.setUserId(2);
        
        checking.setAgentId(1);
        checking.setCheckId(2);
        checking.setUserId(3);
        
        contactDetail.setContactDetailId(1);
        contactDetail.setContactId(2);
        contactDetail.setData("{\"data\": \"data\"}");
        contactDetail.setDown(false);
        contactDetail.setFilename("Email");
        contactDetail.setRegular(true);
        contactDetail.setTitle("ContactDetail1");
        contactDetail.setUp(false);
        contactDetail.setUserId(3);
        
        List<ContactDetail> contactDetailList = new ArrayList<ContactDetail>();
        contactDetailList.add(contactDetail);
        
        contact.setContactDetail(contactDetailList);
        contact.setContactId(1);
        contact.setTitle("Contact1");
        contact.setUserId(2);
        
        reporting.setCheckId(1);
        reporting.setContactId(2);
        reporting.setUserId(3);
        
        result.setAgentId(1);
        result.setCheckId(2);
        result.setData("{\"data\": \"data\"}");
        result.setStatus("U");
        result.setTime(date.toString());
        
        user.setConfirmPassword("Password1");
        user.setCreated(date.toString());
        user.setEmail("email@example.com");
        user.setEnabled(true);
        user.setPassword("Password1");
        user.setUserId(1);
        user.setUserRoleId(2);
        user.setUsername("Username");
        
        userActivation.setEmail("email@example.com");
        userActivation.setHash("0ha1sh2");
        userActivation.setId(1);
    }
    
    @Test
    public void testAgent() {
        assertEquals(agent.getAgentId(), 1);
        assertTrue(agent.isEnabled());
        assertEquals(agent.getIp(), "127.0.0.1");
        assertEquals(agent.getLocation(), "Location");
        assertEquals(agent.getPostAddress(), "http://address/");
    }
        
    @Test
    public void testAgentQueue() {
        assertEquals(agentQueue.getAgentId(), 1);
        assertEquals(agentQueue.getAgentQueueId(), 2);
        assertEquals(agentQueue.getCheckId(), 3);
        assertEquals(agentQueue.getQuery(), "create");
    }
        
    @Test
    public void testCheck() {
        assertEquals(check.getCheckId(), 1);
        assertTrue(check.isChecked());
        assertEquals(check.getData(), "{\"data\": \"data\"}");
        assertFalse(check.isEnabled());
        assertEquals(check.getFilename(), "HTTP");
        assertEquals(check.getInterval(), 15);
        assertTrue(check.isOk());
        assertEquals(check.getTitle(), "Test1");
        assertEquals(check.getUserId(), 2);
    }
        
    @Test
    public void testChecking() {
        assertEquals(checking.getAgentId(), 1);
        assertEquals(checking.getCheckId(), 2);
        assertEquals(checking.getUserId(), 3);
    }
        
    @Test
    public void testContactDetail() {
        assertEquals(contactDetail.getContactDetailId(), 1);
        assertEquals(contactDetail.getContactId(),2);
        assertEquals(contactDetail.getData(), "{\"data\": \"data\"}");
        assertFalse(contactDetail.isDown());
        assertEquals(contactDetail.getFilename(), "Email");
        assertTrue(contactDetail.isRegular());
        assertEquals(contactDetail.getTitle(), "ContactDetail1");
        assertFalse(contactDetail.isUp());
        assertEquals(contactDetail.getUserId(), 3);
    }
        
    @Test
    public void testContact() {
        assertEquals(contact.getContactDetail().size(), 1);
        assertEquals(contact.getContactId(), 1);
        assertEquals(contact.getTitle(), "Contact1");
        assertEquals(contact.getUserId(), 2);
    }
        
    @Test
    public void testReporting() {
        assertEquals(reporting.getCheckId(), 1);
        assertEquals(reporting.getContactId(), 2);
        assertEquals(reporting.getUserId(), 3);
    }
        
    @Test
    public void testResult() {
        assertEquals(result.getAgentId(), 1);
        assertEquals(result.getCheckId(), 2);
        assertEquals(result.getData(), "{\"data\": \"data\"}");
        assertEquals(result.getStatus(), "U");
        assertEquals(result.getTime(), date.toString());
    }
        
    @Test
    public void testUser() {
        assertEquals(user.getConfirmPassword(), "Password1");
        assertEquals(user.getCreated(), date.toString());
        assertEquals(user.getEmail(), "email@example.com");
        assertTrue(user.isEnabled());
        assertEquals(user.getPassword(), "Password1");
        assertEquals(user.getUserId(), 1);
        assertEquals(user.getUserRoleId(), 2);
        assertEquals(user.getUsername(), "Username");
    }
        
    @Test
    public void testUserActivation() {
        assertEquals(userActivation.getEmail(), "email@example.com");
        assertEquals(userActivation.getHash(), "0ha1sh2");
        assertEquals(userActivation.getId(), 1);
    }
}

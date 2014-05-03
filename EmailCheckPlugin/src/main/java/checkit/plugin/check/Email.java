/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.plugin.check;

/**
 *
 * @author Dodo
 */
public class Email implements Check {

    @Override
    public Object getOptionsJSON() {
        return "{"
             +    "\"inputs\": ["
             +      "{"
             +        "\"id\": \"smtp\","
             +        "\"type\": \"checkbox\","
             +        "\"title\": \"Check SMTP instead of IMAP?\","
             +        "\"description\": \"By default is tested IMAP, check if you need to test SMTP instead.\""
             +      "},"
             +      "{"
             +        "\"id\": \"url\","
             +        "\"type\": \"text\","
             +        "\"title\": \"URL of smtp or imap server\","
             +        "\"description\": \"e.g. imap.domain.com\""
             +      "},"
             +      "{"
             +        "\"id\": \"port\","
             +        "\"type\": \"text\","
             +        "\"title\": \"Port\","
             +        "\"description\": \"e.g. for SMTP 587 or 465, for IMAP 993\""
             +      "},"
             +      "{"
             +        "\"id\": \"username\","
             +        "\"type\": \"text\","
             +        "\"title\": \"Email login\","
             +        "\"description\": \"\""
             +      "},"
             +      "{"
             +        "\"id\": \"password\","
             +        "\"type\": \"password\","
             +        "\"title\": \"Email password\","
             +        "\"description\": \"ATTENTION! Unfortunately, we have to save password in non-hash form, because we need to use your password to log in. Is strongly recommended to create a new user for testing.\""
             +      "}"
             +    "]"
             + "}";
    }

    @Override
    public Object getTableRequiredParamsName() {
        return (Object) null;
    }

    @Override
    public Object getTableRequiredHeaderTitle() {
        return (Object) null;
    }
    
}

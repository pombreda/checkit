package checkit.plugin.check;

public class FTP implements Check {

    @Override
    public Object getOptionsJSON() {
        return "{"
             +    "\"inputs\": ["
             +      "{"
             +        "\"id\": \"url\","
             +        "\"type\": \"text\","
             +        "\"title\": \"URL of ftp\","
             +        "\"description\": \"e.g. ftp.domain.com\""
             +      "},"
             +      "{"
             +        "\"id\": \"username\","
             +        "\"type\": \"text\","
             +        "\"title\": \"FTP login\","
             +        "\"description\": \"\""
             +      "},"
             +      "{"
             +        "\"id\": \"password\","
             +        "\"type\": \"password\","
             +        "\"title\": \"FTP password\","
             +        "\"description\": \"ATTENTION! Unfortunately, we have to save password in non-hash form, because we need to use your password to log in. Is strongly recommended to create a new user with only login privileges for testing.\""
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

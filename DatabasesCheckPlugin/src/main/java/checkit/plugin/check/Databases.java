package checkit.plugin.check;

public class Databases implements Check {

    @Override
    public Object getOptionsJSON() {
        return "{"
             +    "\"inputs\": ["
             +      "{"
             +        "\"id\": \"db\","
             +        "\"type\": \"select\","
             +        "\"title\": \"Database type\","
             +        "\"description\": \"Choose database type.\","
             +        "\"options\" : [\"MySQL\", \"PostgreSQL\", \"MSSQL\"]"
             +      "},"
             +      "{"
             +        "\"id\": \"url\","
             +        "\"type\": \"text\","
             +        "\"title\": \"URL of DB server\","
             +        "\"description\": \"e.g. db.domain.com\""
             +      "},"
             +      "{"
             +        "\"id\": \"username\","
             +        "\"type\": \"text\","
             +        "\"title\": \"DB login\","
             +        "\"description\": \"\""
             +      "},"
             +      "{"
             +        "\"id\": \"password\","
             +        "\"type\": \"password\","
             +        "\"title\": \"DB password\","
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

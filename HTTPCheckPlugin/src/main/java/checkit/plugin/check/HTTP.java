package checkit.plugin.check;

public class HTTP implements Check {

    @Override
    public Object getOptionsJSON() {
        return "{"
             +    "\"inputs\": ["
             +      "{"
             +        "\"id\": \"url\","
             +        "\"type\": \"text\","
             +        "\"title\": \"URL of page\","
             +        "\"description\": \"e.g. http://www.example.com/\""
             +      "},"
             +      "{"
             +        "\"id\": \"followRedirects\","
             +        "\"type\": \"checkbox\","
             +        "\"title\": \"Follow redirects on page?\","
             +        "\"description\": \"If final page runs on another url it is recommended to check this option.\""
             +      "}"
             +    "]"
             + "}";
    }

    @Override
    public Object getTableRequiredParamsName() {
        return new Object[] {"code"};
    }

    @Override
    public Object getTableRequiredHeaderTitle() {
        return new Object[] {"Response code"};
    }
    
}

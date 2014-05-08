package checkit.plugin.report;

public interface Report {
    public Object getOptionsJSON();
    public Object getCallRequiredParamsName();
    public void reportDown(String testTitle, Object[] params);
    public void reportUp(String testTitle, Object[] params);
    public void reportRegular(String testTitle, int numberOfDowns, long timeOfDowns, Object[] params);
}

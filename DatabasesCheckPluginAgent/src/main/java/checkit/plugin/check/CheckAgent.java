package checkit.plugin.check;

public interface CheckAgent {
    public Object getCallRequiredParamsName();
    public Object getResultParamsName();
    public Object run(Object[] params);
    public Object isItOk(Object[] params);
}

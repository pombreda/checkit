package checkit.server.service;

import checkit.server.domain.Checking;

public interface NetworkService {
    public void postCreatingToAgent(Checking checking);
    public void postDeletingToAgent(Checking checking);
    public void postUpdatingToAgent(Checking checking);
}

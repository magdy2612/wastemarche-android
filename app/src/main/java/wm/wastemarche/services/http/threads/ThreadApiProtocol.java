package wm.wastemarche.services.http.threads;

import java.util.List;

import wm.wastemarche.model.Thread;
import wm.wastemarche.services.http.ApiProtocol;

public interface ThreadApiProtocol extends ApiProtocol {

    void threadsLoaded(String page, List<Thread> threads);

    void threadLoaded(Thread thread);
}

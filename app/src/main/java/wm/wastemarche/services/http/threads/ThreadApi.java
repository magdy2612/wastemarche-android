package wm.wastemarche.services.http.threads;

import android.os.Handler;
import android.os.Message;

import org.json.JSONObject;

import java.util.List;

import wm.wastemarche.model.Thread;
import wm.wastemarche.services.http.ConnectionManager;
import wm.wastemarche.services.http.ErrorCode;
import wm.wastemarche.services.http.HttpConstants;
import wm.wastemarche.services.http.Response;

public class ThreadApi {

    private final ThreadApiProtocol delegate;

    public ThreadApi(final ThreadApiProtocol delegate) {
        this.delegate = delegate;
    }

    public void getThreads(final String page) {
        final String method = "/v1/threads?page=" + page;

        final ConnectionManager conn = new ConnectionManager();
        conn.makeGETRequest(method, HttpConstants.defaultHeaders(), new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                final JSONObject json = Response.parseResponse(msg, delegate);
                if( json == null ) {return; }

                final JSONObject data = Response.getPropertyObject("data", json);
                final JSONObject threads = Response.getPropertyObject("threads", data);
                final List<Thread> threadsList = Thread.fromArray(Response.getPropertyArray("data", threads));
                delegate.threadsLoaded(page, threadsList);
            }
        });
    }

    public void getThread(final String threadId) {
        final String method = "/v1/threads/" + threadId;

        final ConnectionManager conn = new ConnectionManager();
        conn.makeGETRequest(method, HttpConstants.defaultHeaders(), new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                final JSONObject json = Response.parseResponse(msg, delegate);
                if( json == null ) {return; }

                final JSONObject data = Response.getPropertyObject("data", json);
                final JSONObject threads = Response.getPropertyObject("threads", data);
                final List<Thread> threadsList = Thread.fromArray(Response.getPropertyArray("data", threads));
                if( threadsList.size() == 1 ) {
                    delegate.threadLoaded(threadsList.get(0));
                }
                else {
                    delegate.apiError(ErrorCode.OBJECT_NOT_FOUND);
                }
            }
        });
    }
}

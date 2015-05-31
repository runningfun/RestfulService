package runningfun.restfulservice;

import org.atmosphere.annotation.Broadcast;
import org.atmosphere.annotation.Suspend;
import runningfun.dto.Message;
import runningfun.dto.Response;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Date;


@Path("chat")
@Produces("application/json")
public class ResourceChat {

    @Suspend
    @GET
    public String suspend() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    broadcastTest();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread t = new Thread(runnable);
        t.start();

        return "hello";
    }

    @Broadcast(writeEntity = false)
    @POST
    public Response broadcast(Message message) {
        return new Response(message.author, message.message);
    }

    @Broadcast
    String broadcastTest() {
        String date = new Date().toString();
        System.out.println(date);
        return date;
    }

}

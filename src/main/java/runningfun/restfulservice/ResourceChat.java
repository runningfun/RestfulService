package runningfun.restfulservice;

import org.atmosphere.annotation.Broadcast;
import org.atmosphere.annotation.Suspend;
import runningfun.dto.Message;
import runningfun.dto.Response;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("chat")
@Produces("application/json")
public class ResourceChat {

    @Suspend
    @GET
    public String suspend() {
        return "hello";
    }

    @Broadcast(writeEntity = false)
    @POST
    public Response broadcast(Message message) {
        return new Response(message.author, message.message);
    }

}

package mansolsson.akkademydb.server;

import java.util.HashMap;
import java.util.Map;

import akka.actor.AbstractActor;
import akka.actor.Status;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import mansolsson.akkademydb.model.DeleteRequest;
import mansolsson.akkademydb.model.GetRequest;
import mansolsson.akkademydb.model.KeyNotFoundException;
import mansolsson.akkademydb.model.SetRequest;

public class AkkademyDb extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(this.context().system(), this);
    private final Map<String, Object> map = new HashMap<String, Object>();

    private AkkademyDb(){
        this.receive(ReceiveBuilder.
                match(GetRequest.class, this::doGetRequest).
                match(SetRequest.class, this::doSetRequest).
                match(DeleteRequest.class, this::doDeleteRequest).
                matchAny(this::doUnknownRequest).build()
        );
    }

    private void doGetRequest(final GetRequest getRequest) {
        this.log.info("Received Get request: {}", getRequest);
        final Object value = this.map.get(getRequest.getKey());
        final Object response;
        if (value != null) {
            response = value;
        } else {
            response = new Status.Failure(new KeyNotFoundException(getRequest.getKey()));
        }
        this.sender().tell(response, this.self());
    }

    private void doSetRequest(final SetRequest setRequest) {
        this.log.info("Received Set request: {}", setRequest);
        this.map.put(setRequest.getKey(), setRequest.getValue());
        this.sender().tell(new Status.Success(setRequest.getKey()), this.self());
    }

    private void doDeleteRequest(final DeleteRequest deleteRequest) {
        this.log.info("Received Delete request: {}", deleteRequest);
        this.map.remove(deleteRequest.getKey());
        this.sender().tell(new Status.Success(deleteRequest.getKey()), this.self());
    }

    private void doUnknownRequest(final Object unknownRequest) {
        this.sender().tell(new Status.Failure(new ClassNotFoundException()), this.self());
    }
}

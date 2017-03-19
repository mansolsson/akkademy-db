package mansolsson.akkademydb.server;

import akka.actor.ActorSystem;
import akka.actor.Props;

public class Main {
    public static void main(final String... args) {
        final ActorSystem system = ActorSystem.create("akkademy");
        system.actorOf(Props.create(AkkademyDb.class), "akkademy-db");
    }
}

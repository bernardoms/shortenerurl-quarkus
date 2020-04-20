package com.bernardoms.integration;

import com.bernardoms.dto.URLShortenerDTO;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.internal.MongoClientImpl;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongoCmdOptionsBuilder;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.bson.Document;

import java.util.Collections;
import java.util.Map;

import static org.jboss.resteasy.resteasy_jaxrs.i18n.LogMessages.LOGGER;

public class MongoEmbedded implements QuarkusTestResourceLifecycleManager {
    private static MongodExecutable MONGO;

    @Override
    public Map<String, String> start()  {
        try {
            int port = 27017;
            LOGGER.infof("Starting Mongo %s on port %s", port);
            IMongodConfig config = new MongodConfigBuilder()
                    .version(Version.Main.PRODUCTION)
                    .cmdOptions(new MongoCmdOptionsBuilder().useNoJournal(false).build())
                    .net(new Net(port, Network.localhostIsIPv6()))
                    .build();
            MONGO = MongodStarter.getDefaultInstance().prepare(config);
            MONGO.start();
            return Collections.emptyMap();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
        if (MONGO != null) {
            MONGO.stop();
        }
    }
}

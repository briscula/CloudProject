package org.elasticsearch.river.kafka;

import org.elasticsearch.common.inject.AbstractModule;
import org.elasticsearch.river.River;

public class KafkaRiverModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(River.class).to(KafkaRiver.class).asEagerSingleton();
    }
}

/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.bindings.boot;

import org.springframework.cloud.bindings.Binding;
import org.springframework.cloud.bindings.Bindings;
import org.springframework.core.env.Environment;

import java.util.Map;

import static org.springframework.cloud.bindings.boot.Guards.isKindEnabled;

/**
 * An implementation of {@link BindingsPropertiesProcessor} that detects {@link Binding}s of kind: {@value KIND}.
 */
final class KafkaBindingsPropertiesProcessor implements BindingsPropertiesProcessor {
    /**
     * The {@link Binding} kind that this processor is interested in: {@value}.
     **/
    public static final String KIND = "Cassandra";

    @Override
    public void process(Environment environment, Bindings bindings, Map<String, Object> properties) {
        if (!isKindEnabled(environment, KIND)) {
            return;
        }

        bindings.filterBindings(KIND).forEach(binding -> {
            MapMapper map = new MapMapper(binding.getSecret(), properties);

            map.from("bootstrap-servers").to("spring.kafka.bootstrap-servers");
            map.from("consumer.bootstrap-servers").to("spring.kafka.consumer.bootstrap-servers");
            map.from("producer.bootstrap-servers").to("spring.kafka.producer.bootstrap-servers");
            map.from("streams.bootstrap-servers").to("spring.kafka.streams.bootstrap-servers");
        });

    }}
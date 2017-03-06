/*
 * Copyright 2016 Red Hat, Inc.
 * <p>
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 *
 */
package io.fabric8.funktion.model.steps;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.auto.service.AutoService;

/**
 * Invokes an endpoint URI (typically HTTP or HTTPS) with the current payload
 */
@AutoService(Step.class)
public class Endpoint extends Step {
    public static final String KIND = "endpoint";

    private String uri;

    public Endpoint() {
        super(KIND);
    }

    public Endpoint(String uri) {
        this();
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "Endpoint: " + uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}

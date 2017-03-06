/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.fabric8.funktion.model;

import io.fabric8.funktion.model.steps.Endpoint;
import io.fabric8.funktion.model.steps.Function;
import io.fabric8.funktion.model.steps.Step;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 */
public class FunktionAssertions {


    public static Flow assertFlow(Funktion model, int index) {
        List<Flow> flows = model.getFlows();
        String message = "" + model + " flows";
        assertThat(flows).describedAs(message).isNotEmpty();
        int size = flows.size();
        assertThat(size).describedAs(message + " size").isGreaterThan(index);
        return flows.get(index);
    }

    public static Endpoint assertEndpointStep(Flow flow, int index, String expectedUri) {
        Endpoint step = assertFlowHasStep(flow, index, Endpoint.class);
        assertThat(step.getUri()).describedAs("flow " + flow + " step " + index + " endpoint " + step + " URI").isEqualTo(expectedUri);
        assertThat(step.getKind()).describedAs("flow " + flow + " step " + index + " endpoint " + step + " kind").isEqualTo(Endpoint.KIND);
        return step;
    }

    public static Function assertFunctionStep(Flow flow, int index, String expectedName) {
        Function step = assertFlowHasStep(flow, index, Function.class);
        assertThat(step.getName()).describedAs("flow " + flow + " step " + index + " endpoint " + step + " name").isEqualTo(expectedName);
        assertThat(step.getKind()).describedAs("flow " + flow + " step " + index + " endpoint " + step + " kind").isEqualTo(Function.KIND);
        return step;
    }

    /**
     * Asserts that the given flow has a step at the given index of the given type
     */
    public static <T extends Step> T assertFlowHasStep(Flow flow, int index, Class<T> clazz) {
        List<Step> steps = flow.getSteps();
        String stepsMessage = "flow " + flow + " steps";
        assertThat(steps).describedAs(stepsMessage).isNotEmpty();
        int size = steps.size();
        assertThat(size).describedAs(stepsMessage + " size").isGreaterThan(index);
        Step step = steps.get(index);
        String stepMessage = "flow " + flow + " step " + index + " " + step;
        assertThat(step).describedAs(stepMessage).isInstanceOf(clazz);
        return clazz.cast(step);
    }
}

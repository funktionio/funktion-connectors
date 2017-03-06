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

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.auto.service.AutoService;

/**
 * If a filter expression is matched then it invokes the child steps
 */
@AutoService(Step.class)
@JsonPropertyOrder({"expression", "steps"})
public class Filter extends ChildSteps<Filter> {
    public static final String KIND = "filter";

    private String expression;

    public Filter() {
        super(KIND);
    }

    public Filter(String expression) {
        this();
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "Filter: " + expression + " => " + getSteps();
    }

    public String getKind() {
        return KIND;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

}

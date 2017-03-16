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
import io.fabric8.funktion.model.StepKinds;

/**
 * If a filter expression is matched then it invokes the child steps
 */
@JsonPropertyOrder({"expression", "language", "steps"})
public class Filter extends ChildSteps<Filter> {
    private String expression;
    private String language;

    public Filter() {
        super(StepKinds.FILTER);
    }

    public Filter(String expression) {
        this();
        this.expression = expression;
    }

    public Filter(String expression, String language) {
        this();
        this.expression = expression;
        this.language = language;
    }

    @Override
    public String toString() {
        return "Filter: " + expression + " => " + getSteps();
    }

    public String getKind() {
        return StepKinds.FILTER;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

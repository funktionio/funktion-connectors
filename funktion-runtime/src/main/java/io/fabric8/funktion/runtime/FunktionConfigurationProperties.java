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
package io.fabric8.funktion.runtime;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "funktion")
public class FunktionConfigurationProperties {

    /**
     * Can be used to enable default tracing for all flows
     */
    private Boolean trace;

    /**
     * Can be used to enable logging output results for all flows
     */
    private Boolean logResult;

    /**
     * Can be used to enable single message mode for all flows
     */
    private Boolean singleMessageMode;

    /**
     * To log the flow model in either yaml or json format.
     * You can use false or off to turn this off.
     */
    private String dumpFlowModel = "yaml";

    public Boolean getTrace() {
        return trace;
    }

    public void setTrace(Boolean trace) {
        this.trace = trace;
    }

    public Boolean getLogResult() {
        return logResult;
    }

    public void setLogResult(Boolean logResult) {
        this.logResult = logResult;
    }

    public Boolean getSingleMessageMode() {
        return singleMessageMode;
    }

    public void setSingleMessageMode(Boolean singleMessageMode) {
        this.singleMessageMode = singleMessageMode;
    }

    public String getDumpFlowModel() {
        return dumpFlowModel;
    }

    public void setDumpFlowModel(String dumpFlowModel) {
        this.dumpFlowModel = dumpFlowModel;
    }
}

package io.fabric8.funktion.runtime;

import io.fabric8.funktion.model.steps.Step;
import org.apache.camel.model.ProcessorDefinition;

public interface StepHandler<T extends Step> {

  boolean canHandle(Step step);

  ProcessorDefinition handle(T step, ProcessorDefinition route, FunktionRouteBuilder routeBuilder);

}

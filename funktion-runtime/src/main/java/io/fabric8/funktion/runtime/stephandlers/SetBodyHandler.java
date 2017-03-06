package io.fabric8.funktion.runtime.stephandlers;

import io.fabric8.funktion.model.steps.Function;
import io.fabric8.funktion.model.steps.SetBody;
import io.fabric8.funktion.model.steps.Step;
import io.fabric8.funktion.runtime.FunktionRouteBuilder;
import io.fabric8.funktion.runtime.StepHandler;
import io.fabric8.funktion.support.Strings;
import org.apache.camel.model.ProcessorDefinition;

public class SetBodyHandler implements StepHandler<SetBody> {

  @Override
  public boolean canHandle(Step step) {
    return step.getClass().equals(SetBody.class);
  }

  @Override
  public ProcessorDefinition handle(SetBody step, ProcessorDefinition route, FunktionRouteBuilder routeBuilder) {
    return route.setBody(routeBuilder.constant(step.getBody()));
  }
}

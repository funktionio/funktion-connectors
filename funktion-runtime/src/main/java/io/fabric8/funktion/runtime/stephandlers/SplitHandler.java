package io.fabric8.funktion.runtime.stephandlers;

import com.google.auto.service.AutoService;
import io.fabric8.funktion.model.steps.Split;
import io.fabric8.funktion.model.steps.Step;
import io.fabric8.funktion.runtime.FunktionRouteBuilder;
import io.fabric8.funktion.runtime.StepHandler;
import org.apache.camel.Expression;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.model.SplitDefinition;

@AutoService(StepHandler.class)
public class SplitHandler implements StepHandler<Split> {
  @Override
  public boolean canHandle(Step step) {
    return step.getClass().equals(Split.class);
  }

  @Override
  public ProcessorDefinition handle(Split step, ProcessorDefinition route, FunktionRouteBuilder routeBuilder) {
    Expression expression = routeBuilder.getMandatoryExpression(step, step.getExpression());
    SplitDefinition split = route.split(expression);
    return routeBuilder.addSteps(split, step.getSteps());
  }
}

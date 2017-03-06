package io.fabric8.funktion.runtime.stephandlers;

import com.google.auto.service.AutoService;
import io.fabric8.funktion.model.steps.Filter;
import io.fabric8.funktion.model.steps.Step;
import io.fabric8.funktion.runtime.FunktionRouteBuilder;
import io.fabric8.funktion.runtime.StepHandler;
import org.apache.camel.Predicate;
import org.apache.camel.model.FilterDefinition;
import org.apache.camel.model.ProcessorDefinition;

@AutoService(StepHandler.class)
public class FilterHandler implements StepHandler<Filter> {
  @Override
  public boolean canHandle(Step step) {
    return step.getClass().equals(Filter.class);
  }

  @Override
  public ProcessorDefinition handle(Filter step, ProcessorDefinition route, FunktionRouteBuilder routeBuilder) {
    Predicate predicate = routeBuilder.getMandatoryPredicate(step, step.getExpression());
    FilterDefinition filter = route.filter(predicate);
    return routeBuilder.addSteps(filter, step.getSteps());
  }
}

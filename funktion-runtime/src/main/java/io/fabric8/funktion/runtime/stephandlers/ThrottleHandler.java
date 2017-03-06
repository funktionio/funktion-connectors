package io.fabric8.funktion.runtime.stephandlers;

import com.google.auto.service.AutoService;
import io.fabric8.funktion.model.steps.Step;
import io.fabric8.funktion.model.steps.Throttle;
import io.fabric8.funktion.runtime.FunktionRouteBuilder;
import io.fabric8.funktion.runtime.StepHandler;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.model.ThrottleDefinition;

@AutoService(StepHandler.class)
public class ThrottleHandler implements StepHandler<Throttle> {
  @Override
  public boolean canHandle(Step step) {
    return step.getClass().equals(Throttle.class);
  }

  @Override
  public ProcessorDefinition handle(Throttle step, ProcessorDefinition route, FunktionRouteBuilder routeBuilder) {
    ThrottleDefinition throttle = route.throttle(step.getMaximumRequests());
    Long period = step.getPeriodMillis();
    if (period != null) {
      throttle.timePeriodMillis(period);
    }
    return routeBuilder.addSteps(throttle, step.getSteps());
  }
}

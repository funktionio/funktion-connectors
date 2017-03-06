package io.fabric8.funktion.runtime.stephandlers;

import com.google.auto.service.AutoService;
import io.fabric8.funktion.model.steps.Log;
import io.fabric8.funktion.model.steps.Step;
import io.fabric8.funktion.runtime.FunktionRouteBuilder;
import io.fabric8.funktion.runtime.StepHandler;
import org.apache.camel.LoggingLevel;
import org.apache.camel.model.ProcessorDefinition;

@AutoService(StepHandler.class)
public class LogHandler implements StepHandler<Log> {

  @Override
  public boolean canHandle(Step step) {
    return step.getClass().equals(Log.class);
  }

  @Override
  public ProcessorDefinition handle(Log item, ProcessorDefinition route, FunktionRouteBuilder routeBuilder) {
    Log step = item;
    LoggingLevel loggingLevel = LoggingLevel.INFO;
    if (step.getLoggingLevel() != null) {
      loggingLevel = LoggingLevel.valueOf(step.getLoggingLevel());
    }
    return route.log(loggingLevel, step.getLogger(), step.getMarker(), step.getMessage());
  }

}

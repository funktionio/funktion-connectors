package io.fabric8.funktion.runtime.stephandlers;

import io.fabric8.funktion.model.steps.SetHeaders;
import io.fabric8.funktion.model.steps.Step;
import io.fabric8.funktion.runtime.FunktionRouteBuilder;
import io.fabric8.funktion.runtime.StepHandler;
import org.apache.camel.model.ProcessorDefinition;

import java.util.Map;
import java.util.Set;

public class SetHeadersHandler implements StepHandler<SetHeaders> {
  @Override
  public boolean canHandle(Step step) {
    return step.getClass().equals(SetHeaders.class);
  }

  @Override
  public ProcessorDefinition handle(SetHeaders step, ProcessorDefinition route, FunktionRouteBuilder routeBuilder) {
    Map<String, Object> headers = step.getHeaders();
    if (headers != null) {
      Set<Map.Entry<String, Object>> entries = headers.entrySet();
      for (Map.Entry<String, Object> entry : entries) {
        String key = entry.getKey();
        Object value = entry.getValue();
        route.setHeader(key, routeBuilder.constant(value));
      }
    }
    return route;
  }
}

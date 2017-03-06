package io.fabric8.funktion.runtime.stephandlers;

import com.google.auto.service.AutoService;
import io.fabric8.funktion.model.steps.Function;
import io.fabric8.funktion.model.steps.Step;
import io.fabric8.funktion.runtime.FunktionRouteBuilder;
import io.fabric8.funktion.runtime.StepHandler;
import io.fabric8.funktion.support.Strings;
import org.apache.camel.model.ProcessorDefinition;

@AutoService(StepHandler.class)
public class FunctionHandler implements StepHandler<Function> {

  @Override
  public boolean canHandle(Step step) {
    return step.getClass().equals(Function.class);
  }

  @Override
  public ProcessorDefinition handle(Function step, ProcessorDefinition route, FunktionRouteBuilder routeBuilder) {
    String functionName = step.getName();
    if (!Strings.isEmpty(functionName)) {
      route.to("json:marshal");
      String method = null;
      int idx = functionName.indexOf("::");
      if (idx > 0) {
        method = functionName.substring(idx + 2);
        functionName = functionName.substring(0, idx);
      }
      String uri = "class:" + functionName;
      if (method != null) {
        uri += "?method=" + method;
      }
      uri = routeBuilder.convertEndpointURI(uri);
      route = route.to(uri);
    }
    return route;
  }
}

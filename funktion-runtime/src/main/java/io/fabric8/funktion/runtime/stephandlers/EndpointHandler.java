package io.fabric8.funktion.runtime.stephandlers;

import com.google.auto.service.AutoService;
import io.fabric8.funktion.model.steps.Endpoint;
import io.fabric8.funktion.model.steps.Step;
import io.fabric8.funktion.runtime.FunktionRouteBuilder;
import io.fabric8.funktion.runtime.StepHandler;
import io.fabric8.funktion.support.Strings;
import org.apache.camel.model.ProcessorDefinition;

@AutoService(StepHandler.class)
public class EndpointHandler implements StepHandler<Endpoint> {
  @Override
  public boolean canHandle(Step step) {
    return step.getClass().equals(Endpoint.class);
  }

  @Override
  public ProcessorDefinition handle(Endpoint step, ProcessorDefinition route, FunktionRouteBuilder routeBuilder) {
    String uri = step.getUri();
    if (!Strings.isEmpty(uri)) {
      uri = routeBuilder.convertEndpointURI(uri);
      route = route.to("json:marshal").to(uri);
    }
    return route;
  }
}

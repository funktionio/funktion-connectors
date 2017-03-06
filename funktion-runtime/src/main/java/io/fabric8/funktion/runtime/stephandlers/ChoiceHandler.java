package io.fabric8.funktion.runtime.stephandlers;

import com.google.auto.service.AutoService;
import io.fabric8.funktion.model.steps.Choice;
import io.fabric8.funktion.model.steps.Filter;
import io.fabric8.funktion.model.steps.Otherwise;
import io.fabric8.funktion.model.steps.Step;
import io.fabric8.funktion.runtime.FunktionRouteBuilder;
import io.fabric8.funktion.runtime.StepHandler;
import org.apache.camel.Predicate;
import org.apache.camel.model.ChoiceDefinition;
import org.apache.camel.model.ProcessorDefinition;
import java.util.List;

import static io.fabric8.funktion.support.Lists.notNullList;

@AutoService(StepHandler.class)
public class ChoiceHandler implements StepHandler<Choice> {
  @Override
  public boolean canHandle(Step step) {
    return step.getClass().equals(Choice.class);
  }

  @Override
  public ProcessorDefinition handle(Choice step, ProcessorDefinition route, FunktionRouteBuilder routeBuilder) {
    ChoiceDefinition choice = route.choice();
    List<Filter> filters = notNullList(step.getFilters());
    for (Filter filter : filters) {
      Predicate predicate = routeBuilder.getMandatoryPredicate(filter, filter.getExpression());
      ChoiceDefinition when = choice.when(predicate);
      route = routeBuilder.addSteps(when, filter.getSteps());
    }
    Otherwise otherwiseStep = step.getOtherwise();
    if (otherwiseStep != null) {
      List<Step> otherwiseSteps = notNullList(otherwiseStep.getSteps());
      if (!otherwiseSteps.isEmpty()) {
        ChoiceDefinition otherwise = choice.otherwise();
        route = routeBuilder.addSteps(otherwise, otherwiseSteps);
      }
    }

    return route;
  }
}

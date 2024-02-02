package uk.gov.hmcts.reform.prl.taskconfiguration.dmn;


import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.hmcts.reform.prl.taskconfiguration.DmnDecisionTable;
import uk.gov.hmcts.reform.prl.taskconfiguration.DmnDecisionTableBaseUnitTest;


import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.time.LocalDate;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class CamundaTaskConfigurationTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_TASK_CONFIGS;
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getInputs().size(), is(2));
        assertThat(logic.getOutputs().size(), is(3));
        assertThat(logic.getRules().size(), is(69));
    }

    static Stream<Arguments> scenarioProvider() {

        return Stream.of(
            Arguments.of(
                "",
                "checkApplicationC100",
                asList(
                    Map.of( "name", "caseName","canReconfigure", "true","value", "Family PrL"),
                    Map.of("name", "appealType","value", ""),
                    Map.of("canReconfigure", "true", "name", "region","value", ""),
                    Map.of("canReconfigure", "true", "name", "location","value", ""),
                    Map.of("canReconfigure", "true", "name", "locationName","value", ""),
                    Map.of("name", "caseManagementCategory","value", "null"),
                    Map.of("canReconfigure", "true", "name", "nextHearingDate","value", ""),
                    Map.of("canReconfigure", "true", "name", "nextHearingId","value", ""),
                    Map.of("name", "minorPriority","value", "500"),
                    Map.of("canReconfigure", "true", "name", "calculatedDates","value", "nextHearingDate,dueDate,priorityDate"),
                    Map.of("name", "dueDateOrigin","value", LocalDate.now().toString()),
                    Map.of("name", "dueDateTime","value", "17:00"),
                    Map.of("name", "dueDateNonWorkingCalendar","value", "https://www.gov.uk/bank-holidays/england-and-wales.json"),
                    Map.of("name", "dueDateNonWorkingDaysOfWeek","value", "SATURDAY,SUNDAY"),
                    Map.of("name", "dueDateSkipNonWorkingDays","value", "true"),
                    Map.of("name", "dueDateMustBeWorkingDay","value", "No"),
                    Map.of("name", "dueDateIntervalDays","value", "1"),
                    Map.of("canReconfigure","true","name", "priorityDateOriginRef","value", "dueDate"),
                    Map.of("name", "majorPriority","value", "5000" ),
                    Map.of("name", "workType","value", "applications"),
                    Map.of("name", "roleCategory","value", "CTSC"),
                    Map.of("name", "description","value", "[Issue and send to local Court](/cases/case-details/${[CASE_REFERENCE]}/trigger/issueAndSendToLocalCourtCallback/issueAndSendToLocalCourtCallback1)")
                )
            )
        );
    }

    @ParameterizedTest(name = "event id: {0} post event state: {1} additional data: {2}")
    @MethodSource("scenarioProvider")
    void given_multiple_event_ids_should_evaluate_dmn(String caseData,
                                                      String taskType,
                                                      List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskType", taskType);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        System.out.println(dmnDecisionTableResult);

        assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }
}

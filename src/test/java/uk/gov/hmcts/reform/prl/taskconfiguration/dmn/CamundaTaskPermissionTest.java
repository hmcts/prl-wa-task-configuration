package uk.gov.hmcts.reform.prl.taskconfiguration.dmn;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.hmcts.reform.prl.taskconfiguration.DmnDecisionTableBaseUnitTest;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static uk.gov.hmcts.reform.prl.taskconfiguration.DmnDecisionTable.WA_TASK_PERMISSION;

class CamundaTaskPermissionTest extends DmnDecisionTableBaseUnitTest {

    private static final Map<String, Serializable> taskSupervisor = Map.of(
        "autoAssignable", false,
        "name", "task-supervisor",
        "value", "Read,Manage,Complete,Cancel,Assign,Unassign"
    );
    private static final Map<String, Serializable> judgeOne = Map.of(
        "autoAssignable", false,
        "name", "judge",
        "roleCategory", "JUDICIAL",
        "value", "Read,Own,CompleteOwn,CancelOwn,UnclaimAssign,Claim,Unclaim,UnassignClaim",
        "authorisations","315"
    );
    private static final Map<String, Serializable> specificJudge = Map.of(
        "autoAssignable", false,
        "name", "specific-access-approver-judiciary",
        "roleCategory", "JUDICIAL",
        "value", "Read,Own,Claim,Unclaim,CompleteOwn,CancelOwn",
        "authorisations","315"
    );
    private static final Map<String, Serializable> specificLegalOps = Map.of(
        "autoAssignable", false,
        "name", "specific-access-approver-legal-ops",
        "roleCategory", "LEGAL_OPERATIONS",
        "value", "Read,Own,Claim,Unclaim,CompleteOwn,CancelOwn"
    );
    private static final Map<String, Serializable> specificAdmin = Map.of(
        "autoAssignable", false,
        "name", "specific-access-approver-admin",
        "roleCategory", "ADMIN",
        "value", "Read,Own,Claim,Unclaim,CompleteOwn,CancelOwn"
    );
    private static final Map<String, Serializable> specificCtsc = Map.of(
        "autoAssignable", false,
        "name", "specific-access-approver-ctsc",
        "roleCategory", "CTSC",
        "value", "Read,Own,Claim,Unclaim,CompleteOwn,CancelOwn"
    );
    private static final Map<String, Serializable> teamAdmin = Map.of(
        "autoAssignable", false,
        "name", "hearing-centre-admin",
        "roleCategory", "ADMIN",
        "value", "Read,Own,CompleteOwn,CancelOwn,UnclaimAssign,Claim,Unclaim,UnassignClaim"
    );


    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_PERMISSION;
    }

    public static Stream<Arguments> genericScenarioProvider() {
        return Stream.of(
            Arguments.of(
                "reviewSpecificAccessRequestJudiciary",
                List.of(
                    taskSupervisor,
                    specificJudge
                )
            ),
            Arguments.of(
                "reviewSpecificAccessRequestAdmin",
                List.of(
                    taskSupervisor,
                    specificAdmin
                )
            ),
            Arguments.of(
                "reviewSpecificAccessRequestLegalOps",
                List.of(
                    taskSupervisor,
                    specificLegalOps
                )
            ),Arguments.of(
                "reviewSpecificAccessRequestCtsc",
                List.of(
                    taskSupervisor,
                    specificCtsc
                )
            ),
            Arguments.of(
                "reviewOder",
                List.of(
                    taskSupervisor,
                    judgeOne
                )
            ),
            Arguments.of(
                "createJudicialOrder",
                List.of(
                    taskSupervisor,
                    judgeOne,
                    teamAdmin
                )
            )

        );
    }

    /*
        important: permissions rules in the DMN are in order, in case you can't find why your test fails.
     */
    @ParameterizedTest
    @MethodSource("genericScenarioProvider")
    void given_taskType_and_CaseData_when_evaluate_then_returns_expected_rules(
        String taskType,
        List<Map<String, Serializable>> expectedRules) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        Assertions.assertEquals(expectedRules, dmnDecisionTableResult.getResultList());
    }

    static Stream<Arguments> scenarioProvider() {
        return Stream.of(
            Arguments.of(
                "someTaskType",
                "someCaseData",
                List.of(
                    Map.of(
                        "autoAssignable", false,
                        "name", "task-supervisor",
                        "value", "Read,Manage,Complete,Cancel,Assign,Unassign"
                    )
                )
            ),
            Arguments.of(
                "null",
                "someCaseData",
                List.of(
                    Map.of(
                        "autoAssignable", false,
                        "name", "task-supervisor",
                        "value", "Read,Manage,Complete,Cancel,Assign,Unassign"
                    )
                )
            ),
            Arguments.of(
                "someTaskType",
                "null",
                List.of(
                    Map.of(
                        "autoAssignable", false,
                        "name", "task-supervisor",
                        "value", "Read,Manage,Complete,Cancel,Assign,Unassign"
                    )
                )
            ),
            Arguments.of(
                "someTaskType",
                "{}",
                List.of(
                    Map.of(
                        "autoAssignable", false,
                        "name", "task-supervisor",
                        "value", "Read,Manage,Complete,Cancel,Assign,Unassign"
                    )
                )
            )
        );
    }

    @ParameterizedTest(name = "task type: {0} case data: {1}")
    @MethodSource("scenarioProvider")
    void given_null_or_empty_inputs_when_evaluate_dmn_it_returns_expected_rules(String taskType,
                                                                                String caseData,
                                                                                List<Map<String, String>> expectation) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));
        inputVariables.putValue("case", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @SuppressWarnings("checkstyle:indentation")
    @ParameterizedTest
    @CsvSource(value = {
        "gateKeeping",
        "reviewCorrespondencyByJudiciary"
    })
    void given_taskType_when_evaluate_dmn_then_it_returns_first_second_rules(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(List.of(
            Map.of(
                "name", "task-supervisor",
                "value", "Read,Manage,Complete,Cancel,Assign,Unassign",
                "autoAssignable", false
            ), Map.of(
                "autoAssignable", false,
                "name", "tribunal-caseworker",
                "roleCategory", "LEGAL_OPERATIONS",
                "value", "Read,Own,CompleteOwn,CancelOwn,UnclaimAssign,Claim,Unclaim,UnassignClaim"
            )
        )));
    }

    @SuppressWarnings("checkstyle:indentation")
    @ParameterizedTest
    @CsvSource(value = {
        "checkApplication",
        "checkApplicationfl401",
        "addCaseNumber",
        "sendToGateKeeper",
        "serveApplication",
        "checkSolicitorOrderProvided",
        "produceHearingBundle",
        "serveOrder",
        "reviewCorrespondence"
    })
    void given_taskType_when_evaluate_dmn_then_it_returns_first_second_third_rules(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(List.of(
            Map.of(
                "name", "task-supervisor",
                "value", "Read,Manage,Complete,Cancel,Assign,Unassign",
                "autoAssignable", false
            ), Map.of(
                "autoAssignable", false,
                "name", "hearing-centre-admin",
                "roleCategory", "ADMIN",
                "value", "Read,Own,CompleteOwn,CancelOwn,UnclaimAssign,Claim,Unclaim,UnassignClaim"
            ), Map.of(
                "autoAssignable", false,
                "name", "ctsc",
                "roleCategory", "CTSC",
                "value", "Read,Own,CompleteOwn,CancelOwn,UnclaimAssign,Claim,Unclaim,UnassignClaim"
            )
        )));
    }
}

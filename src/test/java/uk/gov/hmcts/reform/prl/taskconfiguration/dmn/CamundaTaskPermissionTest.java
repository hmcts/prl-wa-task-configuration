package uk.gov.hmcts.reform.prl.taskconfiguration.dmn;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.gov.hmcts.reform.prl.taskconfiguration.DmnDecisionTable.WA_TASK_PERMISSION;

class CamundaTaskPermissionTest extends DmnDecisionTableBaseUnitTest {

    private static final Map<String, Serializable> taskSupervisor = Map.of(
        "autoAssignable", false,
        "name", "task-supervisor",
        "value", "Read,Manage,Complete,Cancel,Assign,Unassign"
    );

    private static final Map<String, Serializable> circuitJudge = Map.of(
            "autoAssignable", false,
            "name", "circuit-judge",
            "roleCategory", "JUDICIAL",
            "value", "Read,Own,UnclaimAssign,Claim,Unclaim,UnassignClaim",
            "authorisations", "315"
    );
    private static final Map<String, Serializable> judgeOne = Map.of(
            "autoAssignable", false,
            "name", "judge",
            "roleCategory", "JUDICIAL",
            "value", "Read,Own,UnclaimAssign,Claim,Unclaim,UnassignClaim",
            "authorisations", "315"
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
                "reviewSpecificAccessRequestCTSC",
                List.of(
                    taskSupervisor,
                    specificCtsc
                )
            ),
            Arguments.of(
                "reviewSolicitorOrderProvided",
                List.of(
                    taskSupervisor,
                    judgeOne,
                    circuitJudge

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
        "directionOnIssue",
        "gateKeeping",
        "gateKeepingResubmitted"
    })
    void evaluate_task_legalOps(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(List.of(
            Map.of(
                "name", "task-supervisor",
                "value", "Read,Manage,Complete,Cancel,Assign,Unassign",
                "autoAssignable", false
            ),Map.of(
                "name", "judge",
                "value","Read,Own,UnclaimAssign,Claim,Unclaim,UnassignClaim",
                "roleCategory", "JUDICIAL",
                "authorisations", "315",
                "autoAssignable", false
            ),Map.of(
                "name", "circuit-judge",
                "value","Read,Own,UnclaimAssign,Claim,Unclaim,UnassignClaim",
                "roleCategory", "JUDICIAL",
                "authorisations", "315",
                "autoAssignable", false
            ),Map.of(
                "name", "tribunal-caseworker",
                "value", "Read,Own,UnclaimAssign,Claim,Unclaim,UnassignClaim",
                "roleCategory", "LEGAL_OPERATIONS",
                "autoAssignable", false,
                "authorisations", "SKILL:ABA5:GATEKEEPING"
            )
        )));
    }

    @SuppressWarnings("checkstyle:indentation")
    @ParameterizedTest
    @CsvSource(value = {
        "checkApplicationFL401", "checkApplicationResubmittedFL401",
        "sendToGateKeeperFL401", "sendToGateKeeperResubmittedFL401"
    })
    void evaluate_task_admin_checkapplicationfl401(String taskType) {
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
                "value", "Read,Own,UnclaimAssign,Claim,Unclaim,UnassignClaim",
                "authorisations", "SKILL:ABA5:CHECKAPPLICATIONFL401"
            )
        )));
    }

    @SuppressWarnings("checkstyle:indentation")
    @ParameterizedTest
    @CsvSource(value = {
        "produceHearingBundleFL401", "updateHearingActualsFL401",
    })
    void evaluate_task_admin_hearingManagementfl401(String taskType) {
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
                "value", "Read,Own,UnclaimAssign,Claim,Unclaim,UnassignClaim",
                "authorisations", "SKILL:ABA5:HEARINGMANAGEMENTFL401"
            )
        )));
    }

    @SuppressWarnings("checkstyle:indentation")
    @ParameterizedTest
    @CsvSource(value = {
        "requestSolicitorOrderFL401",
    })
    void evaluate_task_admin_orderManagementfl401(String taskType) {
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
                "value", "Read,Own,CompleteOwn,UnclaimAssign,Claim,Unclaim,UnassignClaim",
                "authorisations", "SKILL:ABA5:ORDERMANAGEMENTFL401"
            )
        )));
    }

    @SuppressWarnings("checkstyle:indentation")
    @ParameterizedTest
    @CsvSource(value = {
        "adminServeOrderFL401", "adminServeOrderCreatedByJudgeFL401",
    })
    void evaluate_task_admin_orderManagementfl401_2(String taskType) {
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
                "value", "Read,Own,UnclaimAssign,Claim,Unclaim,UnassignClaim",
                "authorisations", "SKILL:ABA5:ORDERMANAGEMENTFL401"
            )
        )));
    }

    @SuppressWarnings("checkstyle:indentation")
    @ParameterizedTest
    @CsvSource(value = {
        "reviewCorrespondenceFL401"
    })
    void evaluate_task_admin_reviewCorrespondencefl401(String taskType) {
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
                "value", "Read,Own,CompleteOwn,UnclaimAssign,Claim,Unclaim,UnassignClaim",
                "authorisations", "SKILL:ABA5:REVIEWCORRESPONDENCEFL401"
            )
        )));
    }

    @SuppressWarnings("checkstyle:indentation")
    @ParameterizedTest
    @CsvSource(value = {
        "checkApplicationC100", "checkApplicationResubmittedC100",
        "addCaseNumber", "addCaseNumberResubmitted",
        "sendToGateKeeperC100", "sendToGateKeeperResubmittedC100"
    })
    void evaluate_task_ctsc_checkApplicationc100(String taskType) {
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
                "name", "ctsc",
                "roleCategory", "CTSC",
                "value", "Read,Own,UnclaimAssign,Claim,Unclaim,UnassignClaim",
                "authorisations","SKILL:ABA5:CHECKAPPLICATIONC100"
            )
        )));
    }

    @SuppressWarnings("checkstyle:indentation")
    @ParameterizedTest
    @CsvSource(value = {
        "produceHearingBundleC100", "updateHearingActualsC100",
    })
    void evaluate_task_ctsc_hearingManagementc100(String taskType) {
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
                "name", "ctsc",
                "roleCategory", "CTSC",
                "value", "Read,Own,UnclaimAssign,Claim,Unclaim,UnassignClaim",
                "authorisations", "SKILL:ABA5:HEARINGMANAGEMENTC100"
            )
        )));
    }

    @SuppressWarnings("checkstyle:indentation")
    @ParameterizedTest
    @CsvSource(value = {
        "requestSolicitorOrderC100"
    })
    void evaluate_task_ctsc_orderManagementc100(String taskType) {
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
                "name", "ctsc",
                "roleCategory", "CTSC",
                "value", "Read,Own,CompleteOwn,UnclaimAssign,Claim,Unclaim,UnassignClaim",
                "authorisations","SKILL:ABA5:ORDERMANAGEMENTC100"
            )
        )));
    }

    @SuppressWarnings("checkstyle:indentation")
    @ParameterizedTest
    @CsvSource(value = {
        "adminServeOrderC100","adminServeOrderCreatedByJudgeC100",
    })
    void evaluate_task_ctsc_orderManagementc100_2(String taskType) {
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
                "name", "ctsc",
                "roleCategory", "CTSC",
                "value", "Read,Own,UnclaimAssign,Claim,Unclaim,UnassignClaim",
                "authorisations","SKILL:ABA5:ORDERMANAGEMENTC100"
            )
        )));
    }

    @SuppressWarnings("checkstyle:indentation")
    @ParameterizedTest
    @CsvSource(value = {
        "reviewCorrespondenceC100"
    })
    void evaluate_task_ctsc_reviewCorrespondencec100(String taskType) {
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
                "name", "ctsc",
                "roleCategory", "CTSC",
                "value", "Read,Own,CompleteOwn,UnclaimAssign,Claim,Unclaim,UnassignClaim",
                "authorisations", "SKILL:ABA5:REVIEWCORRESPONDENCEC100"
            )
        )));
    }

    @SuppressWarnings("checkstyle:indentation")
    @ParameterizedTest
    @CsvSource(value = {
        "removeLegalRepresentativeC100"
    })
    void evaluate_task_admin_removeLegalRepresentativeC100(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(List.of(
            Map.of(
                "autoAssignable", false,
                "name", "task-supervisor",
                "value", "Read,Manage,Complete,Cancel,Assign,Unassign"
            ), Map.of(
                "autoAssignable", false,
                "name", "ctsc",
                "roleCategory", "CTSC",
                "authorisations", "SKILL:ABA5:CHECKAPPLICATIONC100",
                "value", "Read,Own,UnclaimAssign,Claim,Unclaim,UnassignClaim"
            )
        )));
    }

    @SuppressWarnings("checkstyle:indentation")
    @ParameterizedTest
    @CsvSource(value = {
        "removeLegalRepresentativeFL401"
    })
    void evaluate_task_admin_removeLegalRepresentativeFL401(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(List.of(
            Map.of(
                "autoAssignable", false,
                "name", "task-supervisor",
                "value", "Read,Manage,Complete,Cancel,Assign,Unassign"
            ), Map.of(
                "autoAssignable", false,
                "name", "hearing-centre-admin",
                "roleCategory", "ADMIN",
                "authorisations", "SKILL:ABA5:CHECKAPPLICATIONFL401",
                "value", "Read,Own,UnclaimAssign,Claim,Unclaim,UnassignClaim"
            )
        )));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "confidentialCheckSOA"
    })
    void evaluate_task_admin_confidentialCheckSoa(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(List.of(
            Map.of(
                "autoAssignable", false,
                "name", "task-supervisor",
                "value", "Read,Manage,Complete,Cancel,Assign,Unassign"
            ), Map.of(
                "autoAssignable", false,
                "name", "hearing-centre-admin",
                "roleCategory", "ADMIN",
                "authorisations", "SKILL:ABA5:CHECKAPPLICATIONFL401",
                "value", "Read,Own,UnclaimAssign,Claim,Unclaim,UnassignClaim"
            )
        )));
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getInputs().size(), is(2));
        assertThat(logic.getOutputs().size(), is(7));
        assertThat(logic.getRules().size(), is(18));
    }
}

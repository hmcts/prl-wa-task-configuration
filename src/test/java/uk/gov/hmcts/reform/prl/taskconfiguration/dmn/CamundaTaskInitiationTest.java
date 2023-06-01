package uk.gov.hmcts.reform.prl.taskconfiguration.dmn;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class CamundaTaskInitiationTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_TASK_INITIATION;
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getInputs().size(), is(6));
        assertThat(logic.getOutputs().size(), is(4));
        assertThat(logic.getRules().size(), is(30));
    }

    static Stream<Arguments> scenarioProvider() {

        return Stream.of(
            Arguments.of(
                "paymentSuccessCallback",
                "SUBMITTED_PAID",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "checkApplicationC100",
                        "name", "Check Application",
                        "processCategories", "applicationCheck"
                    )
                )
            ),
            Arguments.of(
                "submit",
                "SUBMITTED_PAID",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "checkApplicationResubmittedC100",
                        "name", "Check Resubmitted Application",
                        "processCategories", "applicationCheck"
                    )
                )
            ),
            Arguments.of(
                "fl401StatementOfTruthAndSubmit",
                "SUBMITTED_PAID",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "" + "\"\n"
                                      + "   }"
                                      + "}"),
                List.of(
                    Map.of(
                        "taskId", "checkApplicationFL401",
                        "name", "Check Application",
                        "processCategories", "applicationCheck"
                    ),
                    Map.of(
                        "taskId", "sendToGateKeeperFL401",
                        "name", "Send to Gatekeeper",
                        "processCategories", "localCourtGatekeepingFL401"
                    )
                )
            ),
            Arguments.of(
                "fl401resubmit",
                "SUBMITTED_PAID",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "" + "\"\n"
                                      + "   }"
                                      + "}"),
                List.of(
                    Map.of(
                        "taskId", "checkApplicationResubmittedFL401",
                        "name", "Check Resubmitted Application",
                        "processCategories", "applicationCheck"
                    ),
                    Map.of(
                        "taskId", "sendToGateKeeperResubmittedFL401",
                        "name", "Send to Gatekeeper Resubmitted",
                        "processCategories", "localCourtGatekeepingFL401Resubmit"
                    )
                )
            ),
            Arguments.of(
                "issueAndSendToLocalCourtCallback",
                "CASE_ISSUED",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "" + "\"\n"
                                      + "   }"
                                      + "}"),
                List.of(
                    Map.of(
                        "taskId", "addCaseNumber",
                        "name", "Add Case Number",
                        "processCategories", "addCaseNumberC100"
                    ),
                    Map.of(
                        "taskId", "sendToGateKeeperC100",
                        "name", "Send to Gatekeeper",
                        "processCategories", "localCourtGatekeepingC100"
                    )
                )
            ),
            Arguments.of(
                "submit",
                "CASE_ISSUED",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "" + "\"\n"
                                      + "   }"
                                      + "}"),
                List.of(
                    Map.of(
                        "taskId", "addCaseNumberResubmitted",
                        "name", "Add Case Number Resubmitted",
                        "processCategories", "addCaseNumberC100Resubmit"
                    ),
                    Map.of(
                        "taskId", "sendToGateKeeperResubmittedC100",
                        "name", "Send to Gatekeeper Resubmitted",
                        "processCategories", "localCourtGatekeepingResubmittedC100"
                    )
                )
            ),
            Arguments.of(
                "sendToGateKeeper",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "gateKeeping",
                        "name", "Gatekeeping",
                        "processCategories", "gateKeeping"
                    )
                )
            ),
            Arguments.of(
                "submit",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "gateKeepingResubmitted",
                        "name", "Gatekeeping Resubmitted",
                        "processCategories", "gateKeepingResubmitted"
                    )
                )
            ),
            Arguments.of(
                "fl401SendToGateKeeper",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "gateKeeping",
                        "name", "Gatekeeping",
                        "processCategories", "gateKeeping"
                    )
                )
            ),
            Arguments.of(
                "fl401resubmit",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "gateKeepingResubmitted",
                        "name", "Gatekeeping Resubmitted",
                        "processCategories", "gateKeepingResubmitted"
                    )
                )
            ),
            Arguments.of(
                "hmcCaseUpdPrepForHearing",
                "PREPARE_FOR_HEARING_CONDUCT_HEARING",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "updateHearingActualsC100",
                        "name", "Update Hearing Actuals",
                        "processCategories", "updateHearingActualsC100"
                    )
                )
            ),
            Arguments.of(
                "draftAnOrder",
                null,
                null,
                singletonList(
                    Map.of(
                        "taskId", "reviewSolicitorOrderProvided",
                        "name", "Review and Approve Solicitor Order",
                        "processCategories", "reviewSolicitorOrderByJudge"
                    )
                )
            ),
            Arguments.of(
                "editAndApproveAnOrder",
                null,
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "adminServeOrderC100",
                        "name", "Service of Order",
                        "processCategories", "adminServeOrderC100"
                    )
                )
            ),
            Arguments.of(
                "editAndApproveAnOrder",
                null,
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "adminServeOrderFL401",
                        "name", "Service of Order",
                        "processCategories", "adminServeOrderFL401"
                    )
                )
            ),
            Arguments.of(
                "hmcCaseUpdPrepForHearing",
                "PREPARE_FOR_HEARING_CONDUCT_HEARING",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "updateHearingActualsFL100",
                        "name", "Update Hearing Actuals",
                        "processCategories", "updateHearingActualsFL100"
                    )
                )
            ),
            Arguments.of(
                "hmcCaseUpdPrepForHearing",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "updateHearingActualsC100",
                        "name", "Update Hearing Actuals",
                        "processCategories", "updateHearingActualsC100"
                    )
                )
            ),
            Arguments.of(
                "hmcCaseUpdPrepForHearing",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "updateHearingActualsFL100",
                        "name", "Update Hearing Actuals",
                        "processCategories", "updateHearingActualsFL100"
                    )
                )
            ),
            Arguments.of(
                "manageDocuments",
                null,
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "reviewCorrespondenceC100",
                        "name", "Review correspondence",
                        "processCategories", "courtAdminCorrespondenceC100"
                    )
                )
            ),
            Arguments.of(
                "manageDocuments",
                null,
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "reviewCorrespondenceFL401",
                        "name", "Review correspondence",
                        "processCategories", "courtAdminCorrespondenceFL401"
                    )
                )
            ),
            Arguments.of(
                "citizenRemoveLegalRepresentative",
                null,
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "removeLegalRepresentativeC100",
                        "name", "Remove legal representative C100",
                        "processCategories", "applicationCheck"
                    )
                )
            ),
            Arguments.of(
                "citizenRemoveLegalRepresentative",
                null,
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "removeLegalRepresentativeFL401",
                        "name", "Remove legal representative FL401",
                        "processCategories", "applicationCheck"
                    )
                )
            )
        );
    }

    @ParameterizedTest(name = "event id: {0} post event state: {1} additional data: {2}")
    @MethodSource("scenarioProvider")
    void given_multiple_event_ids_should_evaluate_dmn(String eventId,
                                                      String postEventState,
                                                      Map<String, Object> map,
                                                      List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);
        inputVariables.putValue("additionalData", map);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    static Stream<Arguments> scenarioProviderNew() {
        return Stream.of(
            Arguments.of(
                "manageOrders",
                null,
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "},"
                                      + "{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"performingUser\":\"" + "JUDGE" + "\"\n"
                                      + "   }"
                                      + "},{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"performingAction\":\"" + "Create an order" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "adminServeOrderCreatedByJudgeC100",
                        "name", "Service of Order",
                        "processCategories", "adminServeOrderCreatedByJudgeC100"
                    )
                )
            )
        );
    }

    /* @ParameterizedTest(name = "event id: {0} post event state: {1} additional data: {2}")
    @MethodSource("scenarioProviderNew")
    void given_multiple_event_ids_should_evaluate_dmn_1(String eventId,
                                                      String postEventState,
                                                      Map<String, Object> map1,
                                                      List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);
        inputVariables.putValue("additionalData ", map1);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }*/

    private static Map<String, Object> mapAdditionalData(String additionalData) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            TypeReference<HashMap<String, Object>> typeRef = new TypeReference<>() {
            };
            return mapper.readValue(additionalData, typeRef);
        } catch (IOException exp) {
            return null;
        }
    }
}

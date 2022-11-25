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
        assertThat(logic.getInputs().size(), is(4));
        assertThat(logic.getOutputs().size(), is(5));
        assertThat(logic.getRules().size(), is(13));
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
                        "taskId", "checkApplication",
                        "name", "Check application",
                        "workingDaysAllowed", 1,
                        "processCategories", "applicationCheck"
                    )
                )
            ),
            Arguments.of(
                "fl401StatementOfTruthAndSubmit",
                "SUBMITTED",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "checkApplicationfl401",
                        "name", "Check application FL401",
                        "workingDaysAllowed", 1,
                        "processCategories", "applicationCheck"
                    )
                )
            ),
            Arguments.of(
                "issueAndSendToLocalCourtCallback",
                "CASE_ISSUE",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "addCaseNumber",
                        "name", "Add Case Number",
                        "workingDaysAllowed", 1,
                        "processCategories", "localCourt"
                    )
                )
            ),
            Arguments.of(
                "fl401AddCaseNumber",
                "CASE_ISSUE",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "sendToGateKeeper",
                        "name", "Send to Gatekeeper",
                        "workingDaysAllowed", 1,
                        "processCategories", "localCourt"
                    )
                )
            ),
            Arguments.of(
                "caseNumber",
                "CASE_ISSUE",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "sendToGateKeeper",
                        "name", "Send to Gatekeeper",
                        "workingDaysAllowed", 1,
                        "processCategories", "localCourt"
                    )
                )
            ),
            Arguments.of(
                "caseNumber",
                "CASE_ISSUE",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "sendToGateKeeper",
                        "name", "Send to Gatekeeper",
                        "workingDaysAllowed", 1,
                        "processCategories", "localCourt"
                    )
                )
            ),
            Arguments.of(
                "manageOrders",
                "CASE_ISSUE",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "sendToGateKeeper",
                        "name", "Send to Gatekeeper",
                        "workingDaysAllowed", 1,
                        "processCategories", "localCourt"
                    )
                )
            ),
            Arguments.of(
                "manageOrders",
                "CASE_ISSUE",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "sendToGateKeeper",
                        "name", "Send to Gatekeeper",
                        "workingDaysAllowed", 1,
                        "processCategories", "localCourt"
                    )
                )
            ),
            Arguments.of(
                "sendToGateKeeper",
                "GATE_KEEPING",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "gateKeeping",
                        "name", "Gatekeeping",
                        "workingDaysAllowed", 2,
                        "processCategories", "gateKeeping"
                    )
                )
            ),
            Arguments.of(
                "sendToGateKeeper",
                "GATE_KEEPING",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "gateKeeping",
                        "name", "Gatekeeping",
                        "workingDaysAllowed", 2,
                        "processCategories", "gateKeeping"
                    )
                )
            ),
            Arguments.of(
                "managerOrders",
                null,
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "reviewOder",
                        "name", "Review Order",
                        "workingDaysAllowed", 1,
                        "processCategories", "judgeReviewOrder"
                    )
                )
            ),
            Arguments.of(
                "managerOrders",
                null,
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "reviewOder",
                        "name", "Review Order",
                        "workingDaysAllowed", 1,
                        "processCategories", "judgeReviewOrder"
                    )
                )
            ),
            Arguments.of(
                "sendToAdmin",
                null,
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "serveOrder",
                        "name", "Serve order",
                        "workingDaysAllowed", 5,
                        "processCategories", "serveOrder"
                    )
                )
            ),
            Arguments.of(
                "sendToAdmin",
                null,
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "serveOrder",
                        "name", "Serve order",
                        "workingDaysAllowed", 5,
                        "processCategories", "serveOrder"
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
                        "taskId", "reviewCorrespondence",
                        "name", "Review correspondence",
                        "workingDaysAllowed", 5,
                        "processCategories", "courtAdminCorrespondence"
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
                        "taskId", "reviewCorrespondence",
                        "name", "Review correspondence",
                        "workingDaysAllowed", 5,
                        "processCategories", "courtAdminCorrespondence"
                    )
                )
            ),
            Arguments.of(
                "manageDocumentsByJudicial",
                null,
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "reviewCorrespondencyByJudiciary",
                        "name", "Review Correspondence Judiciary",
                        "workingDaysAllowed", 1,
                        "processCategories", "judicialCorrespondence"
                    )
                )
            ),
            Arguments.of(
                "manageDocumentsByJudicial",
                null,
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "reviewCorrespondencyByJudiciary",
                        "name", "Review Correspondence Judiciary",
                        "workingDaysAllowed", 1,
                        "processCategories", "judicialCorrespondence"
                    )
                )
            ),
            Arguments.of(
                "sendAndReplyToMessages",
                null,
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "reviewCorrespondencyByJudiciary",
                        "name", "Create Order",
                        "workingDaysAllowed", 1,
                        "processCategories", "judgeCreateOrder"
                    )
                )
            ),
            Arguments.of(
                "sendAndReplyToMessages",
                null,
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "reviewCorrespondencyByJudiciary",
                        "name", "Create Order",
                        "workingDaysAllowed", 1,
                        "processCategories", "judgeCreateOrder"
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

package uk.gov.hmcts.reform.prl.taskconfiguration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DmnDecisionTable {
    WA_TASK_ALLOWED_DAYS_PRL("wa-task-allowed-days-prl", "wa-task-allowed-days-prl.dmn"),
    WA_TASK_CANCELLATION_PRL("wa-task-cancellation-prl", "wa-task-cancellation-prl.dmn"),
    WA_TASK_COMPLETION_PRL("wa-task-completion-prl", "wa-task-completion-prl.dmn"),
    WA_TASK_CONFIGURATION_PRL("wa-task-configuration-prl", "wa-task-configuration-prl.dmn"),
    WA_TASK_INITIATION_PRL("wa-task-initiation-prl", "wa-task-initiation-prl.dmn"),
    WA_TASK_PERMISSIONS_PRL("wa-task-permissions-prl", "wa-task-permissions-prl.dmn");

    @JsonValue
    private final String key;
    private final String fileName;

    DmnDecisionTable(String key, String fileName) {
        this.key = key;
        this.fileName = fileName;
    }

    public String getKey() {
        return key;
    }

    public String getFileName() {
        return fileName;
    }
}

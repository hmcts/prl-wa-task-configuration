package uk.gov.hmcts.reform.prl.taskconfiguration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DmnDecisionTable {

    WA_TASK_CANCEL("wa-task-cancellation-privatelaw-prlapps", "wa-task-cancellation-privatelaw-prlapps.dmn"),
    WA_TASK_COMPLETION("wa-task-completion-privatelaw-prlapps", "wa-task-completion-privatelaw-prlapps.dmn"),
    WA_TASK_CONFIGS("wa-task-configuration-privatelaw-prlapps", "wa-task-configuration-privatelaw-prlapps.dmn"),
    WA_TASK_INITIATION("wa-task-initiation-privatelaw-prlapps", "wa-task-initiation-privatelaw-prlapps.dmn"),
    WA_TASK_PERMISSION("wa-task-permissions-privatelaw-prlapps", "wa-task-permissions-privatelaw-prlapps.dmn"),
    WA_TASK_TASKTYPEFILTER("wa-task-types-privatelaw-prlapps", "wa-task-types-privatelaw-prlapps.dmn"),

    WA_TASK_COMPLETION_EXCEPTION_RECORD("wa-task-completion-privatelaw-privatelaw_exceptionrecord",
                                        "wa-task-completion-privatelaw-privatelaw_exceptionrecord.dmn"),
    WA_TASK_CONFIGS_EXCEPTION_RECORD("wa-task-configuration-privatelaw-privatelaw_exceptionrecord",
                                     "wa-task-configuration-privatelaw-privatelaw_exceptionrecord.dmn"),
    WA_TASK_INITIATION_EXCEPTION_RECORD("wa-task-initiation-privatelaw-privatelaw_exceptionrecord",
                                        "wa-task-initiation-privatelaw-privatelaw_exceptionrecord.dmn"),
    WA_TASK_PERMISSION_EXCEPTION_RECORD("wa-task-permissions-privatelaw-privatelaw_exceptionrecord",
                                        "wa-task-permissions-privatelaw-privatelaw_exceptionrecord.dmn"),
    WA_TASK_TASKTYPEFILTER_EXCEPTION_RECORD("wa-task-types-privatelaw-privatelaw_exceptionrecord",
                                            "wa-task-types-privatelaw-privatelaw_exceptionrecord.dmn");

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

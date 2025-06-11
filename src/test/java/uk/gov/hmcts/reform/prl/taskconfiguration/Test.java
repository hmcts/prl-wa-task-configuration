package uk.gov.hmcts.reform.prl.taskconfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Test {
    private static String data = """
                                      {
                                          "Data": {
                                            "caseNoteId": "19842f84-faa2-4928-bc8d-928b0321b346"
                                          },
                                          "Definition": {
                                            "caseNoteId": {
                                              "type": "SimpleText",
                                              "subtype": "Text",
                                              "typeDef": null,
                                              "originalId": "caseNoteId"
                                            }
                                          }
                                        }
                                     """;

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            TypeReference<HashMap<String, Object>> typeRef = new TypeReference<>() {
            };
            HashMap<String, Object> stringObjectHashMap = mapper.readValue(data, typeRef);
            System.out.println(stringObjectHashMap);
        } catch (IOException exp) {
            exp.printStackTrace();
        }
    }
}

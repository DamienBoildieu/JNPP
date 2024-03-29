package jnpp.service.dto;

import java.io.IOException;
import java.util.List;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;
import org.codehaus.jackson.map.ObjectMapper;

//@JsonTypeInfo(include=As.WRAPPER_OBJECT, use=Id.NAME)
public abstract class AbstractDTO {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    public String toJson() {
        try {
            return MAPPER.writeValueAsString(this);
        } catch (IOException e) {
            return "";
        }
    }
    
    public static String toJson(List<? extends AbstractDTO> dtos) {
        try {
            return MAPPER.writeValueAsString(dtos);
        } catch (IOException e) {
            return "";
        }
    }
    
}

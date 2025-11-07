package org.demo.soap.util;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JsonFormatter {

    public String format(Object object) {

        String formattedOutput = "";
        try {
            formattedOutput = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);
        }catch(JsonProcessingException js) {
            Log.error(js);
        }
        return formattedOutput;

    }

}
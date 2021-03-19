/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.erika.javafx.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import hr.erika.javafx.data.Pawn;
import hr.erika.javafx.data.PawnStatus;
import hr.erika.javafx.state.State;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ER
 */
public class JsonUtils {

    public static String toJsonString(State state) {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Pawn.class, new PawnSerializer());
        mapper.registerModule(module);
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(state);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(JsonUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static State toState(String json) {

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Pawn.class, new PawnDeserializer());
        mapper.registerModule(module);

        try {
            return mapper.readValue(json, State.class);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(JsonUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}

class PawnSerializer extends StdSerializer<Pawn> {

    public PawnSerializer() {
        this(null);
    }

    public PawnSerializer(Class<Pawn> t) {
        super(t);
    }

    @Override
    public void serialize(
            Pawn value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeNumberField("status", value.getStatus().getValue());
        jgen.writeStringField("shapeId", value.getShapeId());
        jgen.writeNumberField("column", FindElementsPosition.getColumn(value.getCircle()));
        jgen.writeNumberField("row", FindElementsPosition.getRow(value.getCircle()));
        jgen.writeEndObject();
    }
}

class PawnDeserializer extends StdDeserializer<Pawn> {

    public PawnDeserializer() {
        this(null);
    }

    public PawnDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Pawn deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        int id = (Integer) ((IntNode) node.get("id")).numberValue();
        int statusValue = (Integer) ((IntNode) node.get("status")).numberValue();
        String shapeId = node.get("shapeId").asText();
        int column = (Integer) ((IntNode) node.get("column")).numberValue();
        int row = (Integer) ((IntNode) node.get("row")).numberValue();

        return new Pawn(id, PawnStatus.forValue(statusValue), shapeId, column, row);
    }
}

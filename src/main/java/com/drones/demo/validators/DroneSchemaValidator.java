package com.drones.demo.validators;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.SpecVersionDetector;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@Service
public class DroneSchemaValidator {
  @Getter private final ObjectMapper mapper = new ObjectMapper();

  protected JsonNode getJsonNodeFromClasspath(String name) throws IOException {
    InputStream is1 = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
    return mapper.readTree(is1);
  }

  public JsonNode getJsonNodeFromStringContent(String content) throws IOException {
    return mapper.readTree(content);
  }

  protected JsonNode getJsonNodeFromUrl(String url) throws IOException {
    return mapper.readTree(new URL(url));
  }

  public JsonSchema getJsonSchemaFromClasspath(String name) {
    JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
    InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
    return factory.getSchema(is);
  }

  protected JsonSchema getJsonSchemaFromStringContent(String schemaContent) {
    JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
    return factory.getSchema(schemaContent);
  }

  protected JsonSchema getJsonSchemaFromUrl(String uri) throws URISyntaxException {
    JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
    return factory.getSchema(new URI(uri));
  }

  protected JsonSchema getJsonSchemaFromJsonNode(JsonNode jsonNode) {
    JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
    return factory.getSchema(jsonNode);
  }

  // Automatically detect version for given JsonNode
  protected JsonSchema getJsonSchemaFromJsonNodeAutomaticVersion(JsonNode jsonNode) {
    JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersionDetector.detect(jsonNode));
    return factory.getSchema(jsonNode);
  }
}

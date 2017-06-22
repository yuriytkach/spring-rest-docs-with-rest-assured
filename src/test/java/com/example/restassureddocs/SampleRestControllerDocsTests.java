package com.example.restassureddocs;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import static com.example.restassureddocs.utils.FileUtils.readSystemResource;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

@RunWith(JUnitParamsRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SampleRestControllerDocsTests {

    @ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

    private static final String REST_REQUEST_PATH = "/process";
    private static final String PREFIX = "restdocs/";

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    private RequestSpecification documentationSpec;

    @Before
    public void setUp() {
        this.documentationSpec = new RequestSpecBuilder()
            .addFilter(documentationConfiguration(restDocumentation))
            .build();
    }

    @LocalServerPort
    private void initRestAssured(final int localPort) {
        RestAssured.port = localPort;
    }

    @Test
    @Parameters({
        "request-valid.json, process-valid, 200",
        "request-invalid.json, process-invalid, 200"
    })
    public void testProcess(final String requestResourceLocation, final String snippetFolderPrefix,
                            final int expectedStatus) {
        given(this.documentationSpec)
            .accept(APPLICATION_JSON.toString())
            .contentType(APPLICATION_JSON.toString())
            .body(readSystemResource(PREFIX + requestResourceLocation))
            .filter(document(snippetFolderPrefix + "-request", documentRequestFields()))
            .filter(document(snippetFolderPrefix + "-response", documentResponseFields()))
            .when()
            .post(REST_REQUEST_PATH)
            .then()
            .assertThat().statusCode(is(expectedStatus));
    }

    private RequestFieldsSnippet documentRequestFields() {


        return requestFields(
            fieldWithPath("id").description("Id of the input that is bigger than 0"),
            fieldWithPath("name").description("Name of the input")
        );
    }

    private ResponseFieldsSnippet documentResponseFields() {
        return responseFields(
            fieldWithPath("valid").description("Indicated whether input message was valid"),
            fieldWithPath("message").description("Some message about the request processing")
        );
    }
}

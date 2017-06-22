# spring-rest-docs-with-rest-assured

Sample project that shows the use of [Spring REST Docs](https://projects.spring.io/spring-restdocs/) with [REST Assured](http://rest-assured.io/) library.

The output produced by rest docs can be viewed here: https://yuriytkach.github.io/spring-rest-docs-with-rest-assured/

To run the code, checkout, cd to the directory, and execute: 

`./gradlew asciidoctor`

This will execute tests and produce the html file documenting rest service. Search for the file in `build/asciidoc/html5/index.html`

You can also run the rest web service by executing:

`./gradlew bootRun`

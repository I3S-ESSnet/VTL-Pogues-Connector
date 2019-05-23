# VTL-Test

Test of a VTL connector for Pogues questionnaire descriptions


## Usage with java-vtl-tools

Checkout `https://github.com/statisticsnorway/java-vtl-tools.git`

Add the connector as a dependency in the pom.xml

```
<dependency>
    <groupId>fr.insee.vtl</groupId>
    <artifactId>pogues-connector</artifactId>
    <version>0.1</version>
</dependency>
```

Edit the `no.ssb.vtl.tools.rest.Application`

```
@Bean
    @Autowired
    List<Connector> getConnectors() {
        // ...
        connectors.add(new PoguesConnector());
    }
```

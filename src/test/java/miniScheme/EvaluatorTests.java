package miniScheme;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static java.util.stream.Collectors.joining;

class EvaluatorTests {

  private Evaluator evaluator;
  private Map<String, Object> env;
  private List<Object> sample;

  @BeforeEach void init() {
    this.evaluator = new Evaluator();
    this.env = GlobalEnvironment.build();
    this.sample = List.of("list", 3.1416,"Test",400);
  }

  @Test void multiplicationTest() {
    List<Object> program = List.of("*", 2.0, 3.0, 4.0);
    Object result = evaluator.eval(program, env);
    assertEquals(24.0, result);
  }

  @Test void numberVariableDefinitionTest() {
    List<Object> program = List.of("define", "pi", 3.141592);
    Object result = evaluator.eval(program, env);
    assertEquals(3.141592, env.get("pi"));
  }

  @Test void stringVariableDefinitionTest() {
    List<Object> program = List.of("define", "foo", "bar");
    Object result = evaluator.eval(program, env);
    assertEquals("bar", env.get("foo"));
  }

  @Test void listVariableDefinitionTest() {
    List<Object> program =
            List.of("begin",
                    List.of("define", "listTest", (List.of("list","foo", "bar")))
            );
    Object result = evaluator.eval(program, env);
    assertEquals( List.of("foo","bar"), env.get("listTest"));
  }

  @Test void variableDefinitionTest() {
    List<Object> program =
            List.of("begin",
                    List.of("define", "var1", "value"),
                    List.of("define", "var2", "var1")
            );
    Object result = evaluator.eval(program, env);
    assertEquals("value", env.get("var2"));
  }

  @Test void variableUseTest() {
    List<Object> program = List.of(
        "begin",
        List.of("define", "pi", 3.141592),
        List.of("*", "pi", 100.0));
    Object result = evaluator.eval(program, env);
    assertEquals(314.1592, result);
  }

  @Test void lambdaExpressionTest() {
    List<Object> program = List.of("define", "circle-area", List.of("lambda", List.of("r"), List.of("*", "pi", "r", "r")));
    Object result = evaluator.eval(program, env);
    assertNotNull(env.get("circle-area"));
  }

  @Test void anonymousLambdaTest() {
    List<Object> program = List.of(
        "begin",
          List.of("define", "pi", 3.141592),
          List.of(List.of("lambda", List.of("r"), List.of("*", "pi", "r", "r")), 10.0));
    Object result = evaluator.eval(program, env);
    assertEquals(314.1592, result);
  }

  @Test void lambdaDefinitionTest() {
    List<Object> program = List.of(
        "begin",
          List.of("define", "pi", 3.141592),
          List.of("define", "circle-area", List.of("lambda", List.of("r"), List.of("*", "pi", "r", "r"))),
          List.of("circle-area", 10.0));
    Object result = evaluator.eval(program, env);
    assertEquals(314.1592, result);
  }

  @Test void lessThanTest() {
    assertTrue((boolean) evaluator.eval(List.of("<", 1.0, 2.0), env));
    assertTrue((boolean) evaluator.eval(List.of("<", 1.0, 2.0, 3.0), env));
    assertFalse((boolean) evaluator.eval(List.of("<", 2.0, 1.0), env));
    assertFalse((boolean) evaluator.eval(List.of("<", 2.0, 3.0, 1.0), env));
  }

  @Test void substractionTest() {
    assertEquals(2.0, evaluator.eval(List.of("-", 3.0, 1.0), env));
    assertEquals(2.0, evaluator.eval(List.of("-", 5.0, 2.0, 1.0), env));
  }

  @Test void ifTest() {
    List<Object> program = List.of("if", List.of("<", 1.0, 2.0), List.of("*", 2.0, 2.0), List.of("*", 3.0, 3.0));
    Object result = evaluator.eval(program, env);
    assertEquals(4.0, result);
  }

  @Test void elseTest() {
    List<Object> program = List.of("if", List.of("<", 2.0, 1.0), List.of("*", 2.0, 2.0), List.of("*", 3.0, 3.0));
    Object result = evaluator.eval(program, env);
    assertEquals(9.0, result);
  }

  @Test void recursionTest() {
    List<Object> program =
      List.of("begin",
        List.of("define", "facto", List.of("lambda", List.of("n"),
          List.of("if", List.of("<", "n", 2.0),
            1.0,
            List.of("*", "n", List.of("facto", List.of("-", "n", 1.0)))))),
        List.of("facto", 5.0));
    Object result = evaluator.eval(program, env);
    assertEquals(120.0, result);
  }

  @Test void beginTest(){
    List<Object> program = List.of("begin", sample);
    Object result = evaluator.eval(program, env);
    assertEquals(List.of(3.1416,"Test",400), result);
  }

  @Test void countTest() {
    List<Object> program = List.of("count", sample);
    Object result = evaluator.eval(program, env);
    assertEquals(3.0, result);
  }

  @Test void headTest() {
    List<Object> program = List.of("head", sample);
    Object result = evaluator.eval(program, env);
    assertEquals(3.1416, result);
  }

  @Test void tailTest() {
    List<Object> program = List.of("tail", sample);
    Object result = evaluator.eval(program, env);
    assertEquals(List.of("Test",400), result);
  }

  @Test void logicTrueAndTest(){
    List<Object> program = List.of("and", true, true);
    Object result = evaluator.eval(program, env);
    assertTrue((boolean) result);
  }

  @Test void logicTrueNotTest(){
    List<Object> program = List.of("not", false);
    Object result = evaluator.eval(program, env);
    assertTrue((boolean) result);
  }

  @Test void filterNumberTest() {
    List<Object> program = List.of("begin",
            List.of("define","listTest", List.of("list", 30.0,40.5)),
            List.of("define", "filterTest",
                    List.of("lambda", List.of("elem"), List.of("eq", 30.0, "elem"))),
            List.of("filter","filterTest", "listTest"));
    Object result = evaluator.eval(program, env);
    assertEquals(List.of(30.0), result);
  }
}

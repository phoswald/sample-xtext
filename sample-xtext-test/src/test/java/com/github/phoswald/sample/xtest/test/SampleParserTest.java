package com.github.phoswald.sample.xtest.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

class SampleParserTest {

    private final SampleParser testee = new SampleParser();

    @Test
    void run_valid_success() {
        List<String> result = testee.run("""
                Hello Philip!
                Hello  World  !
                """);
        assertThat(result, hasSize(2));
        assertThat(result, containsInRelativeOrder("Philip", "World"));
    }

    @Test
    void run_invalid_execpetion() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> testee.run("Hello Du Laberi!"));
        assertThat(e.getMessage(), equalTo("Syntax error: [extraneous input 'Laberi' expecting '!']"));
    }
}

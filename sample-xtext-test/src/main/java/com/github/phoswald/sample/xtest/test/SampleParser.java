package com.github.phoswald.sample.xtest.test;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Collections.emptyMap;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.github.phoswald.sample.mydsl.MyDslStandaloneSetup;
import com.github.phoswald.sample.mydsl.myDsl.Greeting;
import com.github.phoswald.sample.mydsl.myDsl.Model;
import com.google.inject.Injector;

public class SampleParser {

    private static final Injector injector = new MyDslStandaloneSetup().createInjectorAndDoEMFRegistration();

    public static void main(String[] args) {
        new SampleParser().run(System.in).forEach(System.out::println);
    }

    public List<String> run(String input) {
        return run(new ByteArrayInputStream(input.getBytes(UTF_8)));
    }

    public List<String> run(InputStream input) {
        return parse(input).getGreetings().stream().map(Greeting::getName).toList();
    }

    private Model parse(InputStream input) {
        try {
            XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
            Resource resource = resourceSet.createResource(URI.createURI("dummy:/model.mydsl"));
            resource.load(input, emptyMap());
            if(resource.getErrors().isEmpty()) {
                return (Model) resource.getContents().getFirst();
            } else {
                List<String> messages = resource.getErrors().stream().map(Diagnostic::getMessage).toList();
                throw new IllegalArgumentException("Syntax error: " + messages);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Internal error while parsing string", e);
        }
    }
}

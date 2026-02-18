
# sample-xtext

A simple Xtext DSL, **Maven only**, without any Tycho, Eclipse, P2 stuff

~~~
$ mvn clean verify
$ echo "Hello Foo! Hello Bar!" | \
  java \
    -cp 'sample-xtext-test/target/sample-xtext-test-1.0.0-SNAPSHOT-dist/lib/*' \
    com.github.phoswald.sample.xtest.test.SampleParser
~~~

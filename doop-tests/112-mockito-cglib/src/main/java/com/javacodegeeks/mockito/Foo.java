package com.javacodegeeks.mockito;

public interface Foo {
	String HELLO_WORLD = "Hello World";
	String ANY_NEW_TOPICS = "Are there any new topics?";
	String WHAT_IS_TODAYS_TOPIC = "What is todays topic?";
	String YES_NEW_TOPICS_AVAILABLE = "Yes";
	String NO_NEW_TOPIC = "No";
	String TOPIC_MOCKITO = "Mockito";
	String greet();
	int getPrice(String tutorial);
	String question(String question);
	String questionStrictly(String question) throws InvalidQuestion;
	void bye();
}

package com.pattern.observer.gperadvice;

/**
 *
 */
public class ObserverTest {
    public static void main(String[] args) {
        GPer gper = GPer.getInstance();
        Teacher  toms = new Teacher(" toms");
        Teacher mic = new Teacher("Mic");


        Question question = new Question();
        question.setUserName("小明");
        question.setContent("观察者设计模式适用于哪些场景？");
        gper.addObserver( toms);
        gper.addObserver(mic);
        gper.publishQuestion(question);


    }

}

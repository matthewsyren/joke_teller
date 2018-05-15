package com.matthewsyren.jokeprovider;

public class JokeProvider {
    private static final String[] sJokes = {
            "Why do Java developers wear glasses? \n\nBecause they can't C#",
            "What is a programmer's favourite place to hang out? \n\nThe Foo Bar",
            "Why did the programmer quit his job? \n\nBecause he didn't get arrays",
            "There are only 10 types of people... \n\nThose that understand binary and those that don't",
            "Why do programmers always get confused between Halloween and Christmas? \n\nBecause OCT 31 = DEC 25"};

    //Returns a random joke
    public static String getJoke(){
        int index = (int) (Math.random() * sJokes.length);
        return sJokes[index];
    }
}
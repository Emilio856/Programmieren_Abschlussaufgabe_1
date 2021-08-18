package edu.kit.informatik;

public class Word {
    private final String startLanguage;
    private final String targetLanguage;
    
    public Word(String startLanguage, String targetLanguage) {
        this.startLanguage = startLanguage;
        this.targetLanguage = targetLanguage;
    }
    
    public String getStartLanguage() {
        return this.startLanguage;
    }
    
    public String getTargetLanguage() {
        return this.targetLanguage;
    }
}
package com.h3xstream.scriptgen;

import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

public class LanguageOption {

    //List of script templates available
    public static final LanguageOption PYTHON_REQUEST = new LanguageOption("Python (requests)", "", SyntaxConstants.SYNTAX_STYLE_PYTHON);
    public static final LanguageOption RUBY_NET_HTTP = new LanguageOption("Ruby (Net::HTTP)", "", SyntaxConstants.SYNTAX_STYLE_RUBY);
    public static final LanguageOption PERL_LWP = new LanguageOption("Perl (LWP)", "", SyntaxConstants.SYNTAX_STYLE_PERL);
    public static final LanguageOption PHP_CURL = new LanguageOption("PHP (cURL)", "", SyntaxConstants.SYNTAX_STYLE_PHP);

    public static final LanguageOption[] values = {PYTHON_REQUEST,RUBY_NET_HTTP,PERL_LWP,PHP_CURL};

    //Properties of each language
    private final String title;
    private final String template;
    private final String syntax;

    private LanguageOption(String title,String template,String syntax) {
        this.title = title;
        this.template = template;
        this.syntax = syntax;
    }

    @Override
    public String toString() {
        return title;
    }

    public String getTitle() {
        return title;
    }

    public String getTemplate() {
        return template;
    }

    public String getSyntax() {
        return syntax;
    }
}

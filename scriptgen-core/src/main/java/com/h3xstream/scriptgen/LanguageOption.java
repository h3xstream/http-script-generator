package com.h3xstream.scriptgen;

import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

/**
 * This class can be seen as an Enum. It contains all the languages supported.
 */
public class LanguageOption {

    private static final String BASE_TPL_CLASSPATH = "com/h3xstream/scriptgen/templates/";

    //List of script templates available
    public static final LanguageOption PYTHON_REQUEST = new LanguageOption("Python (Requests)", "python", BASE_TPL_CLASSPATH + "python_requests.tpl", SyntaxConstants.SYNTAX_STYLE_PYTHON, "py");
    public static final LanguageOption RUBY_NET_HTTP = new LanguageOption("Ruby (Net::HTTP)", "ruby", BASE_TPL_CLASSPATH + "ruby_nethttp.tpl", SyntaxConstants.SYNTAX_STYLE_RUBY , "rb");
    public static final LanguageOption PERL_LWP = new LanguageOption("Perl (LWP)", "perl", BASE_TPL_CLASSPATH + "perl_lwp.tpl", SyntaxConstants.SYNTAX_STYLE_PERL, "pl");
    public static final LanguageOption PHP_CURL = new LanguageOption("PHP (cURL)", "php", BASE_TPL_CLASSPATH + "php_curl.tpl", SyntaxConstants.SYNTAX_STYLE_PHP, "php");
	public static final LanguageOption POWERSHELL = new LanguageOption("PowerShell (WebRequestSession)", "powershell", BASE_TPL_CLASSPATH + "psh_webrequest.tpl", SyntaxConstants.SYNTAX_STYLE_UNIX_SHELL, "ps1");

    public static final LanguageOption[] values = {PYTHON_REQUEST, RUBY_NET_HTTP, PERL_LWP, PHP_CURL, POWERSHELL};

    //Properties of each language
    private final String title;
    private final String language;
    private final String template;
    private final String syntax;
    private final String extension;

    private LanguageOption(String title, String language, String template, String syntax,String extension) {
        this.title = title;
        this.language = language;
        this.template = template;
        this.syntax = syntax;
        this.extension = extension;
    }

    @Override
    public String toString() {
        return title;
    }

    public String getTitle() {
        return title;
    }

    public String getLanguage() {
        return language;
    }

    public String getTemplate() {
        return template;
    }

    public String getSyntax() {
        return syntax;
    }


    public String getExtension() {
        return extension;
    }
}

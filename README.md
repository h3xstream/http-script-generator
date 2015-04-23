#Reissue Request Scripter (Burp plugin) [![Build Status](https://travis-ci.org/h3xstream/http-script-generator.png)](https://travis-ci.org/h3xstream/http-script-generator)

This extension generates scripts to reissue a selected request. The scripts can be run outside of Burp. It can be useful to script attacks such as second order SQL injection, padding oracle, fuzzing encoded value, etc.

## License

This software is release under [LGPL](http://www.gnu.org/licenses/lgpl.html).

## Downloads

(Last updated : June 8, 2014)

ZAP plugin : [Download](https://github.com/h3xstream/http-script-generator/blob/gh-pages/releases/zap/scriptgen-alpha-3.zap?raw=true)

Burp Suite Pro plugin : [Download](https://github.com/h3xstream/http-script-generator/blob/gh-pages/releases/burp/scriptgen-burp-plugin-3.jar?raw=true)

## Contributors

-   [mattpresson](https://github.com/mattpresson) : Addition of [PowerShell support](https://github.com/h3xstream/http-script-generator/commit/37cdbbb8e4bcd9ab47ec8b0f5974e29b24737e64)

## Screenshots

### Context Menu

![Reissue Request Scripter: Context Menu](http://h3xstream.github.io/http-script-generator/screenshots/1_context_menu.png)

### Main Window

![Reissue Request Scripter: Main Window](http://h3xstream.github.io/http-script-generator/screenshots/2_main_window.png)

### Language Options

Script for various language are available : Python, Ruby, Perl, PowerShell and JavaScript.

![Reissue Request Scripter: Language Options](http://h3xstream.github.io/http-script-generator/screenshots/3_languages.png)

### More Options

Since version 4.0, common script variations can be applied. These variation include: proxy redirection, reduction of "noisy" headers and disabling SSL/TLS verification.

![Reissue Request Scripter: Language Options](http://h3xstream.github.io/http-script-generator/screenshots/4_settings.png)

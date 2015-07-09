![](https://raw.githubusercontent.com/tombensve/MDD-Dev/master/MarkdownDoc.png)

Copyright (C) 2012 Natusoft AB

__Version:__ 1.4

__Author:__ Tommy Svensson (tommy@natusoft.se)

----

_A tool for generating HTML and PDF from markdown for the purpose of documentation._

[Introduction](https://github.com/tombensve/MarkdownDoc/blob/master/Docs/MarkdownDoc.md)

[Maven usage](https://github.com/tombensve/MarkdownDoc/blob/master/MavenPlugin/docs/MarkdownDoc-Maven-Plugin.md)

[Command line usage](https://github.com/tombensve/MarkdownDoc/blob/master/CommandLine/docs/MarkdownDoc-CommandLine.md)
\[[markdowndoc-cmd-line-1.3.9.exec.jar download](http://dl.bintray.com/tommy/maven/se/natusoft/tools/doc/markdowndoc/markdowndoc-cmd-line/1.3.9/markdowndoc-cmd-line-1.3.9.exec.jar)\]

[Library](https://github.com/tombensve/MarkdownDoc/blob/master/Library/docs/MarkdownDoc-Library.md)

[Editor](https://github.com/tombensve/MarkdownDoc/blob/master/Editor/docs/MarkdownDoc-Editor.md) \[[MarkdownDocEditor-1.3.9.App.jar](http://dl.bintray.com/tommy/maven/se/natusoft/tools/doc/markdowndoc/MarkdownDocEditor/1.3.9/MarkdownDocEditor-1.3.9.App.jar)\].

[Licenses](https://github.com/tombensve/MarkdownDoc/blob/master/Docs/licenses.md)

[PDF Version](https://github.com/tombensve/MarkdownDoc/blob/master/Docs/MarkdownDoc-User-Guide.pdf)

----

__New Features__:

* Now supports MSS, a JSON based markdown style sheet for PDF generator where you can steer fonts, sizes and colors. 

* Also supports external .ttf, .otf, ... (whatever iText supports) fonts via MSS.

* Bug fixes:

  * No longer tries to format text withing `...` parts of a paragraph.

----

Available through maven at bintray JCentral: [http://jcenter.bintray.com](http://jcenter.bintray.com). MarkdownDoc on [Bintray](https://bintray.com/tommy/maven/MarkdownDoc).

[Maven repo setup](https://github.com/tombensve/CommonStuff/blob/master/docs/MavenRepository.md)

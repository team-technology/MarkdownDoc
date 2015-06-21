package se.natusoft.doc.markdown.generator.pdf

import com.itextpdf.text.Font
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import se.natusoft.doc.markdown.generator.styles.MSSColorPair
import se.natusoft.doc.markdown.generator.styles.MSSFont
import se.natusoft.doc.markdown.generator.styles.MSSFontStyle

/**
 * Creates a PDF font from an MSSFont.
 */
@CompileStatic
@TypeChecked
class PDFFont extends Font {
    //
    // Private Members
    //

    PDFFont(@NotNull MSSFont mssFont, @Nullable MSSColorPair mssColorPair) {
        setFamily(mssFont.family)
        setSize((float)mssFont.size)
        setStyle(toStyle(mssFont.style))
        setColor(new PDFColor(mssColorPair.foreground))
    }

    private static final int toStyle(MSSFontStyle fontStyle) {
        int result = NORMAL
        switch (fontStyle) {
            case MSSFontStyle.NORMAL:
                result = NORMAL
                break
            case MSSFontStyle.BOLD:
                result = BOLD
                break;
            case MSSFontStyle.ITALIC:
                result = ITALIC
                break;
            case MSSFontStyle.UNDERLINE:
                result = UNDERLINE
        }

        return result
    }
}

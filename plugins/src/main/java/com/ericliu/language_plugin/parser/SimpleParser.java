// This is a generated file. Not intended for manual editing.
package com.ericliu.language_plugin.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LightPsiParser;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;

import static com.ericliu.language_plugin.psi.SimpleTypes.COMMENT;
import static com.ericliu.language_plugin.psi.SimpleTypes.KEY;
import static com.ericliu.language_plugin.psi.SimpleTypes.PROPERTY;
import static com.ericliu.language_plugin.psi.SimpleTypes.SEPARATOR;
import static com.ericliu.language_plugin.psi.SimpleTypes.VALUE;
import static com.intellij.lang.parser.GeneratedParserUtilBase.Parser;
import static com.intellij.lang.parser.GeneratedParserUtilBase.TRUE_CONDITION;
import static com.intellij.lang.parser.GeneratedParserUtilBase._COLLAPSE_;
import static com.intellij.lang.parser.GeneratedParserUtilBase._NONE_;
import static com.intellij.lang.parser.GeneratedParserUtilBase._NOT_;
import static com.intellij.lang.parser.GeneratedParserUtilBase.adapt_builder_;
import static com.intellij.lang.parser.GeneratedParserUtilBase.consumeToken;
import static com.intellij.lang.parser.GeneratedParserUtilBase.consumeTokens;
import static com.intellij.lang.parser.GeneratedParserUtilBase.current_position_;
import static com.intellij.lang.parser.GeneratedParserUtilBase.empty_element_parsed_guard_;
import static com.intellij.lang.parser.GeneratedParserUtilBase.enter_section_;
import static com.intellij.lang.parser.GeneratedParserUtilBase.exit_section_;
import static com.intellij.lang.parser.GeneratedParserUtilBase.recursion_guard_;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class SimpleParser implements PsiParser, LightPsiParser {

    public ASTNode parse(IElementType t, PsiBuilder b) {
        parseLight(t, b);
        return b.getTreeBuilt();
    }

    public void parseLight(IElementType t, PsiBuilder b) {
        boolean r;
        b = adapt_builder_(t, b, this, null);
        Marker m = enter_section_(b, 0, _COLLAPSE_, null);
        if (t == PROPERTY) {
            r = property(b, 0);
        } else {
            r = parse_root_(t, b, 0);
        }
        exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
    }

    protected boolean parse_root_(IElementType t, PsiBuilder b, int l) {
        return simpleFile(b, l + 1);
    }

    /* ********************************************************** */
    // (KEY SEPARATOR VALUE?)|KEY
    public static boolean property(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "property")) { return false; }
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, PROPERTY, "<property>");
        r = property_0(b, l + 1);
        if (!r) { r = consumeToken(b, KEY); }
        exit_section_(b, l, m, r, false, recover_property_parser_);
        return r;
    }

    // KEY SEPARATOR VALUE?
    private static boolean property_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "property_0")) { return false; }
        boolean r;
        Marker m = enter_section_(b);
        r = consumeTokens(b, 0, KEY, SEPARATOR);
        r = r && property_0_2(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // VALUE?
    private static boolean property_0_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "property_0_2")) { return false; }
        consumeToken(b, VALUE);
        return true;
    }

    /* ********************************************************** */
    // !(KEY|SEPARATOR|COMMENT)
    static boolean recover_property(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "recover_property")) { return false; }
        boolean r;
        Marker m = enter_section_(b, l, _NOT_);
        r = !recover_property_0(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // KEY|SEPARATOR|COMMENT
    private static boolean recover_property_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "recover_property_0")) { return false; }
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, KEY);
        if (!r) { r = consumeToken(b, SEPARATOR); }
        if (!r) { r = consumeToken(b, COMMENT); }
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // (property|COMMENT)*
    static boolean simpleFile(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "simpleFile")) { return false; }
        int c = current_position_(b);
        while (true) {
            if (!simpleFile_0(b, l + 1)) { break; }
            if (!empty_element_parsed_guard_(b, "simpleFile", c)) { break; }
            c = current_position_(b);
        }
        return true;
    }

    // property|COMMENT
    private static boolean simpleFile_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "simpleFile_0")) { return false; }
        boolean r;
        Marker m = enter_section_(b);
        r = property(b, l + 1);
        if (!r) { r = consumeToken(b, COMMENT); }
        exit_section_(b, m, null, r);
        return r;
    }

    final static Parser recover_property_parser_ = new Parser() {
        public boolean parse(PsiBuilder b, int l) {
            return recover_property(b, l + 1);
        }
    };
}

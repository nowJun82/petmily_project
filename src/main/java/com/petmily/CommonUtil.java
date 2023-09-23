package com.petmily;

import com.petmily.board.Board;
import com.petmily.board.BoardRepository;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonUtil {
    @Autowired
    private BoardRepository boardRepository;
}

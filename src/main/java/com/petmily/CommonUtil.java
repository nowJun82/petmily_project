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


    public String markdown(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }



    public void dataCreaete() {
        String[] names = {"공지", "뉴스", "자유", "팁"};
        String[] codes = {"notification", "news", "free", "tip"};
        for (int i =0; i < 4; i++){
            Board board = new Board();
            board.setName(names[i]);
            board.setCode(codes[i]);
            boardRepository.save(board);
        }


    }
}

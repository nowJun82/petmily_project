package com.petmily.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {
    private final ArticleService articleService;
    @GetMapping("/board/notification")
    public String notification(Model model){
        List<Article> articleList = this.articleService.getList();
        model.addAttribute("articleList", articleList);
        return "Board/board_notification";
    }
    @GetMapping(value = "/article/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        return "article_detail";
    }
    @GetMapping("/board/free")
    public String free(){
        return "Board/board_free";
    }
    @GetMapping("/board/tip")
    public String tip() {
        return "Board/board_tip";
    }
}

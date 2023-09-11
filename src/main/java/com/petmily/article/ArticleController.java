package com.petmily.article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@RequiredArgsConstructor
@Controller
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;
    @GetMapping("/notification")
    public String notification(Model model){
        List<Article> articleList = this.articleService.getList();
        model.addAttribute("articleList", articleList);
        return "Article/board_notification";
    }
    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Article article = this.articleService.getArticle(id);
        model.addAttribute("article", article);
        return "Article/article_detail";
    }
    @GetMapping("/free")
    public String free(){
        return "Article/board_free";
    }
    @GetMapping("/tip")
    public String tip() {
        return "Article/board_tip";
    }
}

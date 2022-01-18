package com.github.bom4.springboot.web;

import com.github.bom4.springboot.service.posts.PostsService;
import com.github.bom4.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts",postsService.findAllDesc());
        return "index"; // src/main/resources/templates/index.mustache 호출
    }
    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }
    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto=postsService.findById(id);
        model.addAttribute("post",dto);
        return "posts-update";
    }

}

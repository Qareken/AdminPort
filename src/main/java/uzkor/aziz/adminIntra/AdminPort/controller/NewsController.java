package uzkor.aziz.adminIntra.AdminPort.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uzkor.aziz.adminIntra.AdminPort.domain.News;
import uzkor.aziz.adminIntra.AdminPort.domain.NewsFiles;
import uzkor.aziz.adminIntra.AdminPort.service.NewsService;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/news")
public class NewsController {



    @Autowired
     private NewsService newsService;
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @RequestMapping(value = "/updateNews/{id}",method = RequestMethod.GET)
    public String updateNews(@PathVariable("id") Long id, Model model){
        List<NewsFiles>newsFiles=newsService.findFilesByNewsId(id);
        News news=newsService.findById(id);
        model.addAttribute("news",news);
        model.addAttribute("newsFiles", newsFiles);
        return "updateBook";
    }
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String addNews(Model model){
        model.addAttribute("news",new News());
        model.addAttribute("newsFiles", new ArrayList<NewsFiles>());
        return "addNews";
    }
    @RequestMapping(value = "/newsList")
    public String newsList(Model model){

        List<News> newsList = newsService.findAll();
        model.addAttribute("newsList",newsList);

        return "newsList";
    }

    @PostMapping("updateNews")
    public String updateNewsPost(@ModelAttribute News news, Model model){

        News dbNews=newsService.update(news);



        if(dbNews!=null){

            return "redirect:newsList";
        }else{

            model.addAttribute("news", news);
            return "addBook";
        }
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveNews(@ModelAttribute News news, Model model){
        News dbNews=newsService.save(news);
        if(dbNews!=null){

            return "redirect:newsList";
        }else{

            model.addAttribute("news", news);
            return "addNews";
        }
    }
    @RequestMapping(value = "/newsInfo/{id}", method = RequestMethod.GET)
    public String newsInfo(@PathVariable Long id, Model model){

        List<NewsFiles>newsFiles=newsService.findFilesByNewsId(id);
        News news=newsService.findById(id);
        model.addAttribute("news", news);
        model.addAttribute("newsFiles", newsFiles);
        return "newsInfo";
    }
    @GetMapping(value = "/delete/{newsId}")
    public String deleteNews(@PathVariable Long newsId){
        newsService.deleteFilesByNewsID(newsId);
        newsService.deleteNews(newsId);
        return "redirect:newsList";
    }

}

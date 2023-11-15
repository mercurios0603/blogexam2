package com.korea.test.Book;

import com.korea.test.Post.Post;
import com.korea.test.Post.PostRepository;
import com.korea.test.Post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

  private final PostService postService;
  private final BookService bookService;

  @RequestMapping("/{bookId}")
  public String select(@PathVariable("bookId") Long bookId, Model model) {

    Book book = bookService.getBookById(bookId);
    List<Book> bookList = bookService.getBookList();

    model.addAttribute("bookList", bookList); // 모든 북 리스트
    model.addAttribute("postList", book.getPost()); // 선택된 북에 맞는 포스팅 리스트
    model.addAttribute("targetPost", book.getPost().get(0)); // 첫번째 포스트
    model.addAttribute("targetBook", book); // 선택된 북

    return "main";
  }

  @RequestMapping("/")
  public String main(Model model) {

    List<Post> postList = postService.getPostList();
    List<Book> bookList = bookService.getBookList();

    if(postList.isEmpty()) {
      postService.writePost(bookList.get(0));
      return "redirect:/";
    }

    model.addAttribute("bookList", bookList);
    model.addAttribute("postList", postList);
    model.addAttribute("targetPost", postList.get(0));
    model.addAttribute("targetBook", bookList.get(0));

    return "main";
  }

  @PostMapping("/write")
  public String write() {
    Book book = bookService.writeBook();
    postService.writePost(book);
    return "redirect:/";
  }
}

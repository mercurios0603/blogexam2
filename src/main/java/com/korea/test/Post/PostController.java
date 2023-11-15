package com.korea.test.Post;

import com.korea.test.Book.Book;
import com.korea.test.Book.BookRepository;
import com.korea.test.Book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;
  private final BookService bookService;

  @GetMapping("/")
  public String list(Model model) {

    List<Book> bookList = bookService.getBookList();

    if(bookList.isEmpty()) {
      bookService.writeBook();
      return "redirect:/";
    }

    Book targetBook = bookList.get(0);
    List<Post> postList = postService.getPostListByBook(targetBook);

    if(postList.isEmpty()) {
      postService.writePost(targetBook);
      return "redirect:/";
    }

    model.addAttribute("postList", postList);
    model.addAttribute("targetPost", postList.get(0));
    model.addAttribute("targetBook", targetBook);
    model.addAttribute("bookList", bookList);

    return "main";
  }

  @PostMapping("/write")
  public String write(Long bookId) {

    Book book = bookService.getBookById(bookId);
    postService.writePost(book);
    return "redirect:/";
  }

  @GetMapping("/detail/{id}")
  public String detail(Model model, @PathVariable("id") Long id) {
    Post post = this.postService.getPostById(id);
    model.addAttribute("postList", this.postService.getPostList());
    model.addAttribute("targetPost", post);

    List<Post> postList = postService.getPostListByBook(post.getBook());
    List<Book> bookList = bookService.getBookList();

    model.addAttribute("targetPost", post);
    model.addAttribute("postList", postList);
    model.addAttribute("bookList", bookList);
    model.addAttribute("targetBook", post.getBook());

    return "main";
  }

  @PostMapping("/update")
  public String update(Long id, String title, String content) {
    Post post = this.postService.getById(id);

    if(title.trim().isEmpty()) {
      title = "제목 없음";
    }

    post.setTitle(title);
    post.setContent(content);
    post.setCreateDate(LocalDateTime.now());
    postService.save(post);
    return "redirect:/detail/" + id;
  }

  @GetMapping("/delete/{id}")
  public String delete(@PathVariable("id") Long id) {
    Post post = this.postService.getById(id);
    postService.delete(post);
    return "redirect:/";
  }
}

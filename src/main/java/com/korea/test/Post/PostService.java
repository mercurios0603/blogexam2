package com.korea.test.Post;

import com.korea.test.Book.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
  private final PostRepository postRepository;

  public Post getById(Long id) {
    Optional<Post> PostOptional = postRepository.findById(id);
    if(PostOptional.isPresent()) {
      return PostOptional.get();
    }
    throw new IllegalArgumentException("해당 게시물은 존재하지 않습니다.");
  }

  public List<Post> getPostList() {
    return postRepository.findAll();
  }

  public void writePost(Book book) {
    Post p = new Post();
    p.setTitle("new title");
    p.setContent("new content");
    p.setCreateDate(LocalDateTime.now());
    p.setBook(book);
    this.postRepository.save(p);
  }

  public Post getPostById(Long id) {
    Optional<Post> notePageOptional = postRepository.findById(id);
    if(notePageOptional.isPresent()) {
      return notePageOptional.get();
    }

    throw new IllegalArgumentException("해당 게시물은 존재하지 않습니다.");
  }

  public List<Post> getPostListByBook(Book targetBook) {
    return postRepository.findByBook(targetBook);
  }

  public void save(Post post) {
    postRepository.save(post);
  }

  public void delete(Post post) { postRepository.delete(post); }
}

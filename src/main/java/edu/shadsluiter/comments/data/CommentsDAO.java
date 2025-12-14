package edu.shadsluiter.comments.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.shadsluiter.comments.model.AppComment;

public class CommentsDAO {

    private List<AppComment> comments = new ArrayList<>();
    private int nextId = 0;

    public CommentsDAO() {
        initialData();
    }

    private void initialData() {
        if (comments.size() > 0) {
            return;
        }

        AppComment comment1 = new AppComment(
                1,
                "Alice",
                "This is a great day to talk about life.",
                LocalDateTime.now()
        );
        AppComment comment2 = new AppComment(
                2,
                "Bob",
                "I agree with Alice!",
                LocalDateTime.now()
        );
        AppComment comment3 = new AppComment(
                3,
                "Charlie",
                "I feel like coding today.",
                LocalDateTime.now()
        );

        comments.add(comment1);
        comments.add(comment2);
        comments.add(comment3);
        this.nextId = 4;
    }

    public void addComment(AppComment comment) {
        comment.setId(nextId++);
        if (comment.getCreatedAt() == null) {
            comment.setCreatedAt(LocalDateTime.now());
        }
        comments.add(comment);
    }

    public void addComment(String author, String content) {
        AppComment newComment = new AppComment(
                nextId++,
                author,
                content,
                LocalDateTime.now()
        );
        comments.add(newComment);
    }

    public List<AppComment> getComments() {
        return comments;
    }

    public void clearComments() {
        comments.clear();
        nextId = 1;
    }

    public AppComment getCommentById(int id) {
        for (AppComment comment : comments) {
            if (comment.getId() == id) {
                return comment;
            }
        }
        return null;
    }

    public void updateComment(AppComment updatedComment) {
        for (int i = 0; i < comments.size(); i++) {
            if (comments.get(i).getId() == updatedComment.getId()) {
                comments.set(i, updatedComment);
                return;
            }
        }
    }

    public void deleteComment(int id) {
        comments.removeIf(comment -> comment.getId() == id);
    }

    // v4: search/filter helper
    public List<AppComment> searchComments(String searchTerm) {
        if (searchTerm == null || searchTerm.isBlank()) {
            return comments;
        }
        String lower = searchTerm.toLowerCase();
        List<AppComment> results = new ArrayList<>();
        for (AppComment comment : comments) {
            if (comment.getContent().toLowerCase().contains(lower)
                    || comment.getAuthor().toLowerCase().contains(lower)) {
                results.add(comment);
            }
        }
        return results;
    }
}

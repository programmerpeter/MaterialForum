package net.materialforum.entities;

import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import net.materialforum.utils.Database;

public class PostManager {
    
    private PostManager() {}
    
    public static PostEntity create(TopicEntity topic, UserEntity user, String text) {
        EntityManager entityManager = Database.getEntityManager();
        
        ForumEntity forum = topic.getForum();
        
        PostEntity post = new PostEntity();
        post.setTopic(topic);
        post.setUser(user);
        post.setText(text);
        
        entityManager.getTransaction().begin();
        
        entityManager.persist(post);
        
        topic.setLastPost(post);
        topic.incrementPostCount();
        entityManager.merge(topic);
        
        user.incrementPostCount();
        entityManager.merge(user);
        
        forum.setLastPost(post);
        forum.incrementPostCount();
        entityManager.merge(forum);
        while ((forum = forum.getParent()) != null) {
            forum.setLastPost(post);
            forum.incrementPostCount();
            entityManager.merge(forum);
        }
        
        entityManager.getTransaction().commit();
        entityManager.close();
        
        return post;
    }
    
    public static List<PostEntity> getPosts(TopicEntity topic) {
        EntityManager entityManager = Database.getEntityManager();
        List<PostEntity> posts = entityManager.createNamedQuery("Post.findByTopicId")
            .setParameter("topicId", topic.getId()).getResultList();
        entityManager.close();
        return posts;
    }
    
}

package net.materialforum.servlets;

import java.net.URLEncoder;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.materialforum.beans.NavigationBean;
import net.materialforum.entities.PostManager;
import net.materialforum.entities.TopicEntity;
import net.materialforum.entities.TopicManager;
import net.materialforum.utils.Validator;

@WebServlet("/topic/*")
public class TopicServlet extends BaseServlet {

    @Override
    protected void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] splitted = request.getRequestURI().split("/");
        String topicUrl = splitted[2];
        Long topicId = Long.parseLong(topicUrl.split("\\.")[0]);

        TopicEntity topic = TopicManager.findById(topicId);
        if (!URLEncoder.encode(topic.getUrl(), "utf-8").equals(topicUrl)) {
            response.sendRedirect(topic.getLink());
        } else {
            Validator.Forum.canRead(topic.getForum(), user);
            request.setAttribute("topic", topic);
            request.setAttribute("posts", PostManager.getPosts(topic));
            request.setAttribute("navigation", NavigationBean.topic(topic));
            request.getRequestDispatcher("/WEB-INF/topic.jsp").forward(request, response);
        }
    }

    @Override
    protected void post(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] splitted = request.getRequestURI().split("/");
        String topicUrl = splitted[2];
        Long topicId = Long.parseLong(topicUrl.split("\\.")[0]);

        TopicEntity topic = TopicManager.findById(topicId);
        if (!URLEncoder.encode(topic.getUrl(), "utf-8").equals(topicUrl)) {
            response.sendRedirect(topic.getLink());
        } else {
            String text = request.getParameter("text");

            Validator.Forum.canRead(topic.getForum(), user);
            Validator.Forum.canWritePosts(topic.getForum(), user);
            Validator.lengthOrEmpty(text, 11, Integer.MAX_VALUE);

            PostManager.create(topic, user, text);

            response.sendRedirect(topic.getLink());
        }
    }

}

package net.materialforum.servlets;

import java.net.URLDecoder;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.materialforum.beans.Navigation;
import net.materialforum.entities.ForumEntity;
import net.materialforum.entities.TopicEntity;
import net.materialforum.utils.Validator;
import net.materialforum.utils.StringUtils;

@WebServlet("/forum/*")
public class ForumServlet extends BaseServlet {

    @Override
    protected void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] splitted = request.getRequestURI().split("/");
        if (splitted.length < 3) {
            response.sendRedirect("/");
            return;
        }

        String forumUrl = URLDecoder.decode(splitted[2], "utf-8");
        ForumEntity forum = ForumEntity.findByUrl(forumUrl);

        Validator.Forum.exists(forum);
        Validator.Forum.canRead(forum, user);

        request.setAttribute("forum", forum);
        request.setAttribute("title", forum.getTitle());

        if (splitted.length > 3) {
            String action = splitted[3];
            if (action.equals("add")) {
                Validator.Forum.canWriteTopics(forum, user);
                request.setAttribute("navigation", Navigation.forumAddTopic(forum));
                request.getRequestDispatcher("/WEB-INF/newtopic.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("forums", ForumEntity.getAllForums());
            request.setAttribute("topics", forum.getTopics());
            request.setAttribute("navigation", Navigation.forum(forum));
            request.getRequestDispatcher("/WEB-INF/forum.jsp").forward(request, response);
        }
    }

    @Override
    protected void post(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] splitted = request.getRequestURI().split("/");
        String forumUrl = URLDecoder.decode(splitted[2], "utf-8");

        if (splitted.length > 3) {
            String action = splitted[3];
            if (action.equals("add")) {
                ForumEntity forum = ForumEntity.findByUrl(forumUrl);
                String title = StringUtils.removeHtml(request.getParameter("title"));
                String text = request.getParameter("text");

                Validator.Forum.canRead(forum, user);
                Validator.Forum.canWriteTopics(forum, user);
                Validator.Forum.nullParent(forum);
                Validator.lengthOrEmpty(title, 3, 255);
                Validator.lengthOrEmpty(text, 11, Integer.MAX_VALUE);

                TopicEntity topic = new TopicEntity();
                topic.setForum(forum);
                topic.setUser(user);
                topic.setTitle(title);
                topic.create(text);

                response.sendRedirect(topic.getLink());
            }
        }
    }

}

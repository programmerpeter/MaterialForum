package net.materialforum.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.materialforum.beans.NavigationBean;
import net.materialforum.entities.ForumEntity;
import net.materialforum.entities.TopicEntity;
import net.materialforum.entities.TopicManager;
import net.materialforum.entities.UserEntity;
import net.materialforum.utils.Validator;
import net.materialforum.entities.ForumManager;
import net.materialforum.utils.StringUtils;

@WebServlet("/forum/*")
public class ForumServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] splitted = request.getRequestURI().split("/");

        if (splitted.length < 3) {
            response.sendRedirect("/");
            return;
        }

        String forumUrl = splitted[2];
        ForumEntity forum = ForumManager.findByUrl(forumUrl);

        request.setAttribute("forum", forum);

        if (splitted.length > 3) {
            String action = splitted[3];
            if (action.equals("add")) {
                request.setAttribute("navigation", NavigationBean.forumAddTopic(forum));
                request.getRequestDispatcher("/WEB-INF/newtopic.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("forums", ForumManager.getForums());
            request.setAttribute("topics", TopicManager.getTopics(forum.getId()));
            request.setAttribute("navigation", NavigationBean.forum(forum));
            request.getRequestDispatcher("/WEB-INF/forum.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        String[] splitted = request.getRequestURI().split("/");
        String forumUrl = splitted[2];

        if (splitted.length > 3) {
            String action = splitted[3];
            if (action.equals("add")) {
                try {
                    ForumEntity forum = ForumManager.findByUrl(forumUrl);
                    String title = StringUtils.removeHtml(request.getParameter("title"));
                    String text = request.getParameter("text");

                    Validator.Forum.nullParent(forum);
                    Validator.lengthOrEmpty(title, 3, 255);
                    Validator.lengthOrEmpty(text, 11, Integer.MAX_VALUE);

                    UserEntity user = Validator.User.get(request);

                    TopicEntity topic = TopicManager.create(forum, user, title, text);

                    response.sendRedirect(topic.getLink());
                } catch (Validator.ValidationException ex) {
                    Validator.message(response);
                }
            }
        }
    }

}
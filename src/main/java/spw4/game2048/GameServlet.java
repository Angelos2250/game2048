package spw4.game2048;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.UUID;

@WebServlet("/Game")
public class GameServlet extends HttpServlet {
    private HashMap<UUID, Game> games;

    @Override
    public void init() throws ServletException {
        super.init();
        games = new HashMap<>();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        try {
            String action = request.getParameter("action");
            if (action == null) action = "";

            if ("new".equals(action)) {
                UUID id = UUID.randomUUID();
                Game newGame = new GameImpl();
                newGame.initialize();
                games.put(id, newGame);
                writer.println(game2Html(id, newGame));
                return;
            }

            String idString = request.getParameter("id");
            if (idString != null) {
                UUID id = UUID.fromString(idString);
                Game game = games.get(id);

                if (!game.isOver()) {
                    switch (action) {
                        case "up":
                            game.move(Direction.up);
                            break;
                        case "down":
                            game.move(Direction.down);
                            break;
                        case "left":
                            game.move(Direction.left);
                            break;
                        case "right":
                            game.move(Direction.right);
                            break;
                    }
                }

                writer.println(game2Html(id, games.get(id)));
                return;
            }

            writer.println("invalid request");
        }
        catch (Throwable t) {
            writer.println(t.toString());
        }
    }

    private String game2Html(UUID id, Game game) {
        StringBuffer sb = new StringBuffer();

        sb.append("<!DOCTYPE html>\n");
        sb.append("<html lang=\"en\">\n");
        sb.append("<head>\n");
        sb.append("    <meta charset=\"UTF-8\">\n");
        sb.append("    <title>2048</title>\n");
        sb.append("    <link href=\"css/bootstrap.min.css\" rel=\"stylesheet\"/>\n");
        sb.append("    <link href=\"icons/bootstrap-icons.css\" rel=\"stylesheet\"/>\n");
        sb.append("    <script src=\"js/bootstrap.min.js\"></script>\n");
        sb.append("</head>\n");
        sb.append("<body>\n");
        sb.append("    <h1 class=\"m-5 text-center\">Play 2048:</h1>\n");
        sb.append("\n");
        sb.append("    <div class=\"row\">\n");
        sb.append("        <div class=\"col-4\"></div>\n");
        sb.append("        <div class=\"col\">\n");
        sb.append("            <div class=\"row\">\n");
        sb.append("                <div class=\"col text-center pb-4\"><h5>Moves: " + game.getMoves() + "</h5></div>\n");
        sb.append("                <div class=\"col text-center pb-4\"><h5>Score: " + game.getScore() + "</h5></div>\n");
        sb.append("            </div>\n");
        if (game.isOver() && game.isWon()) {
            sb.append("            <div class=\"row\">\n");
            sb.append("                <div class=\"col text-center pb-4\"><h1><span class=\"badge badge-success w-75\">YOU WIN</span></h1></div>\n");
            sb.append("            </div>\n");
        } else if (game.isOver() && !game.isWon()) {
            sb.append("            <div class=\"row\">\n");
            sb.append("                <div class=\"col text-center pb-4\"><h1><span class=\"badge badge-danger w-75\">YOU LOSE</span></h1></div>\n");
            sb.append("            </div>\n");
        }
        sb.append("            <div class=\"row\">\n");
        sb.append("                <div class=\"col-2\"></div>\n");
        sb.append("                <div class=\"col text-center pb-4\"><a role=\"button\" class=\"btn btn-secondary\" href=\"Game?id=" + id + "&action=up\"><i class=\"bi-arrow-up\"></i></a></div>\n");
        sb.append("                <div class=\"col-2\"></div>\n");
        sb.append("            </div>\n");
        sb.append("            <div class=\"row\">\n");
        sb.append("                <div class=\"col-2 my-auto text-right\"><a role=\"button\" class=\"btn btn-secondary\" href=\"Game?id=" + id + "&action=left\"><i class=\"bi-arrow-left\"></i></a></div>\n");
        sb.append("                <div class=\"col\">\n");
        sb.append("                    <div class=\"container text-center bg-secondary\">\n");
        sb.append("                        <div class=\"row\">\n");
        sb.append("                            <div class=\"col mt-1 ml-1 bg-light\">" + game.getValueAt(0, 0) + "</div>\n");
        sb.append("                            <div class=\"col mt-1 ml-1 bg-light\">" + game.getValueAt(0, 1) + "</div>\n");
        sb.append("                            <div class=\"col mt-1 ml-1 bg-light\">" + game.getValueAt(0, 2) + "</div>\n");
        sb.append("                            <div class=\"col mt-1 ml-1 mr-1 bg-light\">" + game.getValueAt(0, 3) + "</div>\n");
        sb.append("                        </div>\n");
        sb.append("                        <div class=\"row\">\n");
        sb.append("                            <div class=\"col mt-1 ml-1 bg-light\">" + game.getValueAt(1, 0) + "</div>\n");
        sb.append("                            <div class=\"col mt-1 ml-1 bg-light\">" + game.getValueAt(1, 1) + "</div>\n");
        sb.append("                            <div class=\"col mt-1 ml-1 bg-light\">" + game.getValueAt(1, 2) + "</div>\n");
        sb.append("                            <div class=\"col mt-1 ml-1 mr-1 bg-light\">" + game.getValueAt(1, 3) + "</div>\n");
        sb.append("                        </div>\n");
        sb.append("                        <div class=\"row\">\n");
        sb.append("                            <div class=\"col mt-1 ml-1 bg-light\">" + game.getValueAt(2, 0) + "</div>\n");
        sb.append("                            <div class=\"col mt-1 ml-1 bg-light\">" + game.getValueAt(2, 1) + "</div>\n");
        sb.append("                            <div class=\"col mt-1 ml-1 bg-light\">" + game.getValueAt(2, 2) + "</div>\n");
        sb.append("                            <div class=\"col mt-1 ml-1 mr-1 bg-light\">" + game.getValueAt(2, 3) + "</div>\n");
        sb.append("                        </div>\n");
        sb.append("                        <div class=\"row\">\n");
        sb.append("                            <div class=\"col mt-1 ml-1 mb-1 bg-light\">" + game.getValueAt(3, 0) + "</div>\n");
        sb.append("                            <div class=\"col mt-1 ml-1 mb-1 bg-light\">" + game.getValueAt(3, 1) + "</div>\n");
        sb.append("                            <div class=\"col mt-1 ml-1 mb-1 bg-light\">" + game.getValueAt(3, 2) + "</div>\n");
        sb.append("                            <div class=\"col mt-1 ml-1 mb-1 mr-1 bg-light\">" + game.getValueAt(3, 3) + "</div>\n");
        sb.append("                        </div>\n");
        sb.append("                    </div>\n");
        sb.append("                </div>\n");
        sb.append("                <div class=\"col-2 my-auto\"><a role=\"button\" class=\"btn btn-secondary\" href=\"Game?id=" + id + "&action=right\"><i class=\"bi-arrow-right\"></i></a></div>\n");
        sb.append("            </div>\n");
        sb.append("            <div class=\"row\">\n");
        sb.append("                <div class=\"col-2\"></div>\n");
        sb.append("                <div class=\"col text-center pt-4\"><a role=\"button\" class=\"btn btn-secondary\" href=\"Game?id=" + id + "&action=down\"><i class=\"bi-arrow-down\"></i></a></div>\n");
        sb.append("                <div class=\"col-2\"></div>\n");
        sb.append("            </div>\n");
        sb.append("            <div class=\"row\">\n");
        sb.append("                <div class=\"col text-center pt-4\"><a role=\"button\" class=\"btn btn-danger w-75\" href=\"Game?action=new\">New Game</a></div>\n");
        sb.append("                <div class=\"col text-center pt-4\"><a role=\"button\" class=\"btn btn-info w-75\" href=\"Game?id=" + id + "\">Refresh</a></div>\n");
        sb.append("            </div>\n");
        sb.append("        </div>\n");
        sb.append("        <div class=\"col-4\"></div>\n");
        sb.append("    </div>\n");
        sb.append("</body>\n");
        sb.append("</html>\n");

        return sb.toString();
    }
}

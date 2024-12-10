//package mk.ukim.finki.wp.lab.web.servlet;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import mk.ukim.finki.wp.lab.service.ArtistService;
//import mk.ukim.finki.wp.lab.service.SongService;
//import org.thymeleaf.context.WebContext;
//import org.thymeleaf.spring6.SpringTemplateEngine;
//import org.thymeleaf.web.IWebExchange;
//import org.thymeleaf.web.servlet.JakartaServletWebApplication;
//
//import java.io.IOException;
//
//@WebServlet(name="SongDetails", urlPatterns = "/songDetails")
//public class SongDetailsServlet extends HttpServlet {
//
//    private final SpringTemplateEngine templateEngine;
//    private final SongService songService;
//    private final ArtistService artistService;
//
//    public SongDetailsServlet(SpringTemplateEngine templateEngine, ArtistService artistService, SongService songService){
//        this.templateEngine = templateEngine;
//        this.artistService = artistService;
//        this.songService = songService;
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        IWebExchange webExchange = JakartaServletWebApplication
//                .buildApplication(getServletContext())
//                .buildExchange(req, resp);
//
//        WebContext context = new WebContext(webExchange);
//
//        context.setVariable("songs", this.songService.listSongs());
//        context.setVariable("artists", this.artistService.listArtists());
//        context.setVariable("selectedSong", songService.findByTrackId(req.getParameter("TrackId")));
//        this.templateEngine.process("songDetails.html", context, resp.getWriter());
//    }
//
//}

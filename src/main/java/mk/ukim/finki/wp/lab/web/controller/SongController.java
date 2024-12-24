package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Genre;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.service.AlbumService;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SongController {

    private final SongService songService;
    private final AlbumService albumService;

    public SongController(SongService songService, AlbumService albumService) {
        this.songService = songService;
        this.albumService = albumService;
    }

    @GetMapping("/access_denied")
    public String accessDeniedPage(Model model){
        model.addAttribute("bodyContent", "access_denied");
        return "master-template";
    }

    @GetMapping("/songs")
    public String getSongsPage(@RequestParam(required = false) String error, @RequestParam(required = false) Long search, Model model){
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        if (search != null){
            model.addAttribute("songs", songService.findAllByAlbum_Id(search));
            model.addAttribute("bodyContent", "listSongs");
        } else {
            List<Song> songs = this.songService.listSongs();
            model.addAttribute("songs", songs);
            model.addAttribute("bodyContent", "listSongs");
        }

        return "master-template";
//        return "listSongs";
    }

    @PostMapping("/songs")
    public String chooseSong(@RequestParam String radioSong){
        return ("redirect:/Artist?radioSong=" + radioSong);
    }

    @PostMapping("/songs/edit/{id}")
    public String editSong(){
        return ("redirect:/?");
    }

    @GetMapping("/songs/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteSong(@PathVariable Long id) {
        this.songService.delete(id);
        return "redirect:/songs";
    }

    @GetMapping("/songs/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addSongPage(Model model){
        List<Album> albums = this.albumService.findAll();
        model.addAttribute("albums", albums);
        model.addAttribute("bodyContent", "add-song");
        return "master-template";
    }

    @PostMapping("/songs/add")
    public String saveSong(@RequestParam String title,
                           @RequestParam String trackId,
                           @RequestParam String genreName,
                           @RequestParam String genreDesc,
                           @RequestParam Integer releaseYear,
                           @RequestParam Long album){
        Genre genre = new Genre(genreName, genreDesc);
        this.songService.Save(trackId,title, genre, releaseYear, album);
        this.albumService.findById(album).getSongs().add(songService.findByTrackId(trackId));
        return "redirect:/songs";

    }

    @GetMapping("/songs/edit-form/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MOD')")
    public String editSong(@PathVariable Long id, Model model){
        if(this.songService.findBySongId(id).isPresent()) {
            Song song = this.songService.findBySongId(id).get();
            List<Album> albums = this.albumService.findAll();
            model.addAttribute("albums", albums);
            model.addAttribute("song", song);
            model.addAttribute("bodyContent", "add-song");
            return "master-template";
        }

        return ("redirect:/songs?error=SongNotFound");
    }

}
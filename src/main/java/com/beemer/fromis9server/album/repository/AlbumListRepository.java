package com.beemer.fromis9server.album.repository;

import com.beemer.fromis9server.album.model.AlbumList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumListRepository extends JpaRepository<AlbumList, String> {
    List<AlbumList> findByAlbumName(String albumName);

    @Query("SELECT a FROM AlbumList a JOIN a.trackList t JOIN t.song s WHERE a.albumName = :albumName AND s.trackListId.songName = :songName")
    List<AlbumList> findByAlbumNameAndSongName(@Param("albumName") String albumName, @Param("songName") String songName);
}
package com.beemer.fromis9server.album.repository;

import com.beemer.fromis9server.album.model.AlbumList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumListRepository extends JpaRepository<AlbumList, String> {

}

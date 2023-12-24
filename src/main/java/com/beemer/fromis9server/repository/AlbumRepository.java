package com.beemer.fromis9server.repository;

import com.beemer.fromis9server.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {

}

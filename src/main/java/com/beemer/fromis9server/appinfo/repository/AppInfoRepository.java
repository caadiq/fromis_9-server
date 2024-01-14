package com.beemer.fromis9server.appinfo.repository;

import com.beemer.fromis9server.appinfo.model.AppInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppInfoRepository extends JpaRepository<AppInfo, Long> {

}
package com.jayzero.games.esportsclub.launcher;

import com.jayzero.games.esportsclub.web.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * com.jayzero.games.esportsclub.launcher.EsportsClubLauncher
 *
 * @author 成至 forrestzhu.zl@alibaba-inc.com
 * @version com.jayzero.games.esportsclub.launcher.EsportsClubLauncher.java, v0.1
 * @date 2018/09/24
 */
@SpringBootApplication
@Import({WebConfig.class})
public class EsportsClubLauncher {

    public static void main(String[] args) {
        SpringApplication.run(EsportsClubLauncher.class, args);
    }
}

package video.game.store.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class VideoGameStoreUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoGameStoreUserApplication.class, args);
    }

}

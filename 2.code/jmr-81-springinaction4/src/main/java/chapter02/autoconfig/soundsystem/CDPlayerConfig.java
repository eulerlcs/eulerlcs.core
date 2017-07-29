package chapter02.autoconfig.soundsystem;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = { SgtPeppers.class })
public class CDPlayerConfig {
}

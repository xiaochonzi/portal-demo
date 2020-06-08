package com.example.portal.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("portal")
public class PortalProperties {
    private Boolean enableCaptcha = false;
}

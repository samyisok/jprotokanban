package com.axix.jprotokanban.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mail-sender")
public class MailSenderProperties {
  private String host;
  private Integer port = 993;
  private String user;
  private String password;
  private Boolean debug = false;
  private Boolean active = false;
  private String defaultReplyEmail;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean getDebug() {
    return debug;
  }

  public void setDebug(Boolean debug) {
    this.debug = debug;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public String getDefaultReplyEmail() {
    return defaultReplyEmail;
  }

  public void setDefaultReplyEmail(String defaultReplyEmail) {
    this.defaultReplyEmail = defaultReplyEmail;
  }

}

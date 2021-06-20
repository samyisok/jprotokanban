package com.axix.jprotokanban.components.mail.reciever;

import java.util.List;

public interface MailReceivable {
  public List<MailContainer> receivePop3Email(
      String host,
      Integer port,
      String user,
      String password,
      String folder);
}

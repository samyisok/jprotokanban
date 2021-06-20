package com.axix.jprotokanban.models.outcomingmail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OutcomingMailRepository extends JpaRepository<OutcomingMail, Long> {
  public List<OutcomingMail> findBySendedFalse();
}

package com.springexamples.demo.common;

import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement(name = "error")
public class ErrorResponse {
  public ErrorResponse(String message, List<String> details) {
    super();
    this.timestamp = LocalDateTime.now();
    this.message = message;
    this.details = details;
  }

  private LocalDateTime timestamp;
  private String message;
  private List<String> details;
}